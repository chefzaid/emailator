package com.emailator.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.emailator.utils.Constants;

@Component
@Configuration
@PropertySource({
	"classpath:mail.properties", 
	//More priority:
	"file:${" + Constants.KEY_RESOURCES_PATH + "}/mail.properties"
})
public class ConfigurationLoader {
	@Autowired
	private Environment env;

	public String getProperty(String key) {
		return env.getProperty(key);
	}

}
