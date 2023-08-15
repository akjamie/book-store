package org.akj.springboot.authorization.domain.iam.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.akj.springboot.common.domain.BaseEntity;

import jakarta.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "group_members", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "groupId"})})
@Data
public class GroupMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long groupId;

    private String remark;
}
