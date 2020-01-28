package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dpigPersonCapture.model.IPCamera;
import dpigPersonCapture.smartThings.IPCameraManager;

public class SmartThingsTest {
	
	private String deviceIdCamera1, deviceIdCamera2, token;
	private IPCameraManager ipCameraManager;
	
	@Before
	public void setUp(){
		deviceIdCamera1 = "eb287a69-0e30-4397-9e20-7e26c995bcb7";
		deviceIdCamera2 = "9fd79003-284f-4917-bb6b-a53ee1b82f08";
		token = "82c908bc-daec-4b43-b643-08b90273923e";
		ipCameraManager = new IPCameraManager(token);
	}
	
	@Test
	public void getIPCameras() {
		List<IPCamera> cameras = ipCameraManager.getIPCameras();
		
		assertTrue(cameras.size()>0);
		assertNotNull(cameras);
		
		List<String> cameraNames = new ArrayList<String>();
		List<IPCamera> camarasRegistradasEnEsteMomento = new ArrayList<IPCamera>();
		for (IPCamera ipCamera : cameras) {
			String deviceId = ipCamera.getDeviceId();
			assertTrue(!deviceId.isEmpty());
			assertNotNull(deviceId);//Comprobar que el deviceId no es null
			
			String name = ipCamera.getName();
			if(!cameraNames.contains(name)){
				cameraNames.add(name);
			}
			
			if(deviceId.equals(deviceIdCamera1) || deviceId.equals(deviceIdCamera2)){
				assertNotNull(ipCamera.getName());//Comprobar que el nombre no es null
				assertTrue(!ipCamera.getName().isEmpty());//Comprobar que el nombre no esté vacío
				camarasRegistradasEnEsteMomento.add(ipCamera);
				
			}
		}
		assertEquals(2, camarasRegistradasEnEsteMomento.size());//Comprobar que solo hay dos cámaras, una para cada deviceId definido. No existen cámaras con deviceIds repetidos
		assertEquals(cameraNames.size(), cameras.size());//Se comprueba que no se repite ningún nombre de las cámaras
	}
	
	@Test
	public void getIPCameraVideoURL() throws IOException {
		List<IPCamera> cameras = ipCameraManager.getIPCameras();
		
		for (IPCamera ipCamera : cameras) {
			String videoURL = ipCameraManager.getIPCameraVideoURL(ipCamera.getDeviceId());
			assertNotNull(videoURL);
		}
	}

}
