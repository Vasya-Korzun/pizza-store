package by.tms.korzun.pizza.entity;

import by.tms.korzun.pizza.configuration.security.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_sequence")
	@SequenceGenerator(name = "users_id_sequence", sequenceName = "users_id_sequence", allocationSize = 1)
	private Long id;

	@Column(name="user_name")
	private String username;

	@Column(name = "password")
	private String encodedPassword;

	@Email(message = "email is not valid")
	@Column(name = "email")
	private String email;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(name = "reset_token")
	private String resetToken;

	@Column(name = "force_reset")
	private boolean forceReset;

	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private Status status;
}
