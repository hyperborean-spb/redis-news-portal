package pro.amstepanov.dto;

import lombok.*;
import java.io.Serializable;

@Value
public class ArticleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String content;

	private Double averageScore;
}