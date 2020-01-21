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

public class AccionBotonGuardarParaUso implements ActionListener{
	
	private VentanaPrincipal ventanaPrincipal;
	
	public AccionBotonGuardarParaUso(VentanaPrincipal ventanaPrincipal){
		this.ventanaPrincipal = ventanaPrincipal;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
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
	        
	        String userMainFolderPath = this.ventanaPrincipal.getUtil().getFolderUsersPath()+"/"+userName;
	        File userMainFolder = new File(userMainFolderPath);
	        
	        if(!userMainFolder.exists()){
	        	userMainFolder.mkdir();
	        }
	        
	        if(userFolder.exists()){
	        	 for (File userImage : userFolder.listFiles(imgFilter)) {
	        		 try{
	 	        		FileInputStream fis = new FileInputStream(userImage); //inFile -> Archivo a copiar
	 	        		
	 	        		String[] userImagePathArray = userImage.getAbsolutePath().split(cameraSelectedName);
	 	        		String userImageExtensionName = userImagePathArray[userImagePathArray.length-1];
	 	        		FileOutputStream fos = new FileOutputStream(userMainFolderPath+"/"+cameraSelectedName+userImageExtensionName); //outFile -> Copia del archivo
	 	        		
	 	        		FileChannel inChannel = fis.getChannel();
	 	        		FileChannel outChannel = fos.getChannel();
	 	        		inChannel.transferTo(0, inChannel.size(), outChannel);
	 	        		fos.close();
	 	        		fis.close();	
	 	        	
	 	        	}catch (IOException ioe) {
	 	        		System.err.println("Error al Generar Copia 1");
	 		        }
	        	 }
	 	        JOptionPane.showMessageDialog(null, "Las imágenes se han enviado correctamente para su uso");
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
