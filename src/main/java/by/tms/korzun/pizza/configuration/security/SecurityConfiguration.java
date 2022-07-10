package by.tms.korzun.pizza.configuration.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${jwt.token.secret}")
	private String secret;

	private final JwtUtil jwtUtil;
	private final LoadUserDetailService userDetailService;


	@Autowired
	public SecurityConfiguration(JwtUtil jwtUtil, LoadUserDetailService userDetailService){
		super();
		this.jwtUtil = jwtUtil;
		this.userDetailService = userDetailService;
	}



	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public JwtRequestFilter requestFilter(){
		return new JwtRequestFilter(userDetailService, jwtUtil);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailService).passwordEncoder(getEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
				.disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/pizzas/**", "/api/orders/**", "/api/orders").hasAnyAuthority(UserRole.CUSTOMER.name(), UserRole.ADMIN.name())
				.antMatchers("/api/version").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(requestFilter(), UsernamePasswordAuthenticationFilter.class);
		http
				.headers().frameOptions().sameOrigin();
	}

	@Bean
	public PasswordEncoder getEncoder(){
		return new Pbkdf2PasswordEncoder(secret);
	}

}
