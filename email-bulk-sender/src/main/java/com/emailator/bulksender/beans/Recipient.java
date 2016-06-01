package com.emailator.bulksender.beans;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Recipient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String emailAddress;
	@OneToOne(cascade = CascadeType.ALL)
	private Progress progress;
	@ManyToOne
	private BulkEmail bulkEmail;

	public Recipient() {
	}

	public Recipient(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
