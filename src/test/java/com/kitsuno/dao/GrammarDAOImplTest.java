package com.kitsuno.dao;

import com.kitsuno.entity.Grammar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = "com.kitsuno.dao")
@Sql(scripts = "/grammar_data.sql")
class GrammarDAOImplTest {

    @Autowired
    private GrammarDAO grammarDAO;

    @Test
    void testFindAll() {
        List<Grammar> result = grammarDAO.findAll();

        assertThat(result).extracting(Grammar::getTitle)
                .containsExactly("State of being", "Negative verbs", "Te form");
    }

    @Test
    void testFindByIdExisting() {
        Grammar result = grammarDAO.findById(2);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Negative verbs");
        assertThat(result.getContent()).contains("<p>Two</p>");
    }

    @Test
    void testFindByIdNonExisting() {
        assertThat(grammarDAO.findById(999)).isNull();
    }

    @Test
    void testFindPrevious() {
        assertThat(grammarDAO.findPrevious(2).getTitle()).isEqualTo("State of being");
        assertThat(grammarDAO.findPrevious(1)).isNull();
    }

    @Test
    void testFindNext() {
        assertThat(grammarDAO.findNext(2).getTitle()).isEqualTo("Te form");
        assertThat(grammarDAO.findNext(3)).isNull();
    }
}