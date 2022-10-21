package org.akj.springboot.authorization.domain.iam.entity;

import lombok.Data;
import lombok.ToString;
import org.akj.springboot.authorization.domain.iam.AuthType;
import org.akj.springboot.authorization.domain.iam.UserStatus;
import org.akj.springboot.common.domain.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;

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