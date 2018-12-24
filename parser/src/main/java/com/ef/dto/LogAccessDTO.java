package com.ef.dto;

import com.ef.utils.Utils;

public class LogAccessDTO {
	
	private Long ipAddress;
	private long count;
	
	public LogAccessDTO(long ipAddress, long count) {
		this.ipAddress = ipAddress;
		this.count = count;
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class LogAccessOtherParameter {\n");

		sb.append("    ip address decimal (base 10): ").append(toIndentedString(ipAddress)).append("\n");
		sb.append("    ip address base 256: ").append(toIndentedString(Utils.convertLongToIPV4AddressString(ipAddress))).append("\n");
		sb.append("    count: ").append(toIndentedString(count)).append("\n");
		sb.append("}");

		return sb.toString();
	}

	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
	
	
}
