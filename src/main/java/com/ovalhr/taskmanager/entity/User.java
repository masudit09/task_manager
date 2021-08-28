package com.ovalhr.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ovalhr.taskmanager.enumeration.Role;
import com.ovalhr.taskmanager.util.Util;
//import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rana on 8/27/21.
 */

@Entity
@Table(name = "USERS")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "User.findById", query = "SELECT e FROM User e WHERE e.id = :id")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator( name = "USER_SEQUENCE_GENERATOR", sequenceName = "USER_ID_SEQUENCE" )
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQUENCE_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, precision = 38, scale = 0)
    private Long id;

    @Column(name = "USERNAME", length = 50)
    @NotEmpty(message = "Username is required.")
    private String username;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Transient
    @NotNull(message = "plainPassword is required.")
    private String plainPassword;

    @Column(name = "FULL_NAME", length = 100)
    private String fullName;

    @Column(name = "ENABLED")
    @NotNull(message = "User should enabled of not")
    private Boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy = "username")
    private List<Authority> roles = new ArrayList<Authority>();

    @JsonIgnore
    @Transient
    private Set<String> authorities = new HashSet<String>() ;

    @Transient
    @Enumerated(EnumType.STRING)
    @NotNull(message = "role is required.")
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getRoles() {
        return roles;
    }

    public void setRoles(List<Authority> roles) {
        this.roles = roles;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @PrePersist
    void onPrePersist() {
        if (!StringUtils.isEmpty(getPlainPassword())) {
            setPassword(Util.getEncoder().encode(getPlainPassword()));
        }
    }

//    public void setRolesFromAuthorities(List<Authority> authorities) {
//        if(authorities == null) {
//            this.roles = null;
//            return;
//        }
//
//        this.roles = new ArrayList<Role>();
//        for(Authority authority : authorities) {
//            this.roles.add(Role.valueOf(authority.getAuthority()));
//        }
//    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setListAuthorities(List<Authority> authorities) {
        for(Authority authority: authorities) {
            this.authorities.add(authority.getAuthority());
        }
    }



}
