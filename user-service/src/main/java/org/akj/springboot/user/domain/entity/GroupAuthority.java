package org.akj.springboot.user.domain.entity;

import lombok.Data;
import org.akj.springboot.common.domain.BaseEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "group_authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"groupId", "authorityId"})})
@Data
public class GroupAuthority extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long groupId;

    @Column(nullable = false)
    private Long authorityId;

    private String remark;
}
