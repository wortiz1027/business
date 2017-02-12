package co.com.business.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import co.com.business.service.component.CustomAuthenticationEntryPoint;
import co.com.business.service.utils.Constantes;
import net.bull.javamelody.MonitoredWithSpring;

@Configuration
@EnableResourceServer
@MonitoredWithSpring
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private Environment environtment;
	
	@Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Bean
    public RemoteTokenServices remoteTokenServices() {
        final RemoteTokenServices tokenServices = new RemoteTokenServices();
        
        tokenServices.setClientId(environtment.getProperty(Constantes.CLIENT_ID_KEY));
        tokenServices.setClientSecret(environtment.getProperty(Constantes.CLIENT_SECRET_KEY));
        tokenServices.setCheckTokenEndpointUrl(environtment.getProperty(Constantes.SERVER_CHECK_TOKEN_URL_KEY));
        
        return tokenServices;
    }
	
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) {
		resources
                .tokenServices(remoteTokenServices())
                .resourceId(environtment.getProperty(Constantes.RESOURCE_ID_KEY));
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
           .antMatchers("/monitoring/**").hasRole("ADMIN")
           .antMatchers("/api/v1.0/**").authenticated()
          .and()
           .logout()
           .logoutUrl("/oauth/logout")
          .and()
           .exceptionHandling()
           .accessDeniedPage("/403");;
	}

}