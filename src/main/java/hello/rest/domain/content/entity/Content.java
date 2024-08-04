package hello.rest.domain.content.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "t_contents")
public class Content extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    private String content;
}
