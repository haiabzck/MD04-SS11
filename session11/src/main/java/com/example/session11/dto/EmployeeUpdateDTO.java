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
public class EmployeeUpdateDTO {
    @NotBlank
    @Size(min=5,message = "Tên phải có ít nhất 5 ký tự")
    private String fullName;
    @Email(message = "Email không hợp lệ")
    private String email;
    private MultipartFile avatarUrl;
}
