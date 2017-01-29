package co.com.business.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import co.com.business.service.component.CustomAuthenticationEntryPoint;
import co.com.business.service.utils.Constantes;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources
			.resourceId(Constantes.RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		   .exceptionHandling()
		   .authenticationEntryPoint(customAuthenticationEntryPoint)
		  .and() 
           .csrf()
           .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
           .disable()
           .headers()
           .frameOptions().disable()
          .and()
           .sessionManagement()
           .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and() 
           .authorizeRequests()
           .antMatchers("/api/v1.0/secure").hasRole("ADMIN")
           .antMatchers("/api/v1.0/**").authenticated()
          .and()
           .logout()
           .logoutUrl("/oauth/logout");
	}

}