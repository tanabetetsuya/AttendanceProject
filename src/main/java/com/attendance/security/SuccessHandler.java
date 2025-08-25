package com.attendance.security;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // ユーザの権限を取得
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // 権限ごとに遷移先を分岐
        for (GrantedAuthority authority : authorities) {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                response.sendRedirect("/admin/index"); // 管理者は /admin/index
                return;
            } else if ("ROLE_USER".equals(authority.getAuthority())) {
                response.sendRedirect("/user/AttendanceHandling"); // 一般ユーザは /user/index
                return;
            }
        }

        // どのロールでもなければデフォルトでユーザ画面へ
        response.sendRedirect("/user/index");
    }
}