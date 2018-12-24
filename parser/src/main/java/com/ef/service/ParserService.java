package com.ef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.dto.LogAccessDTO;
import com.ef.model.LogAccess;
import com.ef.model.LogAccessMoreThan100;
import com.ef.model.LogAccessMoreThan250;
import com.ef.repository.LogAccessMoreThan100Repository;
import com.ef.repository.LogAccessMoreThan250Repository;
import com.ef.repository.LogAccessRepository;

@Service
public class ParserService {
	
	@Autowired
	private LogAccessRepository logAccessRepository;

	@Autowired
	private LogAccessMoreThan100Repository logAccessMoreThan100Repository;

	@Autowired
	private LogAccessMoreThan250Repository logAccessMoreThan250Repository;
	
	
	public void saveFile(List<LogAccess> logAccessList) {
		logAccessRepository.save(logAccessList);
	}
	
	public void saveAccessMoreThan100(List<LogAccessMoreThan100> accessMoreThan100List) {
		logAccessMoreThan100Repository.save(accessMoreThan100List);
	}
	
	public void saveAccessMoreThan250(List<LogAccessMoreThan250> accessMoreThan250List) {
		logAccessMoreThan250Repository.save(accessMoreThan250List);
	}
		
	public List<LogAccessDTO> findIpAddressMoreThan(String startDate, String finishDate, long threshold) {
		return logAccessRepository.findIpAddressMoreThan(startDate, finishDate, threshold);
	}
	
	public List<LogAccessMoreThan100> findAllMoreThan100(){
		return (List<LogAccessMoreThan100>) logAccessMoreThan100Repository.findAll();
	}
	
	public List<LogAccessMoreThan250>  findAllMoreThan250(){
		return (List<LogAccessMoreThan250> ) logAccessMoreThan250Repository.findAll();
	}
	
	public List<LogAccess>  findAllLogAccess(){
		return (List<LogAccess>) logAccessRepository.findAll();
	}
	
}
