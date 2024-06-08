package pro.amstepanov.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleNotFoundException extends RuntimeException {
	private final String message;
}