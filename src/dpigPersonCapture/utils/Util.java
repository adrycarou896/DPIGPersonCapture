package dpigPersonCapture.utils;

public class Util {
	
	public static final String LOAD_OPENCV_PATH = "C:\\opencv\\build\\java\\x64\\opencv_java400.dll"; 
	public static final String CASCADE_PATH = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_default.xml";
	
	public static final String SMARTTHINGS_DEVICES = "https://api.smartthings.com/v1/devices";
	
	private String folderCamerasPath;
	private String folderUsersPath;
	
	private String smartThingsToken;
	
	public String getFolderCamerasPath() {
		return folderCamerasPath;
	}
	public void setFolderCamerasPath(String folderCamerasPath) {
		this.folderCamerasPath = folderCamerasPath;
	}
	public String getFolderPersonsPath() {
		return folderUsersPath;
	}
	public void setFolderPersonsPath(String folderUsersPath) {
		this.folderUsersPath = folderUsersPath;
	}
	public String getSmartThingsToken() {
		return smartThingsToken;
	}
	public void setSmartThingsToken(String smartThingsToken) {
		this.smartThingsToken = smartThingsToken;
	}
}
