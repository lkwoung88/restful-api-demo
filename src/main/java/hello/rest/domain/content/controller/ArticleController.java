package hello.rest.domain.content.controller;

import hello.rest.domain.content.entity.Article;
import hello.rest.domain.content.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-v1/article")
public class ArticleController {

    @Autowired
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<Article>> getContents() {
        return ResponseEntity.ok(articleService.getContents());
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<Article> getContent(@PathVariable("article-id") long contentId) {
        return ResponseEntity.ok(articleService.getContent(contentId));
    }

    @PostMapping
    public ResponseEntity<Long> uploadContent(@RequestBody Article article) {
        return ResponseEntity.ok(articleService.uploadContent(article));
    }

    @PutMapping
    public ResponseEntity<Long> modifyContent(@RequestBody Article article) {
        return ResponseEntity.ok(articleService.modifyContent(article));
    }

    @DeleteMapping("/{content-id}")
    public ResponseEntity<Long> deleteContent(@PathVariable("content-id") long contentId) {
        return ResponseEntity.ok(articleService.deleteContent(contentId));
    }
}
