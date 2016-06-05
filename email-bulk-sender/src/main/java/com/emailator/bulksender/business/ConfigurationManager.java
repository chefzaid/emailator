package com.emailator.bulksender.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.emailator.bulksender.utils.Constants;

import lombok.extern.apachecommons.CommonsLog;

@Component
@Configuration
@PropertySource({ 
	"classpath:mail.properties",
	// More priority:
	"file:${" + Constants.KEY_RESOURCES_PATH + "}/mail.properties" 
})
@CommonsLog
public class ConfigurationManager {
	
	@Autowired
	private Environment environment;

	public String getProperty(String key) {
		log.debug("Loading configuration property: " + key);
		return environment.getProperty(key);
	}

}
