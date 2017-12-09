package br.com.xpto.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 
 * @author Marcelo
 * Classe de configuração do spring para atender todas as requisições que chega para a aplicação 
 */
public class ServletSpring extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{ AppWebConfiguration.class, JPAConfigurator.class, JPAProductionConfiguration.class};
	}

	/**
	 * Esse método recebe um array de classes de configurações
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}

	/**
	 * Recebe um array com o  mapeamento que queremos que nosso servlet atenda
	 */
	@Override
	protected String[] getServletMappings() {
		/** definimos que  servlet atenderá as requisições a partir da raiz do nosso projeto*/
		return new String[]{"/"};
		
	}
	
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(RequestContextListener.class);
		servletContext.setInitParameter("spring.profiles.active", "prod");
	}

}
