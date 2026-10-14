package com.kitsuno.service;

import com.kitsuno.dao.GrammarDAO;
import com.kitsuno.entity.Grammar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GrammarServiceImplTest {

    @Mock
    private GrammarDAO grammarDAO;

    @InjectMocks
    private GrammarServiceImpl grammarService;

    @Test
    void testFindAll() {
        List<Grammar> lessons = List.of(new Grammar("One", "<p>1</p>"), new Grammar("Two", "<p>2</p>"));
        when(grammarDAO.findAll()).thenReturn(lessons);

        assertThat(grammarService.findAll()).hasSize(2).isEqualTo(lessons);
    }

    @Test
    void testFindByIdExisting() {
        Grammar lesson = new Grammar("One", "<p>1</p>");
        when(grammarDAO.findById(1)).thenReturn(lesson);

        assertThat(grammarService.findById(1).getTitle()).isEqualTo("One");
    }

    @Test
    void testFindByIdNonExisting() {
        when(grammarDAO.findById(999)).thenReturn(null);

        assertThat(grammarService.findById(999)).isNull();
    }

    @Test
    void testFindPreviousAndNext() {
        Grammar previous = new Grammar("One", "<p>1</p>");
        Grammar next = new Grammar("Three", "<p>3</p>");
        when(grammarDAO.findPrevious(2)).thenReturn(previous);
        when(grammarDAO.findNext(2)).thenReturn(next);

        assertThat(grammarService.findPrevious(2)).isSameAs(previous);
        assertThat(grammarService.findNext(2)).isSameAs(next);
    }
}