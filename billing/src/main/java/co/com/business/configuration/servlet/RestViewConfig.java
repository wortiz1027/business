package co.com.business.configuration.servlet;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import co.com.business.service.utils.Constantes;

@EnableWebMvc
@Configuration
@ComponentScan({ "co.com.business.*" })
public class RestViewConfig {
	
	@Resource
	private Environment env;
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Bean(name="multipartResolver") 
    public CommonsMultipartResolver getResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
         
        resolver.setMaxUploadSizePerFile(Constantes.MAX_UPLOAD_SIZE);
         
        return resolver;
    }
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename(env.getRequiredProperty(Constantes.BASE_MESSAGES_KEY));
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}
	
}