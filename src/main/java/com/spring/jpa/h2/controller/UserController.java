package com.spring.jpa.h2.controller;

import com.spring.jpa.h2.model.User;
import com.spring.jpa.h2.service.UserService;
import com.spring.jpa.h2.vo.ListEmailVO;
import com.spring.jpa.h2.vo.ObfuscationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {


    @Autowired
    UserService usersService;

    @PostMapping("")
    public ResponseEntity<List<ObfuscationVO>> getAllByEmail(@RequestBody ListEmailVO list) {
        List<ObfuscationVO>  users =  usersService.matches(list);
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAll() {
        List<User>  users =  usersService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

}
