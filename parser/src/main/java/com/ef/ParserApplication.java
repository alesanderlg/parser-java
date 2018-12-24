package com.ef;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ef.dto.LogAccessDTO;
import com.ef.model.LogAccess;
import com.ef.model.LogAccessMoreThan100;
import com.ef.model.LogAccessMoreThan250;
import com.ef.service.ParserService;
import com.ef.utils.Constants;
import com.ef.utils.Utils;

@SpringBootApplication
public class ParserApplication implements ApplicationRunner {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ParserApplication.class);
	static String message;
	private boolean containsStartDate;
	private boolean containsDuration;
	private boolean containsThreshold;
	private boolean containsAccessLog;
	Map<String, String> parameter = new HashMap<String, String>();

	@Autowired
	ParserService parserService;

	public static void main(String[] args) {
		SpringApplication.run(ParserApplication.class, args);
	}

	public void run(ApplicationArguments args) throws Exception {

		getArgments(args);

		if (containsAccessLog) {
			readFileAndLoadToMysql();
		} else {
			if (Constants.DURATION_DAILY.equals(parameter.get(Constants.ARGUMENT_DURATION))
					&& "250".equals((parameter.get(Constants.ARGUMENT_THRESHOLD)))) {
				parseLogAccessMoreThan250();
			} else if (Constants.DURATION_HOURLY.equals(parameter.get(Constants.ARGUMENT_DURATION))
					&& "100".equals((parameter.get(Constants.ARGUMENT_THRESHOLD)))) {
				parseLogAccessMoreThan100();
			} else if(containsStartDate && containsDuration && containsThreshold) {
				parseOtherParameters();
			}

		}
	}
	
	public void parseOtherParameters() {
		String startDate = parameter.get(Constants.ARGUMENT_START_DATE);
		String finishDate = Constants.DURATION_DAILY.equals(parameter.get(Constants.ARGUMENT_DURATION)) ? Utils.addOneDayOnDateWithHour00(startDate) : Utils.addMinutAndSecondHourOnFinishDate(startDate);
		long threshold = Long.parseLong(parameter.get(Constants.ARGUMENT_THRESHOLD));
		List<LogAccessDTO> list = parserService.findIpAddressMoreThan(startDate, finishDate, threshold);
		for (LogAccessDTO logAccessDTO : list) {
			System.out.println(logAccessDTO.toString());
		}
		logger.info("The records parsed: >>>> {}",list != null ? list.size() : 0);
	}

	public void parseLogAccessMoreThan100() {
		String startDate = parameter.get(Constants.ARGUMENT_START_DATE);
		String finishDate = Utils.addOneHourOnDate(startDate);
		long threshold = Long.parseLong(parameter.get(Constants.ARGUMENT_THRESHOLD));
		List<LogAccessDTO> list = parserService.findIpAddressMoreThan(startDate, finishDate, threshold);
		LogAccessMoreThan100 logAccessMoreThan100 = new LogAccessMoreThan100();
		List<LogAccessMoreThan100> accessMoreThan100List = new ArrayList<LogAccessMoreThan100>();
		for (LogAccessDTO logAccessDTO : list) {
			logAccessMoreThan100 = new LogAccessMoreThan100(logAccessDTO.getIpAddress(), logAccessDTO.getCount());
			accessMoreThan100List.add(logAccessMoreThan100);
			System.out.println(logAccessMoreThan100.toString());
		}
		logger.info("The records parsed: >>>> {}",accessMoreThan100List != null ? accessMoreThan100List.size() : 0);
		parserService.saveAccessMoreThan100(accessMoreThan100List);
	}

	public void parseLogAccessMoreThan250() {
		String startDate = parameter.get(Constants.ARGUMENT_START_DATE);
		String finishDate = Utils.addOneDayOnDateWithHour00(startDate);
		long threshold = Long.parseLong(parameter.get(Constants.ARGUMENT_THRESHOLD));
		List<LogAccessDTO> list = parserService.findIpAddressMoreThan(startDate, finishDate, threshold);
		LogAccessMoreThan250 logAccessMoreThan250 = new LogAccessMoreThan250();
		List<LogAccessMoreThan250> accessMoreThan250List = new ArrayList<LogAccessMoreThan250>();
		for (LogAccessDTO logAccessDTO : list) {
			logAccessMoreThan250 = new LogAccessMoreThan250(logAccessDTO.getIpAddress(), logAccessDTO.getCount());
			accessMoreThan250List.add(logAccessMoreThan250);
			System.out.println(logAccessMoreThan250.toString());
		}
		logger.info("The records parsed: >>>> {}",accessMoreThan250List != null ? accessMoreThan250List.size() : 0);
		parserService.saveAccessMoreThan250(accessMoreThan250List);
	}

	public void getArgments(ApplicationArguments args) {

		logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
		logger.info("OptionNames: {}", args.getOptionNames());

		if (args.getSourceArgs().length < 1) {
			message = Constants.MSG_NO_ARGUMENT;
			logger.info("MESSAGE: {}", message);
		}

		containsStartDate = args.containsOption(Constants.ARGUMENT_START_DATE);
		containsDuration = args.containsOption(Constants.ARGUMENT_DURATION);
		containsThreshold = args.containsOption(Constants.ARGUMENT_THRESHOLD);
		containsAccessLog = args.containsOption(Constants.ARGUMENT_ACCESS_LOG);

		logger.info("containsStartDate: {}", containsStartDate);
		logger.info("containsDuration: {}", containsDuration);
		logger.info("containsThreshold: {}", containsThreshold);
		logger.info("containsAccessLog: {}", containsAccessLog);

		for (String arg : args.getSourceArgs()) {

			if (arg.contains(Constants.ARGUMENT_ACCESS_LOG)) {
				String[] accessLogIn = arg.split("=");
				parameter.put(Constants.ARGUMENT_ACCESS_LOG, accessLogIn[1]);
			}

			if (arg.contains(Constants.ARGUMENT_START_DATE)) {
				String[] startDateIn = arg.split("=");
				parameter.put(Constants.ARGUMENT_START_DATE, Utils.replacePointDate(startDateIn[1]));
			}

			if (arg.contains(Constants.ARGUMENT_DURATION)) {
				String[] durationIn = arg.split("=");
				parameter.put(Constants.ARGUMENT_DURATION, durationIn[1]);
			}

			if (arg.contains(Constants.ARGUMENT_THRESHOLD)) {
				String[] thresholdIn = arg.split("=");
				parameter.put(Constants.ARGUMENT_THRESHOLD, thresholdIn[1]);
			}
		}

	}

	public void readFileAndLoadToMysql() throws IOException {
		try {
			if (containsAccessLog) {
				InputStream inputStream = new FileInputStream(parameter.get(Constants.ARGUMENT_ACCESS_LOG));
				// Resource resource = new ClassPathResource("/access.log");
				message = Constants.MSG_FILE_FOUND;
				if (inputStream != null) {
					// inputStream = inputStream != null ? inputStream : resource.getInputStream();
					message = Constants.MSG_FILE_FOUND;
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					String line;
					LogAccess log;
					List<LogAccess> logAccessList = new ArrayList<LogAccess>();
					while ((line = bufferedReader.readLine()) != null) {
						String[] lineTracking = line.split("\\|", -1);
						log = new LogAccess(lineTracking[0], lineTracking[1], lineTracking[2], lineTracking[3],
								lineTracking[4]);
						logAccessList.add(log);
						System.out.println(log.toString());
					}
					logger.info("The records parsed: >>>> {}",logAccessList != null ? logAccessList.size() : 0);
					logger.info(">>>> Storing the records in database....... ");
					parserService.saveFile(logAccessList);
					logger.info(">>>> It's records were storing with success! ");
					bufferedReader.close();
					inputStream.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
