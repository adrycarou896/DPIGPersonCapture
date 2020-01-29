package dpigPersonCapture.igu.actions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import dpigPersonCapture.facialDetection.FacialDetection;
import dpigPersonCapture.reader.ReadVideoFrames;
import dpigPersonCapture.utils.Messages;

public class SaveFacesAction {
	
	private String personName, cameraName, videoPath, saveFolderPath, finalPath;
	private FacialDetection facialDetection;
	
	
	public SaveFacesAction(String personName, String cameraName, String videoPath, String saveFolderPath, String finalPath,
			FacialDetection facialDetection) {
		this.personName = personName;
		this.cameraName = cameraName;
		this.videoPath = videoPath;
		this.saveFolderPath = saveFolderPath;
		this.finalPath = finalPath;
		this.facialDetection = facialDetection;
	}


	public String saveFaces(){
		if(personName==null || personName.isEmpty()){
			return Messages.personNameError;
		}
		if(cameraName==null || cameraName.isEmpty()){
			return Messages.cameraNameError;
		}
		if(videoPath==null || videoPath.isEmpty()){
			return Messages.videoError;
		}
		if(saveFolderPath==null || saveFolderPath.isEmpty() || finalPath==null || finalPath.isEmpty()){
			return Messages.pathError;
		}
		
		saveFolderPath = saveFolderPath + "/" + cameraName + "/Frames/" + personName;
		finalPath = finalPath + "/" + personName;
		
		File saveFolder = new File(saveFolderPath);
		if(!saveFolder.exists()){
			saveFolder.mkdirs();
		}
				
		ReadVideoFrames decodeAndCaptureFrames;
		try {
			decodeAndCaptureFrames = new ReadVideoFrames(videoPath);
			List<BufferedImage>images = decodeAndCaptureFrames.getImages();
			
			FilenameFilter imgFilter = new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
	                name = name.toLowerCase(); 
	                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png"); 
	            } 
	        }; 
	        
	         File finalFolder = new File(finalPath);
	         if(!finalFolder.exists()){
	        	 finalFolder.mkdirs();
	          }
		       
		    File[] finalFolderImages = finalFolder.listFiles(imgFilter);
		       
		    int cont = finalFolderImages.length+1;
			for (BufferedImage image : images) {
				cont = cont + facialDetection.detectAndSave(image, cont, saveFolderPath, cameraName);
			}
			return Messages.saveFramesSuccesfully;
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
		return null;
	}
}
