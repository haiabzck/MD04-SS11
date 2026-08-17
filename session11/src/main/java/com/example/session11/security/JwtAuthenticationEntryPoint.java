package com.example.session11.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 1. Set trạng thái lỗi 401 (Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 2. Tạo nội dung trả về
        // Nếu không có thì lấy message mặc định của Spring
        String errorMessage = (String) request.getAttribute("error_message");
        if (errorMessage == null) {
            errorMessage = "Unauthorized: Token không hợp lệ hoặc đã hết hạn";
        }

        Map<String, Object> body = new HashMap<>();
        body.put("status", "FAIL");
        body.put("message", errorMessage);
        body.put("code", HttpServletResponse.SC_UNAUTHORIZED);

        // 3. Ghi JSON ra response body bằng ObjectMapper
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
