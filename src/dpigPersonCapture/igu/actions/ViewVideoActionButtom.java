package dpigPersonCapture.igu.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import dpigPersonCapture.igu.AdminView;
import dpigPersonCapture.model.IPCamera;
import dpigPersonCapture.utils.Messages;

public class ViewVideoActionButtom implements ActionListener {
	
	private AdminView adminView;
	
	public ViewVideoActionButtom(AdminView ventanaPrincipal){
		this.adminView = ventanaPrincipal;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cameraSelectedName = adminView.getComboCamaras().getSelectedItem().toString();
		for (IPCamera ipCamera : adminView.getIPCameras()) {
			if(ipCamera.getName().equals(cameraSelectedName)){
				String videoURL = adminView.getCameraVideoURL(ipCamera);
				if(videoURL!=null){
					String cameraFolderPath = this.adminView.getUtil().getFolderCamerasPath()+"/"+ipCamera.getName();
					
					String videoFolderPath = cameraFolderPath+"/Video";
					File videoFolder = new File(videoFolderPath);
					if(!videoFolder.exists()){
						videoFolder.mkdirs();
					}
					String videoPath = videoFolderPath+"/"+ipCamera.getName()+".mp4";
					
					adminView.saveFile(videoURL, videoPath);
					adminView.createScene(videoPath);
				}
				else{
					this.adminView.showMessage(Messages.viewVideoError);
				}
				break;
			}
		}
	}

}
