package com.spring.jpa.h2.repository;

import com.spring.jpa.h2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findAllByEmailIn(@Param("emails") List<String> emails);
  List<User> findAll();

}
