package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import dpigPersonCapture.igu.actions.SendForUseAction;
import dpigPersonCapture.utils.Messages;

public class SendForUseTest {
	
	private String personName, cameraName, saveFolderPath, finalPath;
	
	@Before
	public void setUp(){
		personName="individuo1";
		cameraName="Camera1";
		saveFolderPath="test/img/sendForUseTest/cameras/";
		finalPath="test/img/sendForUseTest/individuos/";
	}
	
	@Test
	public void PU14() {
		saveFolderPath = "test/img/sendForUseTest/invisibleDirectory/";
		SendForUseAction sendForUseAction = new SendForUseAction(personName, cameraName, saveFolderPath, finalPath);
		String messageResult = sendForUseAction.sendForUse();
		assertEquals(Messages.trainingPathNotExistError,messageResult);
	}
	
	@Test
	public void PU15() {
		finalPath = "test/img/sendForUseTest/individuosCarpetaAntesInexistente/";
		SendForUseAction sendForUseAction = new SendForUseAction(personName, cameraName, saveFolderPath, finalPath);
		String messageResult = sendForUseAction.sendForUse();
		assertEquals(Messages.sendForUseSuccesfuly,messageResult);
		new File(finalPath).delete();
	}

}
