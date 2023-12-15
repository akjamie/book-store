package org.akj.springboot.user.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.akj.springboot.common.domain.BaseEntity;
import org.akj.springboot.user.domain.AuthType;
import org.akj.springboot.user.domain.UserStatus;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@ToString(callSuper = true)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    private String password;

    private String aliasName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthType authType;

    @NotNull
    @Pattern(regexp = "^(?:(?:\\+|00)86)?1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$")
    @Column(nullable = true, unique = true)
    private String phoneNumber;

    @Pattern(regexp = "^(.+)@ (.+)$")
    @Column(nullable = true, unique = true)
    private String email;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private Integer failedLoginAttempts;
    private Instant lockedTime;
}