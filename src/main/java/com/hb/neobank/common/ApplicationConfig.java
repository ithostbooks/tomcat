package com.hb.neobank.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.reqlimitcheck")
public class ApplicationConfig {

	private boolean enable;
}
