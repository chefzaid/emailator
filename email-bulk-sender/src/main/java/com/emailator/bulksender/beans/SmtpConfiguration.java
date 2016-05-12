package com.emailator.bulksender.beans;

import lombok.Data;

@Data
public class SmtpConfiguration {
	private String host;
	private Integer port;
	private Boolean enableAuthentication;
	private Boolean enableStartTls;
	private Boolean enableSsl;
	private String username;
	private String password;
}
