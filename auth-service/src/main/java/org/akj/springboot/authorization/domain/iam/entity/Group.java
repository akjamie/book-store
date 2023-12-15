package org.akj.springboot.authorization.domain.iam.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.akj.springboot.common.domain.BaseEntity;

import jakarta.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "groups")
@Data
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    private String description;
}
