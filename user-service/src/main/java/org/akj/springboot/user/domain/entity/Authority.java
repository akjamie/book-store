package org.akj.springboot.user.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.akj.springboot.common.domain.BaseEntity;

import jakarta.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "authorities")
@Data
public class Authority extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;
}
