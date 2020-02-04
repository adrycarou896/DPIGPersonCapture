package dpigPersonCapture.igu.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import dpigPersonCapture.igu.AdminView;
import dpigPersonCapture.model.IPCamera;

public class ViewVideoAction implements ActionListener {
	
	private AdminView ventanaPrincipal;
	
	public ViewVideoAction(AdminView ventanaPrincipal){
		this.ventanaPrincipal = ventanaPrincipal;
	}
	
	public ViewVideoAction(){}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cameraSelectedName = ventanaPrincipal.getComboCamaras().getSelectedItem().toString();
		for (IPCamera ipCamera : ventanaPrincipal.getIPCameras()) {
			if(ipCamera.getName().equals(cameraSelectedName)){
				String videoURL = ventanaPrincipal.getCameraVideoURL(ipCamera);
				if(videoURL!=null){
					String cameraFolderPath = this.ventanaPrincipal.getUtil().getFolderCamerasPath()+"/"+ipCamera.getName();
					
					String videoFolderPath = cameraFolderPath+"/Video";
					File videoFolder = new File(videoFolderPath);
					if(!videoFolder.exists()){
						videoFolder.mkdirs();
					}
					String videoPath = videoFolderPath+"/"+ipCamera.getName()+".mp4";
					
					/*String imagesFalsePostivePath = cameraFolderPath+"/falsesPositivesImages/";
					File falsesPositivesImagesFolder = new File(imagesFalsePostivePath);
					if(!falsesPositivesImagesFolder.exists()){
						falsesPositivesImagesFolder.mkdirs();
					}
					this.ventanaPrincipal.getFacialDetection().saveFalsesPositivesImages(ipCamera.getName(), imagesFalsePostivePath);
					*/
					
					ventanaPrincipal.saveFile(videoURL, videoPath);
					ventanaPrincipal.createScene(videoPath);
				}
				else{
					JOptionPane.showMessageDialog(null, "La cámara no dispone de ningún video para mostrar");
				}
				break;
			}
		}
	}

}
