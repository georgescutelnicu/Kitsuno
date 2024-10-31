package com.kitsuno.utils;

import com.kitsuno.entity.Kana;
import com.kitsuno.entity.Hiragana;
import com.kitsuno.entity.Katakana;
import com.kitsuno.entity.User;
import com.kitsuno.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Utils {

    public static <T extends Kana> List<T> formatKana(List<T> kanaList) {
        List<T> formattedKana = new ArrayList<>();

        for (T kana : kanaList) {
            formattedKana.add(kana);
            String romaji = kana.getRomaji();

            if ("yu".equals(romaji) || "ya".equals(romaji)) {

                formattedKana.add(createEmptyKana(kana));

            } else if ("wa".equals(romaji)) {

                for (int i = 0; i < 3; i++) {
                    formattedKana.add(createEmptyKana(kana));
                }
            }
        }
        return formattedKana;
    }

    private static <T extends Kana> T createEmptyKana(T kana) {
        if (kana instanceof Hiragana) {
            return (T) new Hiragana();
        } else {
            return (T) new Katakana();
        }
    }

    public static Optional<User> getAuthenticatedUser(UserService userService) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            return userService.findByUsername(username);
        }
        return Optional.empty();
    }
}
