package com.senla.course.security.model;

import javax.persistence.*;
import java.util.List;

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
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user"), inverseJoinColumns = @JoinColumn(name = "role"))
    private List<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
