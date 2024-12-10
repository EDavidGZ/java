package com.edgz.authudemy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.PersistenceConstructor;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users ",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(name = "email")
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Tokens> tokens = new HashSet<>();

    @OneToMany(mappedBy = "recovery_user", cascade = CascadeType.ALL,  orphanRemoval = true)
    @JsonIgnore
    private Set<PasswordRecovery> passwordRecoveries = new HashSet<>();


    public static User of(String firstName, String lastName, String email, String password) {
        return new User(null, firstName, lastName, email, password, new HashSet<>(), new HashSet<>());
    }

    @PersistenceConstructor
    public User(Long id, String firstName, String lastName, String email, String password, Set<Tokens> tokens,  Set<PasswordRecovery> passwordRecoveries) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.tokens = tokens;
        this.passwordRecoveries = passwordRecoveries;
    }


//    public void addToken(Clave clave) {
//        this.tokens.add((Tokens) tokens);
//    }

    public void addToken(Tokens token) {
        token.setUser(this);  // Asociar token con usuario
        this.tokens.add(token);
    }


//    public Boolean removeToken(Tokens token) {
//        return this.tokens.remove(token);
//    }


    public Boolean removeTokenIf(Predicate<? super Tokens> predicate) {
        return this.tokens.removeIf(predicate);
    }

    public void addPasswordRecovery(PasswordRecovery passwordRecovery) {
        if (passwordRecovery != null && !this.passwordRecoveries.contains(passwordRecovery)) {
            passwordRecovery.setUser(this); // Establecer la relación bidireccional
            this.passwordRecoveries.add(passwordRecovery);
        }
    }


//    public Boolean removePasswordRecovery(PasswordRecovery passwordRecovery) {
//        return this.passwordRecoveries.remove(passwordRecovery);
//    }

    public Boolean removePasswordRecoveryIf(Predicate<? super PasswordRecovery> predicate) {
        return this.passwordRecoveries.removeIf(predicate);
    }
}
