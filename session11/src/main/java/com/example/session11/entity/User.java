package com.example.session11.entity;

import com.example.session11.constant.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String fullName;
    private String email;
    @Builder.Default
    private boolean enabled = false; // Mặc định là chưa kích hoạt
    private String otpCode;
    private LocalDateTime otpExpiration;

    @Enumerated(EnumType.STRING)
    private Role role;

}
