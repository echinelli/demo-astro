package com.astropay.service.context;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.astropay.domain.context.AstropayDomainContext;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * Astropay Service Context
 *
 * @author echinelli
 */
@Configuration
@EnableWebMvc
@Import(AstropayDomainContext.class)
@ComponentScan(basePackages = { "com.astropay.service.config",
		"com.astropay.service.controller" })
public class AstropayServiceContext extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {

		MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper
				.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		jacksonConverter.setObjectMapper(objectMapper);

		converters.add(jacksonConverter);
	}
}
