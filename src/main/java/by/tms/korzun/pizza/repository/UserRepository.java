package by.tms.korzun.pizza.repository;

import by.tms.korzun.pizza.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
