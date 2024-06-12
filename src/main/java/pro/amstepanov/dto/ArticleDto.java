package pro.amstepanov.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String content;

	private Double averageScore;
}