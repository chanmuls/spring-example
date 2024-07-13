package com.chanmul.domain.user.entity;

import com.chanmul.domain.user.type.AccountRole;
import com.chanmul.core.exception.ApiException;
import com.chanmul.core.type.ServiceErrorType;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class Account {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String nickname;
    @Column
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder(builderMethodName = "signUpBuilder")
    public Account(String username, String name, String nickname, String password, String phoneNumber) {
        this.username = username;
        this.email = username;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = Stream.of(AccountRole.GUEST)
                .collect(Collectors.toSet());

        this.validation();
    }

    private void validation() {
        if (this.username == null || this.username.isEmpty()
            || this.email == null || this.email.isEmpty()
            || this.name == null || this.name.isEmpty()
            || this.nickname == null || this.nickname.isEmpty()
            || this.password == null || this.password.isEmpty()
            || this.phoneNumber == null || this.phoneNumber.isEmpty()) {
            throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
        }
    }
}
