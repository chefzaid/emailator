package com.emailator.bulksender.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emailator.bulksender.beans.Recipient;

@Repository
public interface RecipientDao extends CrudRepository<Recipient, Long> {
	
	@Query("select r from Recipient r where r.bulkEmail.uuid = ?1 and r.emailAddress = ?2")
	Recipient findByUuidAndEmailAddress(String uuid, String emailAddress);
}
