package dpigPersonCapture.igu.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dpigPersonCapture.facialDetection.FacialDetection;
import dpigPersonCapture.igu.AdminView;

public class SaveFacesActionButtom implements ActionListener{
	
	private SaveFacesAction saveFrames;
	private AdminView adminView;
	
	public SaveFacesActionButtom(AdminView adminView){
		this.adminView = adminView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String personName = adminView.getCampoUsuario().getText();
		String cameraName = adminView.getComboCamaras().getSelectedItem().toString();
		String videoPath = adminView.getVideoPath();
		String saveFolderPath = adminView.getUtil().getFolderCamerasPath();
		String finalPath = adminView.getUtil().getFolderPersonsPath();
		FacialDetection facialDetection = adminView.getFacialDetection();
		saveFrames = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath,facialDetection);
		
		String messageResult = saveFrames.saveFaces();
		this.adminView.showMessage(messageResult);
	}
	
}
