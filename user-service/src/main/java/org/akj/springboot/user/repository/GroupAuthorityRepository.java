package org.akj.springboot.user.repository;

import org.akj.springboot.user.domain.entity.GroupAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupAuthorityRepository extends JpaRepository<GroupAuthority, Long> {
}

