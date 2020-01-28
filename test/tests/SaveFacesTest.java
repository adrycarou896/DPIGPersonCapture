package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dpigPersonCapture.facialDetection.FacialDetection;
import dpigPersonCapture.igu.actions.SaveFacesAction;
import dpigPersonCapture.utils.Messages;
import dpigPersonCapture.utils.Util;

public class SaveFacesTest {
	
	private String personName, cameraName, videoPath, saveFolderPath, finalPath;
	private FacialDetection facialDetection;
	
	@Before
	public void setUp(){
		System.load(Util.LOAD_OPENCV_PATH);
		
		personName="individuo1";
		cameraName="Camera1";
		videoPath="test/img/saveFaceTest/Camera1.mp4";
		saveFolderPath="test/img/saveFaceTest/cameras/";
		finalPath="test/img/saveFaceTest/individuos/";
		
		facialDetection = new FacialDetection();
	}
	
	@Test
	public void saveFacesNotExistPersonName() {
		personName = null;
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.personNameError,messageResult);
		
		personName = "";
		saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.personNameError,messageResult);
	}
	
	@Test
	public void saveFacesNotExistCameraName() {
		cameraName = null;
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.cameraNameError,messageResult);
		
		cameraName = "";
		saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.cameraNameError,messageResult);
	}
	
	@Test
	public void saveFacesNotExistVideo() {
		videoPath = null;
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.videoError,messageResult);
		
		videoPath = "";
		saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.videoError,messageResult);
	}
	
	@Test
	public void saveFacesNotExistSaveFolderPath() {
		saveFolderPath = null;
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.pathError,messageResult);
		
		saveFolderPath = "";
		saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.pathError,messageResult);
	}
	
	@Test
	public void saveFacesNotExistFinalPath() {
		finalPath = null;
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.pathError,messageResult);
		
		finalPath = "";
		saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.pathError,messageResult);
	}
	
	@Test
	public void saveFacesSuccesfully(){
		SaveFacesAction saveFacesAction = new SaveFacesAction(personName, cameraName, videoPath, saveFolderPath, finalPath, facialDetection);
		String messageResult = saveFacesAction.saveFaces();
		assertEquals(Messages.saveFramesSuccesfully,messageResult);
	}

}
