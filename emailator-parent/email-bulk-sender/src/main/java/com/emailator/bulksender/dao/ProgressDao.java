package com.emailator.bulksender.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emailator.bulksender.beans.Progress;

@Repository
public interface ProgressDao extends CrudRepository<Progress, Long> {

}
