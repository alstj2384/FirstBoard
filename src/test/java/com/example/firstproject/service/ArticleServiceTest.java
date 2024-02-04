package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        List<Article> articles = articleService.index();

        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");

        List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c));

        assertEquals(articles.toString(), expected.toString());

    }

    @Test
    void show_성공_존재하는_id_입력() {
        // given
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        // when
        Article article = articleService.show(id);

        // then
        assertEquals(article.toString(), expected.toString());
    }


    @Test
    void show_실패_존재하지_않는_id_입력() {
        // given
        Long id = -1L;
        Article expected = null;

        // when
        Article article = articleService.show(id);

        // then
        assertEquals(article, expected);
    }

    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        // given
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // when
        Article article = articleService.create(dto);

        // then
        assertEquals(article.toString(), expected.toString());
    }


    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {

        // given
        Long id = 1L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(1L, title, content);
        Article expected = null;

        // when
        Article article = articleService.create(dto);

        // then
        assertEquals(article, expected);
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title_content가_있는_dto_입력() {
        //given
        Long id = 1L;
        String title = "updated";
        String content = "updatedContent";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(1L, "updated", "updatedContent");

        //when
        Article update = articleService.update(id, dto);

        //then
        assertEquals(update.toString(), expected.toString());
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title만_있는_dto_입력() {
        //given
        Long id = 1L;
        String title = "updated";
        ArticleForm dto = new ArticleForm(id, title, null);
        Article expected = new Article(1L, "updated", "1111");

        //when
        Article update = articleService.update(id, dto);

        //then
        assertEquals(update.toString(), expected.toString());
    }

    @Test
    @Transactional
    void update_실패_존재하지_않는_id의_dto_입력() {
        //given
        Long id = -1L;
        ArticleForm dto = new ArticleForm(id, null, null);
        Article expected = null;

        //when
        Article update = articleService.update(id, dto);

        //then
        assertEquals(update, expected);
    }

    @Test
    @Transactional
    void delete_성공_존재하는_id_입력() {
        // given
        Long id = 1L;
        Article expected = new Article(1L, "가가가가", "1111");

        // when
        Article delete = articleService.delete(id);

        // then
        assertEquals(expected.toString(), delete.toString());
    }


    @Test
    @Transactional
    void delete_실패_존재하지_않는_id_입력() {
        // given
        Long id = -1L;
        Article expected = null;

        // when
        Article delete = articleService.delete(id);

        // then
        assertEquals(expected, delete);
    }
}