package srduck.dao;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="USERS")
public class User {

    @Id
    private String login;
    private String password;

    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name = "ROLES_OF_USERS",
            joinColumns={@JoinColumn(name="LOGIN")},
            inverseJoinColumns={@JoinColumn(name="NAME")})
    Set<Role> roles = new HashSet<Role>();

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
