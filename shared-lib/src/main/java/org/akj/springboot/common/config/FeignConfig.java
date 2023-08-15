package org.akj.springboot.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.akj.springboot.common.feign.FeignResultDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
	private final ObjectMapper objectMapper;

	public FeignConfig(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Bean
	public Decoder feignDecoder() {
		return new FeignResultDecoder(objectMapper);
	}
}
