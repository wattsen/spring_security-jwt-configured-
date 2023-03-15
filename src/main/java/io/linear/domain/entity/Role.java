package io.linear.domain.entity;

import io.linear.domain.entity.baseEntity.BaseEntity;
import io.linear.domain.entity.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private UserRoleEnum role;
}
