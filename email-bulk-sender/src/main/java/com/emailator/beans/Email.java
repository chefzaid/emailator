package com.emailator.beans;

import lombok.Data;

@Data
public class Email {
	private String sender;
	private String subject;
	private String body;

}
