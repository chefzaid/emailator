package com.emailator.bulksender.beans;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Recipient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String emailAddress;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Progress progress;
	@ManyToOne
	private BulkEmail bulkEmail;

	public Recipient() {
	}

	public Recipient(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
