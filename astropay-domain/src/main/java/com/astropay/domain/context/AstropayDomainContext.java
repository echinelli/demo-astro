package com.astropay.domain.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.astropay.domain.config",
		"com.astropay.repository",
		"com.astropay.domain.service" })
public class AstropayDomainContext {

}
