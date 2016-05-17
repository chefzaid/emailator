package com.emailator.bulksender.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Recipient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String emailAddress;

	public Recipient() {

	}

	public Recipient(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
