package org.akj.springboot.user.domain.entity;

import lombok.Data;
import org.akj.springboot.common.domain.BaseEntity;

import javax.persistence.*;

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
