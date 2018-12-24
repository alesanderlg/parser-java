package com.ef;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ef.dto.LogAccessDTO;
import com.ef.model.LogAccessMoreThan100;
import com.ef.model.LogAccessMoreThan250;
import com.ef.service.ParserService;
import com.ef.utils.Constants;
import com.ef.utils.Utils;

public class ParserTest extends ParserApplicationTests {
	
	@Autowired
	private ParserService parserService;
		
	@Test
	public void argumentsAccesslog() throws Exception {
		String[] args = { "--accesslog=C:\\Users\\Alesander\\Desktop\\access.log" };
		ParserApplication.main(args);
		assertEquals(ParserApplication.message, Constants.MSG_FILE_FOUND);
	}
	
	@Test
	public void moreThan100Request() throws Exception {
		String[] args = { "--startDate=2017-01-01.13:00:00","--duration=hourly","--threshold=100" };
		ParserApplication.main(args);
		List<LogAccessDTO> logAccessList = parserService.findIpAddressMoreThan("2017-01-01 13:00:00", "2017-01-01 14:00:00", 100);
		List<LogAccessMoreThan100> accessMoreThan100RepositorieList = parserService.findAllMoreThan100();
		assertEquals(logAccessList.size(), accessMoreThan100RepositorieList.size());
	}
	
	@Test
	public void moreThan250Request() throws Exception {
		String[] args = { "--startDate=2017-01-01.13:00:00","--duration=daily","--threshold=250" };
		ParserApplication.main(args);
		List<LogAccessDTO> logAccessList = parserService.findIpAddressMoreThan("2017-01-01 13:00:00", "2017-01-02 13:00:00", 250);
		List<LogAccessMoreThan250> accessMoreThan250RepositorieList = parserService.findAllMoreThan250();
		assertEquals(logAccessList.size(), accessMoreThan250RepositorieList.size());
	}
	
	@Test
	public void parseOtherParametersIssueOne() throws Exception {
		String[] args = { "--startDate=2017-01-01.15:00:00","--duration=hourly","--threshold=200" };
		ParserApplication.main(args);
		List<LogAccessDTO> logAccessList = parserService.findIpAddressMoreThan("2017-01-01 15:00:00", "2017-01-01 15:59:59", 200);
		assertTrue(logAccessList.size() > 1);
	}
	
	@Test
	public void parseOtherParametersIssueTwo() throws Exception {
		String[] args = { "--startDate=2017-01-01.00:00:00","--duration=daily","--threshold=500" };
		ParserApplication.main(args);
		List<LogAccessDTO> logAccessList = parserService.findIpAddressMoreThan("2017-01-01 00:00:00", "2017-01-02 23:59:59", 500);
		assertTrue(logAccessList.size() > 1);
	}
	
	@Test
	public void convertIPV4AdressToDecimalNumber() {
		long value = Utils.convertIPV4AdressToDecimalNumber("192.168.228.188");
		assertEquals(3232294076L, value);  
	}
	
	@Test
	public void convertDecimalToIPV4AddressString() {
		String value = Utils.convertLongToIPV4AddressString(3232294076L);
		assertEquals("192.168.228.188", value);
	}
	
	@Test
	public void addOneDayFinishDate() {
		String value = Utils.addOneDayOnDateWithHour23("2017-01-01 00:00:00");
		assertEquals("2017-01-02 23:59:59", value);
	}
	

}
