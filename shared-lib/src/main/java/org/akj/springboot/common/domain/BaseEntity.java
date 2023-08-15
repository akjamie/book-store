package org.akj.springboot.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @CreatedDate
    @JsonIgnore
    protected Instant createdAt;

    @LastModifiedDate
    @JsonIgnore
    protected Instant updatedAt;

    @CreatedBy
    @JsonIgnore
    protected String createdBy;

    @LastModifiedBy
    @JsonIgnore
    protected String updatedBy;
}
