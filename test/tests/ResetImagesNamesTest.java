package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dpigPersonCapture.igu.actions.ResetImagesNamesAction;
import dpigPersonCapture.utils.Messages;

public class ResetImagesNamesTest {
	
	private String personName, cameraName, saveFolderPath, finalPath;
	
	@Before
	public void setUp(){
		personName="individuo1";
		cameraName="Camera1";
		saveFolderPath="test/img/resetImagesNamesTest/cameras/";
		finalPath="test/img/resetImagesNamesTest/individuos/";
	}
	
	@Test
	public void resetImagesNamesImagenesConNombreDesordenado() {
		ResetImagesNamesAction resetImagesNamesAction = new ResetImagesNamesAction(personName, cameraName, saveFolderPath, finalPath);
		String messageResult = resetImagesNamesAction.resetImagesNames();
		assertEquals(Messages.resetImagesNamesSuccesfuly,messageResult);
	}
	
	@Test
	public void resetImagesNamesSaveFolderNotExist() {
		saveFolderPath = "test/img/resetImagesNamesTest/invisibleDirectory/";
		ResetImagesNamesAction resetImagesNamesAction = new ResetImagesNamesAction(personName, cameraName, saveFolderPath, finalPath);
		String messageResult = resetImagesNamesAction.resetImagesNames();
		assertEquals(Messages.trainingPathNotExistError,messageResult);
	}

}
