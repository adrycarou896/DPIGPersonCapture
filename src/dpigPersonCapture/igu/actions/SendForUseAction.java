package dpigPersonCapture.igu.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import dpigPersonCapture.utils.Messages;

public class SendForUseAction {
	
	private String personName, cameraName, saveFolderPath, finalPath;

	public SendForUseAction(String personName, String cameraName, String saveFolderPath, String finalPath) {
		this.personName = personName;
		this.cameraName = cameraName;
		this.saveFolderPath = saveFolderPath;
		this.finalPath = finalPath;
	}
	
	public String sendForUse(){
		if(personName==null || personName.isEmpty()){
			return Messages.personNameError;
		}
		if(cameraName==null || cameraName.isEmpty()){
			return Messages.cameraNameError;
		}
		if(saveFolderPath==null || saveFolderPath.isEmpty() || finalPath==null || finalPath.isEmpty()){
			return Messages.pathError;
		}
		saveFolderPath = saveFolderPath+"/"+cameraName+"/Frames/"+personName;
		File saveFolder = new File(saveFolderPath); 
		if(!saveFolder.exists()){
			return Messages.trainingPathNotExistError;
		}
		FilenameFilter imgFilter = new FilenameFilter() { 
			public boolean accept(File dir, String name) { 
                name = name.toLowerCase(); 
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png"); 
            } 
        }; 
        
        finalPath = finalPath+"/"+personName;
        File finalFolder = new File(finalPath);
        
        if(!finalFolder.exists()){
        	finalFolder.mkdir();
        }
	        
    	for (File userImage : saveFolder.listFiles(imgFilter)) {
    		try{
        		FileInputStream fis = new FileInputStream(userImage); //inFile -> Archivo a copiar
        		
        		String[] userImagePathArray = userImage.getAbsolutePath().split(cameraName);
        		String userImageExtensionName = userImagePathArray[userImagePathArray.length-1];
        		FileOutputStream fos = new FileOutputStream(finalFolder+"/"+cameraName+userImageExtensionName); //outFile -> Copia del archivo
        		
        		FileChannel inChannel = fis.getChannel();
        		FileChannel outChannel = fos.getChannel();
        		inChannel.transferTo(0, inChannel.size(), outChannel);
        		fos.close();
        		fis.close();	
        	
        	}catch (IOException ioe) {
        		System.err.println("Error al Generar Copia 1");
	        }
    	 }
         return Messages.sendForUseSuccesfuly;
    }
}
