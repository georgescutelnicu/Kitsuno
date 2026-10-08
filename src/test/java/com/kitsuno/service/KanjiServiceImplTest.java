package com.kitsuno.service;

import com.kitsuno.dao.KanjiDAO;
import com.kitsuno.entity.Kanji;
import com.kitsuno.exception.rest.CharacterNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KanjiServiceImplTest {

    @Mock
    private KanjiDAO kanjiDAO;

    @InjectMocks
    private KanjiServiceImpl kanjiService;

    private Kanji kanji1;
    private Kanji kanji2;
    private Kanji kanji3;

    @BeforeEach
    void setUp() {
        kanji1 = new Kanji("日", 4, 1,
                "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                "<svg>...</svg>",
                new String[]{"毎日", "日光"});

        kanji2 = new Kanji("月", 4, 1,
                "month, moon",
                new String[]{"ゲツ", "ガツ"},
                new String[]{"つき"},
                "<svg>...</svg>",
                new String[]{"月曜", "来月"});

        kanji3 = new Kanji("人", 2, 2,
                "person",
                new String[]{"ジン", "ニン"},
                new String[]{"ひと"},
                "<svg>...</svg>",
                new String[]{"友人", "いい人"});
    }

    @Test
    void testFindKanjiByIdExisting() {
        when(kanjiDAO.findKanjiById(1)).thenReturn(kanji1);

        Kanji result = kanjiService.findKanjiById(1);

        assertThat(result).isNotNull();
        assertThat(result.getCharacter()).isEqualTo("日");
        assertThat(result.getGrade()).isEqualTo(1);
    }

    @Test
    void testFindKanjiByIdNonExisting() {
        when(kanjiDAO.findKanjiById(999)).thenReturn(null);

        assertThatThrownBy(() -> kanjiService.findKanjiById(999))
                .isInstanceOf(CharacterNotFoundException.class)
                .hasMessage("Kanji character not found for id: 999");
    }

    @Test
    void testFindKanjiByCharacterExisting() {
        when(kanjiDAO.findKanjiByCharacter("日")).thenReturn(kanji1);

        Kanji result = kanjiService.findKanjiByCharacter("日");

        assertThat(result).isNotNull();
        assertThat(result.getCharacter()).isEqualTo("日");
        assertThat(result.getGrade()).isEqualTo(1);
    }

    @Test
    void testFindKanjiByCharacterNonExisting() {
        when(kanjiDAO.findKanjiByCharacter("x")).thenReturn(null);

        assertThatThrownBy(() -> kanjiService.findKanjiByCharacter("x"))
                .isInstanceOf(CharacterNotFoundException.class)
                .hasMessage("Kanji character not found: x");
    }

    @Test
    void testFindAll() {
        when(kanjiDAO.findAll()).thenReturn(Arrays.asList(kanji1, kanji2, kanji3));

        List<Kanji> result = kanjiService.findAll();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result).extracting(Kanji::getCharacter).containsExactlyInAnyOrder("日", "月", "人");
    }

    @Test
    void testFindAllGrades() {
        when(kanjiDAO.findAllGrades()).thenReturn(Arrays.asList(1, 2));

        List<Integer> result = kanjiService.findAllGrades();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void testFindAllByGrade() {
        when(kanjiDAO.findAllByGrade(1)).thenReturn(Arrays.asList(kanji1, kanji2));

        List<Kanji> result = kanjiService.findAllByGrade(1);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Kanji::getCharacter).containsExactlyInAnyOrder("日", "月");
    }

    @Test
    void testFindAllGroupedByGrade() {
        when(kanjiDAO.findAll()).thenReturn(Arrays.asList(kanji1, kanji2, kanji3));

        Map<Integer, List<Kanji>> result = kanjiService.findAllGroupedByGrade();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1)).containsExactlyInAnyOrder(kanji1, kanji2);
        assertThat(result.get(2)).containsExactlyInAnyOrder(kanji3);
    }
}