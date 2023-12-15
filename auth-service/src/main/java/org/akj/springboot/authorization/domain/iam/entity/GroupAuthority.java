package org.akj.springboot.authorization.domain.iam.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.akj.springboot.common.domain.BaseEntity;

import jakarta.persistence.*;

@EqualsAndHashCode(callSuper = true)
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
