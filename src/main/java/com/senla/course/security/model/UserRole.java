package com.senla.course.security.model;


import javax.persistence.*;

@Entity
@Table(name = "user_role", schema = "private_announcements")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUser_Role")
    private int id;
    @Column(name = "user")
    private String user;
    @Column(name = "role")
    private String role;


}
