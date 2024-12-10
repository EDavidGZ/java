package com.edgz.authudemy.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "token")
public class Tokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Tokens(Long id, String refreshToken, LocalDateTime expiredAt, LocalDateTime issuedAt, User user) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
        this.issuedAt = issuedAt;
        this.user = user;
    }

    public Tokens(String refreshToken, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.refreshToken = refreshToken;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }
}
