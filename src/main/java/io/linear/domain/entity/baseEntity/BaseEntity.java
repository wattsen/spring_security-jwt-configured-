package io.linear.domain.entity.baseEntity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This Class will represent ID for all entities,
 * so I do not need to declare ID for each entity
*/

@MappedSuperclass
@NoArgsConstructor
@Data
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
}
