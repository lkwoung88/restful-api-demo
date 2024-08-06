package hello.rest.domain.content.service;

import hello.rest.domain.content.dto.ArticleDto;
import hello.rest.domain.content.entity.Article;
import hello.rest.domain.content.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {

    @Autowired
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getArticle() {
        return this.articleRepository.findAll();
    }

    public Article getArticle(long contentId) {
        return this.articleRepository.findById(contentId).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public ArticleDto createArticle(Article article) {
        this.articleRepository.save(article);
        return new ArticleDto(article);
    }

    @Transactional(rollbackFor = Exception.class)
    public ArticleDto updateArticle(Article article) {
        Article updatedArticle = this.articleRepository.findById(article.getId()).orElseThrow(NoSuchElementException::new);
        updatedArticle.setTitle(article.getTitle());
        updatedArticle.setContent(article.getContent());
        return new ArticleDto(updatedArticle);
    }

    @Transactional(rollbackFor = Exception.class)
    public ArticleDto deleteArticle(long contentId) {
        this.articleRepository.deleteById(contentId);
        return new ArticleDto(contentId);
    }
}
