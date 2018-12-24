package com.ef.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class Utils {

	private static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";

	public static String addOneHourOnDate(String dateString) {
		Date date = validationFormatDateString(dateString);
		String dateStringConverter = null;
		if(date != null) {
			DateTime dateTime = new DateTime(date.getTime());
	        dateStringConverter = dateToString(dateTime.plusHours(1).toDate()); 
		}		
		return dateStringConverter;
	}
	
	public static String addMinutAndSecondHourOnFinishDate(String dateString) {
		Date date = validationFormatDateString(dateString);
		String dateStringConverter = null;
		if(date != null) {
			DateTime dateTime = new DateTime(date.getTime());
	        dateStringConverter = dateToString(dateTime.plusMinutes(59).plusSeconds(59).toDate()); 
		}		
		return dateStringConverter;
	}
	
	public static String addOneDayOnDateWithHour00(String dateString) {
		Date date = validationFormatDateString(dateString);
		String dateStringConverter = null;
		if(date != null) {
			DateTime dateTime = new DateTime(date.getTime());
	        dateStringConverter = dateToString(dateTime.plusDays(1).toDate()); 
		}		
		return dateStringConverter;
	}
	
	public static String addOneDayOnDateWithHour23(String dateString) {
		Date date = validationFormatDateString(dateString);
		String dateStringConverter = null;
		if(date != null) {
			DateTime dateTime = new DateTime(date.getTime());
			dateTime = dateTime.plusDays(2);
			dateTime = dateTime.minusSeconds(1);			
	        dateStringConverter = dateToString(dateTime.toDate()); 
		}		
		return dateStringConverter;
	}
	
	public static Date validationFormatDateString(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String validationFormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String dateString = null;
		try {
			dateString = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}
	
	public static String dateToString(Date date) {		
		return validationFormatDate(date);	
	}
	
	public static long convertIPV4AdressToDecimalNumber(String ipAddress) {
		String[] ipAddressIn = ipAddress.split("\\.");
		long result = 0;
		for(int i = 0; i < ipAddressIn.length; i++) {
			int power = 3 - i;
			result += (Integer.parseInt(ipAddressIn[i]) % 256 * Math.pow(256, power));
		}
		return result;
	}
	
	public static String convertLongToIPV4AddressString(long ipAddress) {
		StringBuilder result = new StringBuilder(15);

		for (int i = 0; i < 4; i++) {

			result.insert(0,Long.toString(ipAddress & 0xff));

			if (i < 3) {
				result.insert(0,'.');
			}

			ipAddress = ipAddress >> 8;
		}
		return result.toString();
	}
	
	public static String replacePointDate(String dateString) {
		return dateString.replace(".", " ");
	}

}
