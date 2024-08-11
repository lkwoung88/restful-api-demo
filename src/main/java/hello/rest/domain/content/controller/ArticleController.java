package hello.rest.domain.content.controller;

import hello.rest.domain.content.dto.ArticleDto;
import hello.rest.domain.content.entity.Article;
import hello.rest.domain.content.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api-v1/article")
public class ArticleController {

    @Autowired
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Article>>> getArticle() {
        List<Article> articles = articleService.getArticle();

        List<EntityModel<Article>> collect = articles.stream().map(article -> EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).getArticle(article.getId())).withSelfRel(),
                linkTo(methodOn(ArticleController.class).updateArticle(null)).withRel("update"),
                linkTo(methodOn(ArticleController.class).deleteArticle(article.getId())).withRel("delete")
        )).toList();

        CollectionModel<EntityModel<Article>> collectionModel = CollectionModel.of(collect,
                linkTo(methodOn(ArticleController.class).getArticle()).withSelfRel(),
                linkTo(methodOn(ArticleController.class).createArticle(null)).withRel("create"));

        return new ResponseEntity<>(collectionModel, getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<EntityModel<Article>> getArticle(@PathVariable("article-id") long contentId) {
        Article selectedArticle = articleService.getArticle(contentId);

        EntityModel<Article> entityModel = EntityModel.of(selectedArticle,
                linkTo(methodOn(ArticleController.class).getArticle(selectedArticle.getId())).withSelfRel(),
                linkTo(methodOn(ArticleController.class).updateArticle(null)).withRel("update"),
                linkTo(methodOn(ArticleController.class).deleteArticle(selectedArticle.getId())).withRel("delete"));

        return new ResponseEntity<>(entityModel, getHttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntityModel<ArticleDto>> createArticle(@RequestBody Article article) {
        ArticleDto articleDto = articleService.createArticle(article);

        EntityModel<ArticleDto> entityModel = EntityModel.of(articleDto,
                linkTo(methodOn(ArticleController.class).getArticle(articleDto.getId())).withSelfRel(),
                linkTo(methodOn(ArticleController.class).getArticle()).withRel("articles"));

        return new ResponseEntity<>(entityModel, getHttpHeaders(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<EntityModel<ArticleDto>> updateArticle(@RequestBody Article article) {
        ArticleDto articleDto = articleService.updateArticle(article);

        EntityModel<ArticleDto> entityModel = EntityModel.of(articleDto,
                linkTo(methodOn(ArticleController.class).getArticle(articleDto.getId())).withSelfRel(),
                linkTo(methodOn(ArticleController.class).getArticle()).withRel("articles"));

        return new ResponseEntity<>(entityModel, getHttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{article-id}")
    public ResponseEntity<EntityModel<ArticleDto>> deleteArticle(@PathVariable("article-id") long contentId) {
        EntityModel<ArticleDto> entityModel = EntityModel.of(articleService.deleteArticle(contentId),
                linkTo(methodOn(ArticleController.class).getArticle()).withRel("articles"));
        return new ResponseEntity<>(entityModel, getHttpHeaders(), HttpStatus.OK);
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        return httpHeaders;
    }
}
