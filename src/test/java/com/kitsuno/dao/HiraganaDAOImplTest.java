package com.kitsuno.dao;

import com.kitsuno.entity.Hiragana;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = "com.kitsuno.dao")
@Sql(scripts = "/hiragana_data.sql")
class HiraganaDAOImplTest {

    @Autowired
    private HiraganaDAO hiraganaDAO;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindAll() {
        List<Hiragana> result = hiraganaDAO.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Hiragana::getRomaji).containsExactlyInAnyOrder("a", "i");
    }

    @Test
    void testFindByCharacterExisting() {
        Hiragana result = hiraganaDAO.findByCharacter("a");

        assertThat(result).isNotNull();
        assertThat(result.getCharacter()).isEqualTo("あ");
        assertThat(result.getRomaji()).isEqualTo("a");
    }

    @Test
    void testFindByCharacterNonExisting() {
        Hiragana result = hiraganaDAO.findByCharacter("x");

        assertThat(result).isNull();
    }
}
