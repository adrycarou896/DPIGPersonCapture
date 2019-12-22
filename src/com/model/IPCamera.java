package com.model;

import java.io.Serializable;

public class IPCamera implements Serializable{
	
	private static final long serialVersionUID = -5830593100181011553L;
	
	private Long id;
	
	private String deviceId;
	
	private String name;
	
	public IPCamera(){}
	
	public IPCamera(String id, String name){
		this.deviceId = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
