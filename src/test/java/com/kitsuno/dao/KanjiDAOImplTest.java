package com.kitsuno.dao;

import com.kitsuno.entity.Kanji;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = "com.kitsuno.dao")
@Sql(scripts = "/kanji_data.sql")
class KanjiDAOImplTest {

    @Autowired
    private KanjiDAO kanjiDAO;

    @Test
    void testFindKanjiByIdExisting() {
        Kanji result = kanjiDAO.findKanjiById(1);

        assertThat(result).isNotNull();
        assertThat(result.getCharacter()).isEqualTo("日");
        assertThat(result.getCategory()).isEqualTo("Time");
    }

    @Test
    void testFindKanjiByIdNonExisting() {
        Kanji result = kanjiDAO.findKanjiById(999);

        assertThat(result).isNull();
    }

    @Test
    void testFindKanjiByCharacterExisting() {
        Kanji result = kanjiDAO.findKanjiByCharacter("日");

        assertThat(result).isNotNull();
        assertThat(result.getCharacter()).isEqualTo("日");
        assertThat(result.getCategory()).isEqualTo("Time");
    }

    @Test
    void testFindKanjiByCharacterNonExisting() {
        Kanji result = kanjiDAO.findKanjiByCharacter("X");

        assertThat(result).isNull();
    }

    @Test
    void testFindAll() {
        List<Kanji> result = kanjiDAO.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).extracting(Kanji::getCharacter).containsExactlyInAnyOrder("日", "月", "人");
    }

    @Test
    void testFindAllCategories() {
        List<String> result = kanjiDAO.findAllCategories();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder("Time", "People");
    }

    @Test
    void testFindAllByCategory() {
        List<Kanji> result = kanjiDAO.findAllByCategory("Time");

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Kanji::getCharacter).containsExactlyInAnyOrder("日", "月");
    }
}
