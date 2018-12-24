package com.ef.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ef.model.LogAccessMoreThan100;

@Repository
public interface LogAccessMoreThan100Repository extends CrudRepository<LogAccessMoreThan100, Long> {
		
}
