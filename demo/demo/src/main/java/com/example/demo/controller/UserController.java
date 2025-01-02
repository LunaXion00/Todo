package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try{
            // 사용자는 이메일, 닉네임, 비밀번호에 대한 정보를 제공하기 때문에 이를 UserDTO로 받아서 UserEntity 생성.
            UserEntity user = UserEntity.builder().email(userDTO.getEmail()).username(userDTO.getUsername()).password(userDTO.getPassword()).build();
            // 검증 절차를 거친 후 유효하다면 user를 repo에 저장.
            UserEntity registeredUser = userService.create(user);
            // 무사히 저장된(새로 생성된) 경우라면 response.
            UserDTO responseUserDTO = UserDTO.builder().email(registeredUser.getEmail()).id(registeredUser.getId()).username(registeredUser.getUsername()).build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword());
        if(user!= null){
            final UserDTO responseUserDTO = UserDTO.builder().email(user.getEmail()).id(user.getId()).build();
            return ResponseEntity.ok().body(responseUserDTO);
        }
        else{
            ResponseDTO responseDTO = ResponseDTO.builder().error("Login Failed.").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
