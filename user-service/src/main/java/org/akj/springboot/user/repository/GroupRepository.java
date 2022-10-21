package org.akj.springboot.user.repository;

import org.akj.springboot.user.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select g.* from `groups` g left join `group_members` gm on g.id = gm.group_Id where gm.user_Id=:userId",
            nativeQuery = true)
    List<Group> findAllByUserId(Long userId);
}

