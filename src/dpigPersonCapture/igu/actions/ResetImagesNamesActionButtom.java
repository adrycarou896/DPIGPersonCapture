package dpigPersonCapture.igu.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dpigPersonCapture.igu.AdminView;

public class ResetImagesNamesActionButtom implements ActionListener{
	
	private ResetImagesNamesAction restImagesNamesAction;
	private AdminView adminView;
	
	public ResetImagesNamesActionButtom(AdminView ventanaPrincipal){
		this.adminView = ventanaPrincipal;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String personName = adminView.getCampoUsuario().getText();
		String cameraName = adminView.getComboCamaras().getSelectedItem().toString();
		String saveFolderPath = adminView.getUtil().getFolderCamerasPath() +"/"+cameraName+"/Frames/"+personName;
		String finalPath = adminView.getUtil().getFolderPersonsPath();
		restImagesNamesAction = new ResetImagesNamesAction(personName, cameraName, saveFolderPath, finalPath);
		
		String messageResult = restImagesNamesAction.resetImagesNames();
		this.adminView.showMessage(messageResult);
	}
	
	

}
