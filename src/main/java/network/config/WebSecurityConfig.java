package network.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.formLogin()
				.loginPage("http://junniejobs.xyz:8080/index.html")
				.defaultSuccessUrl("http://junniejobs.xyz:8080/chat.html")
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("http://junniejobs.xyz:8080/index.html")
				.permitAll()
				.and()
			.authorizeRequests()
				.antMatchers("/js/**", "/lib/**", "/images/**", "/css/**", "/index.html", "/").permitAll()
				.anyRequest().authenticated();
				
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(new AuthenticationProvider() {
			
			@Override
			public boolean supports(Class<?> authentication) {
				return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
			}
			
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
				
				List<GrantedAuthority> authorities = null;
														
				return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
			}
		});
	}
}
