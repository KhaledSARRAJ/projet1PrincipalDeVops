package fr.eql.projet01.springSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//necessary for @PreAuthorize("hasRole('ADMIN or ...')")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService)
		.passwordEncoder(passwordEncoder);
	}

	// roles admin allow to access /admin/**
	// roles user allow to access /user/**
	// custom 403 access denied handler


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/", "/index.html", "/**/*.css", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.css", "/**/*.js", "/home", "/inscription", "/traitement-inscription", "/about").permitAll()
		.antMatchers("/session-end").permitAll()
		.antMatchers("/aec-api-rest/annonces/**").permitAll()
		.antMatchers("/aec-api-rest/supports/**").permitAll()
		.antMatchers("/aec-api-rest/themes/**").permitAll()
		.antMatchers("/chat-api-rest/**").permitAll()
		.antMatchers("/administrateur/**").permitAll()
		.antMatchers("http://al35-store-admin-panel.s3-website.eu-west-3.amazonaws.com/**/**").hasAnyRole("ADMIN")
		.antMatchers("/user/**").hasAnyRole("USER")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.and()
		.logout()
		.permitAll()
		.and()
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler);		
	}
}