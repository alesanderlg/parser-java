package com.ef.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ef.utils.Utils;

@Entity
@Table(name = "log_more_than_100")
public class LogAccessMoreThan100 implements Serializable{

	private static final long serialVersionUID = 249694049732440795L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private long ipAddress;
	private long count;

	private String comments;
		
	public LogAccessMoreThan100() {
		// TODO Auto-generated constructor stub
	}
	
	public LogAccessMoreThan100(long ipAddress, long count) {
		this.ipAddress = ipAddress;
		this.count = count;
		this.comments = "It's IP was blocked because made more than 100 request in one hour";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(long ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class LogAccessMoreThan100 {\n");

		sb.append("    ip address decimal (base 10): ").append(toIndentedString(ipAddress)).append("\n");
		sb.append("    ip address base 256: ").append(toIndentedString(Utils.convertLongToIPV4AddressString(ipAddress))).append("\n");
		sb.append("    count: ").append(toIndentedString(count)).append("\n");
		sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
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
		return Objects.hash(id, ipAddress, comments);
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LogAccessMoreThan100 logAccessMoreThan100 = (LogAccessMoreThan100) o;
		return Objects.equals(this.id, logAccessMoreThan100.id)
				&& Objects.equals(this.ipAddress, logAccessMoreThan100.ipAddress)
				&& Objects.equals(this.count, logAccessMoreThan100.count) 
				&& Objects.equals(this.comments, logAccessMoreThan100.comments);
	}
	
}
