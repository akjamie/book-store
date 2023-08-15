package org.akj.springboot.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
	private String code;
	private String message;
	private Object data;

	private Result(String code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	private Result(Object data) {
		this(null, null, data);
	}

	private Result(String code, String message) {
		this(code, message, null);
	}

	public static Result failure(String code, String message, Object data) {
		return new Result(code, message, data);
	}

	public static Result success(Object body) {
		return new Result(body);
	}

	public static Result success() {
		return new Result(null);
	}

	public static Result ok() {
		return new Result(null);
	}

	public static Result failure(String code, String message) {
		return new Result(code, message);
	}
}
