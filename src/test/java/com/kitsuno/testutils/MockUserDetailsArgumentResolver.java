package com.kitsuno.testutils;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collections;

public class MockUserDetailsArgumentResolver implements HandlerMethodArgumentResolver {

    private final boolean isAuthenticated;

    public MockUserDetailsArgumentResolver(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserDetails.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (isAuthenticated) {
            User user = new User("testuser", "password", Collections.singleton(
                    new SimpleGrantedAuthority("ROLE_USER")));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    user.getAuthorities());
        }

        return null;
    }
}
