package com.emailator.bulksender.beans;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Email {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String sender;
	private String subject;
	private String body;
	@ElementCollection
	private List<String> attachmentsPaths;

}
