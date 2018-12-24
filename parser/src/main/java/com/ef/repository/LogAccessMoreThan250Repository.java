package com.ef.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ef.model.LogAccessMoreThan250;

@Repository
public interface LogAccessMoreThan250Repository extends CrudRepository<LogAccessMoreThan250, Long> {
	
	
}
