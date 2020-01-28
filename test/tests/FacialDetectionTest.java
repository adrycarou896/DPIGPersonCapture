package tests;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;

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
	public void facialDetectionOneFace() throws Exception {
	
		String imagePath = "test/img/facialDetectionTest/oneFace.jpg";
		File image=new File(imagePath);
		BufferedImage img=ImageIO.read(image);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", os);
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		Mat frame = facialDetection.readInputStreamIntoMat(is);
		Mat frame_gray = new Mat();
		
		int numRostrosDetectados = facialDetection.detectAndSave(frame, frame_gray, 0, "test/img/facialDetectionTest/facesDetectedOneFace/", "Camera1");
		assertEquals(1, numRostrosDetectados);
		
	}
	
	@Test
	public void facialDetectionTwoFaces() throws Exception {
		
		String imagePath = "test/img/facialDetectionTest/twoFaces.jpg";
		File image=new File(imagePath);
		BufferedImage img=ImageIO.read(image);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", os);
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		Mat frame = facialDetection.readInputStreamIntoMat(is);
		Mat frame_gray = new Mat();
		
		int numRostrosDetectados = facialDetection.detectAndSave(frame, frame_gray, 0, "test/img/facialDetectionTest/facesDetectedTwoFaces/", "Camera1");
		assertEquals(2, numRostrosDetectados);
		
	}
	
	@Test
	public void facialDetectionCeroFaces() throws Exception {
		
		String imagePath = "test/img/facialDetectionTest/ceroFaces.jpg";
		File image=new File(imagePath);
		BufferedImage img=ImageIO.read(image);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", os);
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		Mat frame = facialDetection.readInputStreamIntoMat(is);
		Mat frame_gray = new Mat();
		
		int numRostrosDetectados = facialDetection.detectAndSave(frame, frame_gray, 0, "test/img/facialDetectionTest/facesDetectedCeroFaces", "Camera1");
		assertEquals(0, numRostrosDetectados);
		
	}
}
