package com.spring.jpa.h2.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.spring.jpa.h2.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class ListEmailVoTest {

    @Test
    void testToJson() throws JsonProcessingException {
        List<UserEmailVO> listUser = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            listUser.add(new UserEmailVO("email" + i + "@email.com", UUID.randomUUID().toString()));
        }

        ListEmailVO listEmail = ListEmailVO.builder()
                .applicantToken("111-111")
                .emailList(listUser)
                .build();


        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(listEmail);
        System.out.printf(json);
    }

    @Test
    void compare2lists() {
        List<UserEmailVO> listUser = new ArrayList<>();
        List<User> findUser = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            listUser.add(new UserEmailVO("email" + i + "@email.com", UUID.randomUUID().toString()));
        }

        List<ObfuscationVO> listObfuscation = copyList(listUser);


        findUser.add(User.builder().email("email1@email.com").build());
        findUser.add(User.builder().email("email2@email.com").build());
        findUser.add(User.builder().email("email3@email.com").build());

        // We create a stream of elements from the first list.
        matches(findUser, listObfuscation);

        System.out.println(listObfuscation);
    }

    private void matches(List<User> findUser, List<ObfuscationVO> listObfuscation) {
        listObfuscation.stream()
                .parallel()
                .filter(one -> findUser.stream()
                        .anyMatch(two -> isMatches(one, two)))
                .forEach(matches -> {
                    matches.setResult(true);
                });
    }

    private List<ObfuscationVO> copyList(List<UserEmailVO> listUser) {
        List<ObfuscationVO> listObfuscation = listUser.stream()
                .map(user -> ObfuscationVO.builder()
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
        return listObfuscation;
    }

    private boolean isMatches(ObfuscationVO two, User one) {
        return one.getEmail().equals(two.getEmail());
    }
}