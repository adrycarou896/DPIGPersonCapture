package dpigPersonCapture.igu.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		String saveFolderPath = adminView.getUtil().getFolderCamerasPath() +"/"+cameraName+"/Frames/"+personName;;
		String finalPath = adminView.getUtil().getFolderPersonsPath();
		saveFrames = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		
		String messageResult = saveFrames.saveFaces();
		this.adminView.showMessage(messageResult);
	}
	
}
