package com.emailator.bulksender.beans;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class BulkEmail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String bulkEmailId;
	@Transient
	private SmtpConfiguration smtpConfiguration;
	@OneToOne
	private Email email;
	@ElementCollection
	private List<String> recipients;
}
