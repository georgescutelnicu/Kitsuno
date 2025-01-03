package com.kitsuno.service;

import com.kitsuno.entity.Hiragana;
import com.kitsuno.dao.HiraganaDAO;
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
class HiraganaServiceImplTest {

    @Mock
    private HiraganaDAO hiraganaDAO;

    @InjectMocks
    private HiraganaServiceImpl hiraganaService;

    @Test
    void testFindAll() {
        Hiragana hiragana1 = new Hiragana("あ", "a", "audio-a.mp3",
                "Looks like a person bowing", "A story for A", "stroke-a.svg");
        Hiragana hiragana2 = new Hiragana("い", "i", "audio-i.mp3",
                "Looks like a person stretching", "A story for I", "stroke-i.svg");

        when(hiraganaDAO.findAll()).thenReturn(List.of(hiragana1, hiragana2));

        List<Hiragana> result = hiraganaService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Hiragana::getRomaji).containsExactlyInAnyOrder("a", "i");
    }

    @Test
    void testFindByCharacterExisting() {
        Hiragana hiragana = new Hiragana("あ", "a", "audio-a.mp3",
                "Looks like a person bowing", "A story for A", "stroke-a.svg");

        when(hiraganaDAO.findByCharacter("a")).thenReturn(hiragana);

        Hiragana result = hiraganaService.findByCharacter("a");

        assertThat(result).isNotNull();
        assertThat(result.getCharacter()).isEqualTo("あ");
        assertThat(result.getRomaji()).isEqualTo("a");
    }

    @Test
    void testFindByCharacterNonExisting() {
        when(hiraganaDAO.findByCharacter("x")).thenReturn(null);

        assertThatThrownBy(() -> hiraganaService.findByCharacter("x"))
                .isInstanceOf(CharacterNotFoundException.class)
                .hasMessage("Hiragana romaji not found: x");
    }
}
