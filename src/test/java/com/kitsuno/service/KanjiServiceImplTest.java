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
        kanji1 = new Kanji("日", "N5",
                "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                new String[]{"毎日 【まいにち】every day", "日光 【にっこう】sunlight", "翌日 【よくじつ】next day"},
                new String[]{"日 【ひ】day, days", "記念日 【きねんび】anniversary, memorial day"},
                "Time", 4);

        kanji2 = new Kanji("月", "N5",
                "month, moon",
                new String[]{"ゲツ", "ガツ"},
                new String[]{"つき"},
                new String[]{"月曜 【げつよう】 Monday", "来月 【らいげつ】 next month", "満月 【まんげつ】 full moon"},
                new String[]{"一月 【ひとつき】 one month", "毎月 【まいつき】 every month"},
                "Time", 4);

        kanji3 = new Kanji("人", "N5",
                "person",
                new String[]{"ジン", "ニン"},
                new String[]{"ひと"},
                new String[]{"~人 【じん】 often used for citizenship after a country (American, Chinese, etc..)",
                        "友人 【ゆうじん】friend"},
                new String[]{"人 【ひと】 man, person, people", "いい人 【いいひと】 good-natured person, " +
                        "good person, lover"},
                "People", 2);
    }

    @Test
    void testFindKanjiByIdExisting() {
        when(kanjiDAO.findKanjiById(1)).thenReturn(kanji1);

        Kanji result = kanjiService.findKanjiById(1);

        assertThat(result).isNotNull();
        assertThat(result.getCharacter()).isEqualTo("日");
        assertThat(result.getCategory()).isEqualTo("Time");
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
        assertThat(result.getCategory()).isEqualTo("Time");
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
    void testFindAllCategories() {
        when(kanjiDAO.findAllCategories()).thenReturn(Arrays.asList("Time", "People"));

        List<String> result = kanjiService.findAllCategories();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactlyInAnyOrder("Time", "People");
    }

    @Test
    void testFindAllByCategory() {
        when(kanjiDAO.findAllByCategory("Time")).thenReturn(Arrays.asList(kanji1, kanji2));

        List<Kanji> result = kanjiService.findAllByCategory("Time");

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Kanji::getCharacter).containsExactlyInAnyOrder("日", "月");
    }

    @Test
    void testFindAllGroupedByCategory() {
        when(kanjiDAO.findAll()).thenReturn(Arrays.asList(kanji1, kanji2, kanji3));

        Map<String, List<Kanji>> result = kanjiService.findAllGroupedByCategory();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get("Time")).containsExactlyInAnyOrder(kanji1, kanji2);
        assertThat(result.get("People")).containsExactlyInAnyOrder(kanji3);
    }
}
