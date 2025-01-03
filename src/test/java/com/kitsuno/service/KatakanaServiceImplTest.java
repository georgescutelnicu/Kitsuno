package com.kitsuno.service;

import com.kitsuno.entity.Katakana;
import com.kitsuno.dao.KatakanaDAO;
import com.kitsuno.exception.rest.CharacterNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KatakanaServiceImplTest {

    @Mock
    private KatakanaDAO katakanaDAO;

    @InjectMocks
    private KatakanaServiceImpl katakanaService;

    @Test
    void testFindAll() {
        Katakana katakana1 = new Katakana("ア", "a", "audio-a.mp3",
                "Looks like a person stretching", "A story for A", "stroke-a.svg");
        Katakana katakana2 = new Katakana("イ", "i", "audio-i.mp3",
                "Looks like a person walking", "A story for I", "stroke-i.svg");

        when(katakanaDAO.findAll()).thenReturn(List.of(katakana1, katakana2));

        List<Katakana> result = katakanaService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Katakana::getRomaji).containsExactlyInAnyOrder("a", "i");
    }

    @Test
    void testFindByCharacterExisting() {
        Katakana katakana = new Katakana("ア", "a", "audio-a.mp3",
                "Looks like a person stretching", "A story for A", "stroke-a.svg");

        when(katakanaDAO.findByCharacter("a")).thenReturn(katakana);

        Katakana result = katakanaService.findByCharacter("a");

        assertThat(result).isNotNull();
        assertThat(result.getCharacter()).isEqualTo("ア");
        assertThat(result.getRomaji()).isEqualTo("a");
    }

    @Test
    void testFindByCharacterNonExisting() {
        when(katakanaDAO.findByCharacter("x")).thenReturn(null);

        assertThatThrownBy(() -> katakanaService.findByCharacter("x"))
                .isInstanceOf(CharacterNotFoundException.class)
                .hasMessage("Katakana romaji not found: x");
    }
}
