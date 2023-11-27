package com.hookahShop.HookahShop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

	    @Value("${project.n}")
	        private String urlContextPath;

	        public void addViewControllers(ViewControllerRegistry registry) {
			        registry.addViewController("/").setViewName("home");
				        registry.addViewController("/login").setViewName("login");
					    }

		    @Bean
		        public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
				        if (urlContextPath != null && !urlContextPath.isEmpty()) {
						            return factory -> factory.setContextPath("/" + urlContextPath);
							            }
					        return factory -> factory.setContextPath("");
						    }

}
