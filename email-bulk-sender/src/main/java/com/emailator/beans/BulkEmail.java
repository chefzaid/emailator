package com.emailator.beans;

import java.util.List;

import lombok.Data;

@Data
public class BulkEmail {
	private List<String> recipients;
	private Email email;

}
