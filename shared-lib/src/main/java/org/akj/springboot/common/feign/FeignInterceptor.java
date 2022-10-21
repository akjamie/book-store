package org.akj.springboot.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Set;

@Component
public class FeignInterceptor implements RequestInterceptor {
	private final Set<String> allowHeaders = Set.of("authorization", "content-type");

	@Override
	public void apply(RequestTemplate requestTemplate) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (null != attributes) {
			HttpServletRequest request = attributes.getRequest();
			if (null != request) {
				Enumeration<String> headerNames = request.getHeaderNames();
				if (headerNames != null) {
					while (headerNames.hasMoreElements()) {
						String name = headerNames.nextElement();
						String values = request.getHeader(name);
						if (allowHeaders.contains(name)) {
							requestTemplate.header(name, values);
						}
					}
				}
			}
		}
	}
}
