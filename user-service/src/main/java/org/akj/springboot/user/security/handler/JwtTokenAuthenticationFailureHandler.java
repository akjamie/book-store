package org.akj.springboot.user.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.common.domain.Result;
import org.akj.springboot.user.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import static org.akj.springboot.user.constant.Constant.SPRING_SECURITY_FORM_MOBILE_KEY;
import static org.akj.springboot.user.constant.Constant.SPRING_SECURITY_FORM_USERNAME_KEY;
import static org.akj.springboot.user.exception.ErrorCodeMap.AUTHENTICATION_FAILED;

@Component
@Slf4j
public class JwtTokenAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final UserRepository userRepository;

    private final ObjectMapper mapper;

    public JwtTokenAuthenticationFailureHandler(UserRepository userRepository, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
            //todo: handle authentication fail, like add up the count to db.
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Result result = Result.failure(AUTHENTICATION_FAILED.code(), AUTHENTICATION_FAILED.message());
            PrintWriter writer = response.getWriter();
            writer.write(mapper.writeValueAsString(result));
            writer.close();
    }


    private AuthParam obtainUserIdentifier(HttpServletRequest request) {
        List<String> potentialParameterKeys = List.of(SPRING_SECURITY_FORM_USERNAME_KEY, SPRING_SECURITY_FORM_MOBILE_KEY);
        AuthParam param = null;
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String element = enumeration.nextElement();
            if (potentialParameterKeys.contains(element)) {
                param = AuthParam.builder().key(element).value(request.getParameter(element)).build();
                break;
            }
        }

        return param;
    }


    @Data
    @Builder
    static class AuthParam {
        private String key;
        private String value;
    }
}
