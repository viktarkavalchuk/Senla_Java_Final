package com.senla.course.security.model;

import javax.persistence.*;

@Entity
@Table(name = "role", schema = "private_announcements")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_Role")
    private int id;
    @Column(name = "role")
    private String role;

}
