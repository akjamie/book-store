package org.akj.springboot.authorization.repository;

import org.akj.springboot.authorization.domain.iam.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	@Query(nativeQuery = true, value = "select g.id, g.name, g.description from groups g\n" +
			"left join group_members gm on gm.group_id=g.id\n" +
			"where gm.user_id=1")
	List<Group> findAllGroupsByUserId(Long userId);
}
