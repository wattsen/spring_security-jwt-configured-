package io.linear.repository;

import io.linear.domain.entity.Role;
import io.linear.domain.entity.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

		Optional<Role> findByRole(UserRoleEnum role);
}
