package com.kitsuno.dao;

import com.kitsuno.entity.Katakana;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = "com.kitsuno.dao")
@Sql(scripts = "/katakana_data.sql")
class KatakanaDAOImplTest {

    @Autowired
    private KatakanaDAO katakanaDAO;

    @Test
    void testFindAll() {
        List<Katakana> result = katakanaDAO.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Katakana::getRomaji).containsExactlyInAnyOrder("a", "i");
    }

    @Test
    void testFindByCharacterExisting() {
        Katakana result = katakanaDAO.findByCharacter("a");

        assertThat(result).isNotNull();
        assertThat(result.getCharacter()).isEqualTo("ア");
        assertThat(result.getRomaji()).isEqualTo("a");
    }

    @Test
    void testFindByCharacterNonExisting() {
        Katakana result = katakanaDAO.findByCharacter("x");

        assertThat(result).isNull();
    }
}
