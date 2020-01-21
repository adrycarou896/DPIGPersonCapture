package com.igu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.JOptionPane;

public class AcctionBotonRestablecerNombres implements ActionListener{
	
	private VentanaPrincipal ventanaPrincipal;
	
	public AcctionBotonRestablecerNombres(VentanaPrincipal ventanaPrincipal){
		this.ventanaPrincipal = ventanaPrincipal;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cameraSelectedName = ventanaPrincipal.getComboCamaras().getSelectedItem().toString();
		String userName = ventanaPrincipal.getCampoUsuario().getText();
		if(!userName.isEmpty()){
			
			FilenameFilter imgFilter = new FilenameFilter() { 
				public boolean accept(File dir, String name) { 
	                name = name.toLowerCase(); 
	                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png"); 
	            } 
	        }; 
	        
	        String userFolderPath = this.ventanaPrincipal.getUtil().getFolderCamerasPath()+"/"+cameraSelectedName+"/Frames/"+userName;
	        File userFolder = new File(userFolderPath); 
	        if(userFolder.exists()){
		        String userMainFolderPath = this.ventanaPrincipal.getUtil().getFolderUsersPath()+"/"+userName;
		        File userMainFolder = new File(userMainFolderPath);
		        if(!userMainFolder.exists()){
		        	 userMainFolder.mkdirs();
		        }
		       
		        File[] userMainImages = userMainFolder.listFiles(imgFilter);
		        
		        //List<File> imagesList = orderList(userFolder.listFiles(imgFilter),userName);
		        
		        String folderCopyPath = userFolderPath+"/copy";
		        File folderCopy = new File(folderCopyPath);
		        folderCopy.mkdirs();
		        
		        int cont = userMainImages.length+1;
		        for (File userImage : userFolder.listFiles(imgFilter)) {
		        	String copyImagePath = folderCopyPath+"/"+cameraSelectedName+"_img"+cont+".jpg";
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
		        
		        cont = userMainImages.length+1;
		        for (File userImageCopy : folderCopy.listFiles(imgFilter)) {
		        	String newUserImagePath = userFolderPath+"/"+cameraSelectedName+"_img"+cont+".jpg";
		        	
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
		        
		        JOptionPane.showMessageDialog(null, "Se han restablecido correctamente los nombres de las imágenes");
	        }
	        else{
	        	JOptionPane.showMessageDialog(null, "No existe una ruta para las imágenes de este individuo", "Error ruta de imágenes", JOptionPane.ERROR_MESSAGE);
	        }
		}
		else{
			JOptionPane.showMessageDialog(null, "El nombre del individuo no está especificado", "Error nombre individuo", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	

}
