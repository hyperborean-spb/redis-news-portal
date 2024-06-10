package pro.amstepanov.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DtoSerializationException extends RuntimeException {
	private final String message;
}