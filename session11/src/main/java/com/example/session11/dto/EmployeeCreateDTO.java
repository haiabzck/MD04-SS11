package com.example.session11.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateDTO {
    @NotBlank(message = "Tên không được để trống") @Size(min=5)
    private String fullName;
    @Email(message = "Email không hợp lệ")
    private String email;
    private String department;
    private MultipartFile avatarUrl;
}
