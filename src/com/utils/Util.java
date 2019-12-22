package com.utils;

public class Util {
	
	public static final String LOAD_OPENCV_PATH = "C:\\opencv\\build\\java\\x64\\opencv_java400.dll"; 
	public static final String CASCADE_PATH = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml";
	
	public static final String SMARTTHINGS_TOKEN = "ac706230-698e-45e0-8375-8f692a033fd7";
	public static final String SMARTTHINGS_DEVICES = "https://api.smartthings.com/v1/devices";
	
	private String folderCamerasPath;
	private String folderUsersPath;
	
	public String getFolderCamerasPath() {
		return folderCamerasPath;
	}
	public void setFolderCamerasPath(String folderCamerasPath) {
		this.folderCamerasPath = folderCamerasPath;
	}
	public String getFolderUsersPath() {
		return folderUsersPath;
	}
	public void setFolderUsersPath(String folderUsersPath) {
		this.folderUsersPath = folderUsersPath;
	}

}
