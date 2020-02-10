package dpigPersonCapture.igu.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import dpigPersonCapture.utils.Messages;

public class ResetImagesNamesAction {
	
	private String personName, cameraName, saveFolderPath, finalPath;

	public ResetImagesNamesAction(String personName, String cameraName, String saveFolderPath, String finalPath) {
		this.personName = personName;
		this.cameraName = cameraName;
		this.saveFolderPath = saveFolderPath;
		this.finalPath = finalPath;
	}
	
	public String resetImagesNames(){
		
		File saveFolder = new File(saveFolderPath);
		String validateEntries = validateEntries(saveFolder);
		if(validateEntries!=null){
			return validateEntries;
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
        	 finalFolder.mkdirs();
        }
       
        File[] finalFolderImages = finalFolder.listFiles(imgFilter);
        
        String folderCopyPath = saveFolderPath+"/copy";
        File folderCopy = new File(folderCopyPath);
        folderCopy.mkdirs();
        
        int cont = finalFolderImages.length+1;
        for (File userImage : saveFolder.listFiles(imgFilter)) {
        	String copyImagePath = folderCopyPath+"/"+cameraName+"_img"+cont+".jpg";
    		try{
        		FileInputStream fis = new FileInputStream(userImage); //inFile -> Archivo a copiar
        		FileOutputStream fos = new FileOutputStream(copyImagePath); //outFile -> Copia del archivo
        		FileChannel inChannel = fis.getChannel();
        		FileChannel outChannel = fos.getChannel();
        		inChannel.transferTo(0, inChannel.size(), outChannel);
        		fos.close();
        		fis.close();
        		
        		userImage.delete();		
        	
        	}catch (IOException ioe) {
        		System.err.println("Error al Generar Copia 1");
	        }
        	cont++;
		}
        
        cont = finalFolderImages.length+1;
        for (File userImageCopy : folderCopy.listFiles(imgFilter)) {
        	String newUserImagePath = saveFolderPath+"/"+cameraName+"_img"+cont+".jpg";
        	
        	try{
        		FileInputStream fis = new FileInputStream(userImageCopy); //inFile -> Archivo a copiar
        		FileOutputStream fos = new FileOutputStream(newUserImagePath); //outFile -> Copia del archivo
        		FileChannel inChannel = fis.getChannel();
        		FileChannel outChannel = fos.getChannel();
        		inChannel.transferTo(0, inChannel.size(), outChannel);
        		fos.close();
        		fis.close();	
        		
        		userImageCopy.delete();
        	
        	}catch (IOException ioe) {
        		System.err.println("Error al Generar Copia 2");
	        }
        	cont++;
		}
        folderCopy.delete();
        
        return Messages.resetImagesNamesSuccesfuly;
    }
	
	private String validateEntries(File saveFolder){
		if(personName==null || personName.isEmpty()){
			return Messages.personNameError;
		}
		if(cameraName==null || cameraName.isEmpty()){
			return Messages.cameraNameError;
		}
		if(saveFolderPath==null || saveFolderPath.isEmpty() || finalPath==null || finalPath.isEmpty()){
			return Messages.pathError;
		}
		if(!saveFolder.exists()){
			return Messages.trainingPathNotExistError;
		}
		return null;
	}
}
