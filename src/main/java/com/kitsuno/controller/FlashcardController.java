package com.kitsuno.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.User;
import com.kitsuno.service.FlashcardService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.UserService;
import com.kitsuno.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class FlashcardController {

    private final FlashcardService flashcardService;
    private final KanjiService kanjiService;
    private final UserService userService;

    @Autowired
    public FlashcardController(FlashcardService flashcardService, KanjiService kanjiService, UserService userService) {
        this.flashcardService = flashcardService;
        this.kanjiService = kanjiService;
        this.userService = userService;
    }

    @PostMapping("/kanji/{character}")
    public String saveOrUpdateFlashcard(@Valid @ModelAttribute("flashcardDTO") FlashcardDTO flashcardDTO,
                                        BindingResult bindingResult,
                                        @RequestParam("kanjiCharacter") String kanjiCharacter,
                                        @RequestParam("userId") int userId,
                                        Model model) {

        if (!flashcardDTO.hasAtLeastOneVocabPair()) {
            bindingResult.reject("error.flashcardDTO", "At least one vocabulary pair is required");
        }

        if (bindingResult.hasErrors()) {
            Kanji kanji = kanjiService.findKanjiByCharacter(kanjiCharacter);
            model.addAttribute("hasError", true);
            model.addAttribute("userId", userId);
            model.addAttribute("kanji", kanji);
            model.addAttribute("flashcardDTO", flashcardDTO);
            model.addAttribute("hasFlashcard",
                    flashcardService.getFlashcardByUserAndKanji(userId, kanjiCharacter) != null);
            return "kanji-details";
        }

        flashcardService.saveOrUpdateFlashcard(flashcardDTO, userId, kanjiCharacter);

        return "redirect:/flashcards";
    }

    @GetMapping("/flashcards")
    public String showFlashcards(Model model) {

        Optional<User> authenticatedUser = SecurityUtils.getAuthenticatedUser(userService);

            if (authenticatedUser.isPresent()) {
                User user = authenticatedUser.get();
                List<Flashcard> flashcardsList = flashcardService.getAllFlashcardsByUserId(user.getId());
                model.addAttribute("flashcardsList", flashcardsList);
            }

        return "flashcard";
    }

    @PostMapping("/flashcards/export_flashcards_csv")
    public void export_flashcards(HttpServletResponse response) {
        Optional<User> authenticatedUser = SecurityUtils.getAuthenticatedUser(userService);

        if (authenticatedUser.isPresent()) {
            User user = authenticatedUser.get();
            List<Flashcard> flashcardsList = flashcardService.getAllFlashcardsByUserId(user.getId());

            response.setContentType("text/csv; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + user.getUsername() +
                    "_flashcards.csv");

            try (PrintWriter writer = response.getWriter()) {
                writer.write('\uFEFF');
                writer.println("Kanji,Kunyomi,Onyomi,Meaning,Notes,Vocabulary");

                for (Flashcard flashcard : flashcardsList) {
                    String kanji = flashcard.getKanji().getCharacter();
                    String kunyomi = flashcard.getKanji().getKunyomiReadings() != null
                            ? String.join(", ", flashcard.getKanji().getKunyomiReadings()) : "-";
                    String onyomi = flashcard.getKanji().getOnyomiReadings() != null
                            ? String.join(", ", flashcard.getKanji().getOnyomiReadings()) : "-";
                    String meanings = flashcard.getKanji().getMeanings();
                    String notes = flashcard.getNotes() != null ? flashcard.getNotes() : "-";
                    String vocabulary = String.join("; ", flashcard.getVocabulary());

                    writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                            kanji, kunyomi, onyomi, meanings, notes, vocabulary);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/flashcards/export_flashcards_anki")
    public void exportFlashcardsAsAnki(HttpServletResponse response) {
        Optional<User> authenticatedUser = SecurityUtils.getAuthenticatedUser(userService);

        if (authenticatedUser.isPresent()) {
            User user = authenticatedUser.get();
            List<Flashcard> flashcardsList = flashcardService.getAllFlashcardsByUserId(user.getId());
            List<List<String>> flashcardsData = new ArrayList<>();

            for (Flashcard flashcard : flashcardsList) {
                List<String> flashcardInfo = new ArrayList<>();
                flashcardInfo.add(flashcard.getKanji().getCharacter());
                flashcardInfo.add(flashcard.getNotes());
                flashcardInfo.add(String.join(", ", flashcard.getKanji().getKunyomiReadings()));
                flashcardInfo.add(String.join(", ", flashcard.getKanji().getOnyomiReadings()));
                flashcardInfo.add(flashcard.getKanji().getMeanings());
                flashcardInfo.add(String.join(", ", flashcard.getVocabulary()));
                flashcardsData.add(flashcardInfo);
            }

            try {
                String jsonFlashcardsData = new ObjectMapper().writeValueAsString(flashcardsData);
                jsonFlashcardsData = jsonFlashcardsData.replace("\"", "\\\"");

                InputStream scriptInputStream = getClass().getResourceAsStream(
                        "/static/python/flashcards_to_anki.py");
                if (scriptInputStream == null) {
                    throw new FileNotFoundException("Python script not found in resources.");
                }

                File tempScript = File.createTempFile("flashcards_to_anki", ".py");
                tempScript.deleteOnExit();
                Files.copy(scriptInputStream, tempScript.toPath(), StandardCopyOption.REPLACE_EXISTING);

                ProcessBuilder processBuilder = new ProcessBuilder("python3", tempScript.getAbsolutePath(),
                        jsonFlashcardsData);
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                InputStream inputStream = process.getInputStream();
                byte[] buffer = new byte[1024];

                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                process.waitFor();

                // Set response for file download
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + user.getUsername() +
                        "_flashcards.apkg\"");

                outputStream.writeTo(response.getOutputStream());
                response.flushBuffer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @PostMapping("/flashcards/delete/{id}")
    public String deleteFlashcard(@PathVariable("id") int id) {
        flashcardService.deleteFlashcard(id);
        return "redirect:/flashcards";
    }
}
