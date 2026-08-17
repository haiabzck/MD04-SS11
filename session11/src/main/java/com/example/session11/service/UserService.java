package com.example.session11.service;

import com.example.session11.dto.UserRegisterDTO;
import com.example.session11.dto.request.LoginRequest;
import com.example.session11.dto.request.UserRegisterResponse;
import com.example.session11.dto.response.LoginResponse;
import com.example.session11.entity.User;
import com.example.session11.repository.UserRepository;
import com.example.session11.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.session11.constant.Role;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final JwtProvider jwtProvider;

    public UserRegisterResponse register(UserRegisterDTO userRegisterDTO) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        User user = User.builder()
                .username(userRegisterDTO.getUsername())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .role(Role.USER)
                .enabled(false)
                .fullName(userRegisterDTO.getFullName())
                .email(userRegisterDTO.getEmail())
                .otpCode(otp)
                .otpExpiration(LocalDateTime.now().plusMinutes(5))
                .build();

        mailService.sendMail(userRegisterDTO.getEmail(),otp);
        User newUser = userRepository.save(user);
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setUsername(user.getUsername());
        userRegisterResponse.setFullName(user.getFullName());
        userRegisterResponse.setEmail(user.getEmail());
        userRegisterResponse.setRole(newUser.getRole().toString());
        return userRegisterResponse;
    }
    public ResponseEntity<?> login(LoginRequest request ){
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        Map<String,String> map = new HashMap<>();
        if(user == null){
            map.put("error","Username hoặc password không chính xác !");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }else {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())){
                if (user.isEnabled()){
                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setUsername(user.getUsername());
                    loginResponse.setType("Bearer");
                    loginResponse.setAccess_token(jwtProvider.generateToken(user));
                    return new ResponseEntity<>(loginResponse, HttpStatus.OK);

                }else {
                    map.put("error" , "Vui lòng active tài khoản trước khi đăng nhập !");
                    return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
                }
            }else {
                map.put("error","Username hoặc password không chính xác !");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
