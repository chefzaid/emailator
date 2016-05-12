package com.emailator.bulksender.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emailator.bulksender.beans.BulkEmail;

@Repository
public interface BulkEmailDao extends CrudRepository<BulkEmail, Long> {

}
