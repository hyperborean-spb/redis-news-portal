package pro.amstepanov.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne  (fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	private Article article;

	private String content;
	private  Integer  rating;
}