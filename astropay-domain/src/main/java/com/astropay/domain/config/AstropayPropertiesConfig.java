package com.astropay.domain.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class AstropayPropertiesConfig {

	@Bean
	public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer p = new PropertyPlaceholderConfigurer();
		ClassPathResource desa = new ClassPathResource(
				"astropay_dsa.properties");
		if (desa.exists()) {
			p.setLocations(desa);
		} else {
			p.setLocations(new ClassPathResource("astropay_prd.properties"));
		}
		return p;
	}
}
