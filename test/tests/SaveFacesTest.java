package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dpigPersonCapture.igu.actions.SaveFacesAction;
import dpigPersonCapture.utils.Messages;
import dpigPersonCapture.utils.Util;

public class SaveFacesTest {
	
	private String personName, cameraName, videoPath, saveFolderPath, finalPath;
	
	@Before
	public void setUp(){
		System.load(Util.LOAD_OPENCV_PATH);
		
		personName="individuo1";
		cameraName="Camera1";
		videoPath="test/img/saveFaceTest/Camera1.mp4";
		saveFolderPath="test/img/saveFaceTest/cameras/";
		finalPath="test/img/saveFaceTest/individuos/";
	}
	
	@Test
	public void PU06() {
		personName = null;
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.personNameError,messageResult);
		
		personName = "";
		saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.personNameError,messageResult);
	}
	
	@Test
	public void PU07() {
		cameraName = null;
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.cameraNameError,messageResult);
		
		cameraName = "";
		saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.cameraNameError,messageResult);
	}
	
	@Test
	public void PU08() {
		videoPath = null;
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.videoError,messageResult);
		
		videoPath = "";
		saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.videoError,messageResult);
	}
	
	@Test
	public void PU09() {
		saveFolderPath = null;
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.pathError,messageResult);
		
		saveFolderPath = "";
		saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.pathError,messageResult);
	}
	
	@Test
	public void PU10() {
		finalPath = null;
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.pathError,messageResult);
		
		finalPath = "";
		saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.pathError,messageResult);
	}
	
	@Test
	public void PU11(){
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.saveFramesSuccesfully,messageResult);
	}

}
