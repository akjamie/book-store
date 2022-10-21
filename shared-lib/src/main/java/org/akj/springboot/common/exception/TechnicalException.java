package org.akj.springboot.common.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TechnicalException extends BaseException implements Serializable {

	public TechnicalException(String code, String message) {
		super(code, message);
	}

	public TechnicalException(String message, String code, Throwable e) {
		super(message, code, e);
	}
}
