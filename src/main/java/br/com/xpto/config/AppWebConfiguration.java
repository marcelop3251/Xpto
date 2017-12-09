package br.com.xpto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


/**
 * 
 * @author Marcelo
 * Classe de configuração do servlet do spring mvc
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages ={"br.com.xpto.restcontroller","br.com.xpto.controller","br.com.xpto.infra","br.com.xpto.services"})  
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	
	/**
	 * Resolvedpr de views do spring, determina onde encontrar as views jsp
	 * @return o resolvedor
	 */
	@Bean /** Determina que esse método deve ser gerênciado pelo spring*/
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	

	/** Informa ao spring as configurções default, deixando passar as requisições default para o container*/
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// 
		configurer.enable();
	}
	
	@Bean
	public MultipartResolver multipartResolver(){
		return new StandardServletMultipartResolver();
	}
	
	
}
