package org.akj.springboot.common.feign;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.akj.springboot.common.domain.Result;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;

public class FeignResultDecoder implements Decoder {
	private final ObjectMapper mapper;

	public FeignResultDecoder(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
		if (response.body() == null) {
			return null;
		}

		String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
		Result result = (Result) parse(bodyStr, Result.class);

		if (StringUtils.isNotEmpty(result.getCode())) {
			throw new DecodeException(response.status(), "Unexpected response returned：" + result.getMessage(), response.request());
		}
		return parse(result.getData(), type);
	}

	private <T> T parse(String jsonStr, Type targetType) {
		try {
			JavaType javaType = TypeFactory.defaultInstance().constructType(targetType);
			return mapper.readValue(jsonStr, javaType);
		} catch (IOException e) {
			throw new IllegalArgumentException("Parse response to Result failed," + jsonStr, e);
		}
	}

	private <T> T parse(Object data, Type targetType) {
		try {
			JavaType javaType = TypeFactory.defaultInstance().constructType(targetType);
			return mapper.readValue(mapper.writeValueAsString(data), javaType);
		} catch (IOException e) {
			throw new IllegalArgumentException("Parse response failed," + data, e);
		}
	}
}
