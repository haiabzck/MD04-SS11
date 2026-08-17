package com.example.session11.dto.response;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private String type;
    private String access_token;
}
