package hello.rest.domain.content.dto;

import hello.rest.domain.content.entity.Article;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleDto {
    private long id;
    private String title;
    private String content;

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }

    public ArticleDto(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public ArticleDto(long id) {
        this.id = id;
    }
}
