package com.sintmo.scs.hiberate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sintmo.scs.hiberate.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByNickname(String nickname);

}
