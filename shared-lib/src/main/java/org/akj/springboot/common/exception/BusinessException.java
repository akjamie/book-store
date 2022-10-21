package org.akj.springboot.common.exception;

import java.io.Serializable;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessException extends BaseException implements Serializable {
	public BusinessException(String code, String message) {
		super(code, message);
	}

	public BusinessException(String message, String code, Throwable e) {
		super(message, code, e);
	}
}
