package dpigPersonCapture.igu.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dpigPersonCapture.igu.AdminView;

public class SendForUseActionButtom implements ActionListener{
	
	private SendForUseAction sendForUseAction;
	private AdminView adminView;
	
	public SendForUseActionButtom(AdminView adminView){
		this.adminView = adminView;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String personName = adminView.getCampoUsuario().getText();
		String cameraName = adminView.getComboCamaras().getSelectedItem().toString();
		String saveFolderPath = adminView.getUtil().getFolderCamerasPath() + "/" + cameraName + "/Frames/" + personName;
		String finalPath = adminView.getUtil().getFolderPersonsPath();
		sendForUseAction = new SendForUseAction(personName, cameraName, saveFolderPath, finalPath);
		
		String messageResult = sendForUseAction.sendForUse();
		this.adminView.showMessage(messageResult);
	}
	
}
