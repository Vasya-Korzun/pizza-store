package by.tms.korzun.pizza.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String email;

	private String country;

	private LocalDate birthDate;

	private int subscriberCounter;
}
