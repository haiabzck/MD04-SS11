package com.example.session11.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailServiceCustom userDetailsServiceCustom;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Lấy token từ header
            String token = getJwtFromRequest(request);

            // Kiểm tra token và validate
            if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsServiceCustom.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            // Token hết hạn
            e.printStackTrace();
            request.setAttribute("error_message", "Token đã hết hạn vui lòng đăng nhập lại!");
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            // Token sai định dạng (vỡ, sửa đổi...)
            request.setAttribute("error_message", "Token không đúng định dạng!");
        } catch (Exception e) {
            e.printStackTrace();
            // Lỗi chung khác
            request.setAttribute("error_message", "Lỗi xác thực Token: " + e.getMessage());
        }

        // Quan trọng: Dù lỗi hay không, vẫn cho filter chạy tiếp.
        // Nếu lỗi -> SecurityContext rỗng -> Spring Security sẽ chặn ở cửa sau và gọi EntryPoint.
        filterChain.doFilter(request, response);
    }

    // Hàm getJwtFromRequest giữ nguyên như bài trước
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
