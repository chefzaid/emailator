package com.emailator.bulksender.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class Progress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Enumerated(EnumType.STRING)
	private ProgressState state;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date creationTime;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdateTime;
	private String details;

	public Progress() {
		this.state = ProgressState.PENDING;
		this.creationTime = new Date();
		this.lastUpdateTime = new Date();
	}
}
