package com.spring.jpa.h2.service;

import com.spring.jpa.h2.model.User;
import com.spring.jpa.h2.repository.UserRepository;
import com.spring.jpa.h2.vo.ListEmailVO;
import com.spring.jpa.h2.vo.ObfuscationVO;
import com.spring.jpa.h2.vo.UserEmailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllByEmailIn(ListEmailVO list) {
        List<String> emails = list.getEmailList()
                .stream()
                .parallel()
                .map(UserEmailVO::getEmail)
                .collect(Collectors.toList());

        return userRepository.findAllByEmailIn(emails);
    }

    private boolean isMatchesEmailAndPass(ObfuscationVO two, User one) {
        return one.getEmail().equals(two.getEmail());
    }

    public List<ObfuscationVO> matches(ListEmailVO emailList) {
        List<User> users = findAllByEmailIn(emailList);
        List<ObfuscationVO> obfuscationList = obfuscationList(emailList.getEmailList());

        compare(users, obfuscationList);
        return obfuscationList;
    }

    private void compare(List<User> users, List<ObfuscationVO> listObfuscation) {
        listObfuscation.stream()
                .parallel()
                .filter(one -> users.stream()
                        .anyMatch(two -> isMatchesEmailAndPass(one, two)))
                .forEach(matches -> matches.setResult(true));
    }


    private List<ObfuscationVO> obfuscationList(List<UserEmailVO> listUser) {
        return listUser.stream()
                .parallel()
                .map(user -> ObfuscationVO.builder()
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }
}
