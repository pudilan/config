package com.sintmo.scs.hiberate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 6427942221750010894L;

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长
    private Long id;

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "avatar", nullable = true)
    private String avatar;

    @Column(name = "address", nullable = true)
    private String address;

    public User(String uid, String nickname, String avatar) {
        this.uid = uid;
        this.nickname = nickname;
        this.avatar = avatar;
    }

}
