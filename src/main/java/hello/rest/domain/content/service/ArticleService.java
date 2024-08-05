package hello.rest.domain.content.service;

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

    public List<Article> getContents() {
        return this.articleRepository.findAll();
    }

    public Article getContent(long contentId) {
        return this.articleRepository.findById(contentId).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public long uploadContent(Article article) {
        this.articleRepository.save(article);
        return article.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public long modifyContent(Article article) {
        Article findArticle = this.articleRepository.findById(article.getId()).orElseThrow(NoSuchElementException::new);
        findArticle.setTitle(article.getTitle());
        findArticle.setContent(article.getContent());
        return article.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public long deleteContent(long contentId) {
        this.articleRepository.deleteById(contentId);
        return contentId;
    }
}
