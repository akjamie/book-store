package org.akj.springboot.user.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.user.constant.Constant;
import org.akj.springboot.user.security.provider.MobileSmsCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MobileSmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private boolean postOnly = true;

	private String mobileParameter = Constant.SPRING_SECURITY_FORM_MOBILE_KEY;
	private String codeParameter = Constant.SPRING_SECURITY_FORM_SMSCODE_KEY;
	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(Constant.DEFAULT_SIGN_IN_PROCESSING_URL_SMS,
			"POST");

	public MobileSmsCodeAuthenticationFilter() {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String mobile = obtainPhoneNumber(request);
		mobile = (mobile != null) ? mobile.trim() : "";
		String smsCode = obtainSmsCode(request);
		smsCode = (smsCode != null) ? smsCode : "";
		MobileSmsCodeAuthenticationToken authRequest = MobileSmsCodeAuthenticationToken.unauthenticated(mobile,
				smsCode);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void setDetails(HttpServletRequest request, MobileSmsCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	protected String obtainPhoneNumber(HttpServletRequest request) {
		return request.getParameter(mobileParameter);
	}

	protected String obtainSmsCode(HttpServletRequest request) {
		return request.getParameter(codeParameter);
	}

}
