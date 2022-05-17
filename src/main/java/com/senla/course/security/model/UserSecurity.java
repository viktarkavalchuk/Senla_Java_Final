package com.senla.course.security.model;

import javax.persistence.*;

@Entity
@Table(name = "user_security", schema = "private_announcements")
public class UserSecurity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUser_Security")
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "idUser")
    private String idUser;

}
