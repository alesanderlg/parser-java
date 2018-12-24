package com.ef.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ef.utils.Utils;

@Entity
@Table(name = "log")
public class LogAccess implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private long ipAddress;
	private Date date;
	private String request;
	private Integer status;
	private String message;
	
	public LogAccess() {
		// TODO Auto-generated constructor stub
	}
	
	public LogAccess(String date, String ip, String request, String status, String message) {
		this.ipAddress = Utils.convertIPV4AdressToDecimalNumber(ip);
		this.date = stringToDate(date);
		this.request = request;
		this.status = Integer.parseInt(status);
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(long ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date stringToDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateConvert = null;
		try {
			dateConvert = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return dateConvert;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Log {\n");

		sb.append("    date: ").append(toIndentedString(Utils.dateToString(date))).append("\n");
		sb.append("    ip address decimal (base 10): ").append(toIndentedString(ipAddress)).append("\n");
		sb.append("    ip address base 256: ").append(toIndentedString(Utils.convertLongToIPV4AddressString(ipAddress))).append("\n");
		sb.append("    request: ").append(toIndentedString(request)).append("\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("    message: ").append(toIndentedString(message)).append("\n");
		sb.append("}");

		return sb.toString();
	}

	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, date, ipAddress, message, request, status);
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LogAccess log = (LogAccess) o;
		return Objects.equals(this.id, log.id) && 
				Objects.equals(this.date, log.date) 
				&& Objects.equals(this.ipAddress, log.ipAddress)
				&& Objects.equals(this.message, log.message) 
				&& Objects.equals(this.request, log.request)
				&& Objects.equals(this.status, log.status);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
