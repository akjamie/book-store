package org.akj.springboot.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BaseException extends RuntimeException implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7160449079887288990L;

	private String message = null;

	private String code = null;

	public BaseException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BaseException(String message, String code, Throwable e) {
		super(message, e);
		this.message = message;
		this.code = code;
	}

}
