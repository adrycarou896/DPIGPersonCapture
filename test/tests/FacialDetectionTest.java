package tests;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import dpigPersonCapture.facialDetection.FacialDetection;
import dpigPersonCapture.utils.Util;

public class FacialDetectionTest {
	
	FacialDetection facialDetection;
	
	@Before
	public void setUp(){
		System.load(Util.LOAD_OPENCV_PATH);
		facialDetection = new FacialDetection();
		facialDetection.saveFalsesPositivesImages("Camera1","test/img/facialDetectionTest/falsesPositivesImages");
	}
	
	@Test
	public void PU03() throws Exception {
	
		String imagePath = "test/img/facialDetectionTest/oneFace.jpg";
		File image=new File(imagePath);
		BufferedImage img=ImageIO.read(image);
		
		int numRostrosDetectados = facialDetection.detectAndSave(img, 0, "test/img/facialDetectionTest/facesDetectedOneFace/", "Camera1");
		assertEquals(1, numRostrosDetectados);
		
	}
	
	@Test
	public void PU04() throws Exception {
		
		String imagePath = "test/img/facialDetectionTest/twoFaces.jpg";
		File image=new File(imagePath);
		BufferedImage img=ImageIO.read(image);
		
		int numRostrosDetectados = facialDetection.detectAndSave(img, 0, "test/img/facialDetectionTest/facesDetectedTwoFaces/", "Camera1");
		assertEquals(2, numRostrosDetectados);
		
	}
	
	@Test
	public void PU05() throws Exception {
		
		String imagePath = "test/img/facialDetectionTest/ceroFaces.jpg";
		File image=new File(imagePath);
		BufferedImage img=ImageIO.read(image);
		
		int numRostrosDetectados = facialDetection.detectAndSave(img, 0, "test/img/facialDetectionTest/facesDetectedCeroFaces", "Camera1");
		assertEquals(0, numRostrosDetectados);
		
	}
}
