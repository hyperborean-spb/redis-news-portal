package pro.amstepanov.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String content;
}