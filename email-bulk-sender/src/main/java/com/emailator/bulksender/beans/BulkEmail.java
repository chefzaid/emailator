package com.emailator.bulksender.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class BulkEmail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String uuid;
	@Transient
	private SmtpConfiguration smtpConfiguration;
	@OneToOne(cascade = CascadeType.ALL)
	private Email email;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Recipient> recipients;
}
