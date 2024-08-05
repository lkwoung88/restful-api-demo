package hello.rest.domain.content.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    private String content;
}
