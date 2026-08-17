package com.example.session11.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRegisterResponse {
    private String username;
    private String fullName;
    private String email;
    private String role ;
}
