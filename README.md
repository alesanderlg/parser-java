# Parser in Java

This parser in Java parses web server access log file, loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration. 

LOG Format:
Date, IP, Request, Status, User Agent (pipe delimited, open the example file in text editor)

Date Format: "yyyy-MM-dd HH:mm:ss.SSS"

The delimiter of the log file is pipe (|)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine.

With Eclipse IDE oxygen or Spring Tool Suite, you should always open Maven projects using “File->Import->Maven->Existing Maven projects”.

This way them figures out it is a Maven project and knows what to do with it
	
* MySQL schema used for the log data: / parser / src / main / resources / schema_data_base.sql
* SQL queries for SQL test: / parser / src / main / resources / script.sql

### Prerequisites

What things you need to config the project:

* JDK 8 installed
* Eclipse IDE oxygen or Spring Tool Suite STS 3.9.1.RELEASE
* MySQL 5.7

### Running the application

Command lines of examples that you will need from running the application: 

To access the log file and loads them to MySQL

```
java -cp "parser.jar" com.ef.ParserApplication --accesslog=/path/to/file
```

To find any IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00 (one hour)

```
java -cp "parser.jar" com.ef.ParserApplication --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
```

To find any IPs that made more than 250 requests starting from 2017-01-01.13:00:00 to 2017-01-02.13:00:00 (24 hours)

```
java -cp "parser.jar" com.ef.ParserApplication --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250
```

To find any IPs that made more than 200 requests starting from 2017-01-01.15:00:00 to 2017-01-01.15:59:59 (one hour)

```
java -cp "parser.jar" com.ef.ParserApplication --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200
```

To find any IPs that made more than 500 requests starting from 2017-01-01.00:00:00 to 2017-01-02.23:59:59 (24 hours)

```
java -cp "parser.jar" com.ef.ParserApplication --startDate=2017-01-01.00:00:00 --duration=daily --threshold=500
```

## Unit Tests

Test Access log file, loads the log to MySQL
To initialize the test, change the default path
```
@Test
	public void argumentsAccesslog() throws Exception {
		String[] args = { "--accesslog=/Users/alesandergonzaga/access.log" };
		ParserApplication.main(args);
		assertEquals(ParserApplication.message, Constants.MSG_FILE_FOUND);
	}
```

Test to find any IPs that made more than 100 requests
```
@Test
	public void moreThan100Request() throws Exception {
		String[] args = { "--startDate=2017-01-01.13:00:00","--duration=hourly","--threshold=100" };
		ParserApplication.main(args);
		List<LogAccessDTO> logAccessList = parserService.findIpAddressMoreThan("2017-01-01 13:00:00", "2017-01-01 14:00:00", 100);
		List<LogAccessMoreThan100> accessMoreThan100RepositorieList = parserService.findAllMoreThan100();
		assertEquals(logAccessList.size(), accessMoreThan100RepositorieList.size());
	}
```

Test to find any IPs that made more than 250 requests
```
@Test
	public void moreThan250Request() throws Exception {
		String[] args = { "--startDate=2017-01-01.13:00:00","--duration=daily","--threshold=250" };
		ParserApplication.main(args);
		List<LogAccessDTO> logAccessList = parserService.findIpAddressMoreThan("2017-01-01 13:00:00", "2017-01-02 13:00:00", 250);
		List<LogAccessMoreThan250> accessMoreThan250RepositorieList = parserService.findAllMoreThan250();
		assertEquals(logAccessList.size(), accessMoreThan250RepositorieList.size());
	}
```

Test to find any IPs that made more than 200 requests
```
@Test
	public void parseOtherParametersIssueOne() throws Exception {
		String[] args = { "--startDate=2017-01-01.15:00:00","--duration=hourly","--threshold=200" };
		ParserApplication.main(args);
		List<LogAccessDTO> logAccessList = parserService.findIpAddressMoreThan("2017-01-01 15:00:00", "2017-01-01 15:59:59", 200);
		assertTrue(logAccessList.size() > 1);
	}
```

Test to find any IPs that made more than 500 requests
```
@Test
	public void parseOtherParametersIssueTwo() throws Exception {
		String[] args = { "--startDate=2017-01-01.00:00:00","--duration=daily","--threshold=500" };
		ParserApplication.main(args);
		List<LogAccessDTO> logAccessList = parserService.findIpAddressMoreThan("2017-01-01 00:00:00", "2017-01-02 23:59:59", 500);
		assertTrue(logAccessList.size() > 1);
	}
```


Test to convert IP Address to Decimal
```
@Test
	public void convertDecimalToIPV4AddressString() {
		String value = Utils.convertLongToIPV4AddressString(3232294076L);
		assertEquals("192.168.228.188", value);
	}
```

Test to  convert Decimal to IP Address (String)
```
@Test
	public void addOneDayFinishDate() {
		String value = Utils.addOneDayOnDateWithHour23("2017-01-01 00:00:00");
		assertEquals("2017-01-02 23:59:59", value);
	}
```

## Additional comments.

* To store IP address was created UNSIGNED INT column.
* Below are two simple functions to get the IP Address
```
SELECT inet_aton('192.168.0.1') -> 32322235521
SELECT inet_ntoa(32322235521) -> 192.168.0.1
```

