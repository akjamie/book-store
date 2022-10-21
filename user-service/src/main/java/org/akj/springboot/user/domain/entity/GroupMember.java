package org.akj.springboot.user.domain.entity;

import lombok.Data;
import org.akj.springboot.common.domain.BaseEntity;

import javax.persistence.*;

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
