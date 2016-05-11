package com.emailator.beans;

import java.util.List;

import lombok.Data;

@Data
public class BulkEmail {
	private SmtpConfiguration smtpConfiguration;
	private Email email;
	private List<String> recipients;
}
