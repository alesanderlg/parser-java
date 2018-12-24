


--SQL
--=========================================================================================================================
--(1) Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.

SELECT   inet_ntoa(l2.ip_address), count(*) as count 
			FROM     log l2 
			WHERE    (date_format(l2.date, '%Y-%m-%d %T.%f') >= '2017-01-01 15:00:00' and  date_format(l2.date, '%Y-%m-%d %T.%f') <= '2017-01-01 15:59:59') 
			GROUP BY l2.ip_address 
			HAVING   count(*) > 200

--(2) Write MySQL query to find requests made by a given IP.

SELECT date, 
       inet_ntoa(ip_address),
       id,
       request, 
       status, 
       message 
FROM   log 
WHERE  ip_address = inet_aton('192.168.102.136');

--=========================================================================================================================

