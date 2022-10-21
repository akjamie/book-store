package org.akj.springboot.user.repository;

import org.akj.springboot.user.domain.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query(value = "select auth.* from authorities auth " +
            "left join group_authorities ga on auth.id=ga.authority_id " +
            "left join `groups` gp on gp.id=ga.group_id " +
            "left join group_members gm on gm.group_id=gp.id " +
            "left join users u on u.id=gm.user_id " +
            "where gm.user_id=:userId", nativeQuery = true)
    List<Authority> findAllAuthoritiesByUserId(Long userId);
}

