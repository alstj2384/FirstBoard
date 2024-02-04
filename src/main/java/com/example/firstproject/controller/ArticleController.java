package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);
        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 해당 게시글의 댓글 조회하여 가져오기
        List<Comment> comments = commentRepository.findByArticleId(id);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("commentDtos", comments);
        model.addAttribute("article", articleEntity);
        // 3. 뷰 페이지 밚환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
//        1. DB에서 모든 데이터 가져오기
        List<Article> articleEntityList = (List<Article>)articleRepository.findAll();
        model.addAttribute("articleList", articleEntityList);
//        2. 가져온 Article 묶음을 모델에 등록하기
//        3. 사용자에게 보여 줄 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form, Model model){
        log.info(form.toString());
        // 1. DTO를 엔티티(객체)로 변환
        Article article = form.toEntity();
        // 2. 레포지터리로 엔티티를 저장
        Article saved = articleRepository.save(article);
        log.info(article.toString());
        log.info(article.toString());
        model.addAttribute("article", article);
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        if(target != null){
            articleRepository.save(articleEntity);
        }

        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        if(target !=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }

        return "redirect:/articles";
    }
}
