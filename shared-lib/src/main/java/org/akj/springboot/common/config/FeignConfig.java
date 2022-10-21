package org.akj.springboot.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.akj.springboot.common.feign.FeignResultDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
	@Autowired
	ObjectMapper objectMapper;

	@Bean
	public Decoder feignDecoder() {
		return new FeignResultDecoder(objectMapper);
	}
}
