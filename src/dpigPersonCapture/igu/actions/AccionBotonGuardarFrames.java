package dpigPersonCapture.igu.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import dpigPersonCapture.facialDetection.FacialDetection;
import dpigPersonCapture.igu.AdminView;
import dpigPersonCapture.reader.ReadVideoFrames;

public class AccionBotonGuardarFrames implements ActionListener{
	
	private AdminView ventanaPrincipal;
	
	public AccionBotonGuardarFrames(AdminView ventanaPrincipal){
		this.ventanaPrincipal = ventanaPrincipal;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		FacialDetection reconocimientoFacialPrueba = new FacialDetection();
		reconocimientoFacialPrueba.setConf();

		String cameraSelectedName = ventanaPrincipal.getComboCamaras().getSelectedItem().toString();
		String userName = ventanaPrincipal.getCampoUsuario().getText();
		if(ventanaPrincipal.getVideoPath()!=null){
			if(!userName.isEmpty()){
				String userFolder = this.ventanaPrincipal.getUtil().getFolderCamerasPath()+"/"+cameraSelectedName+"/Frames/"+userName;
				File videoFolder = new File(userFolder);
				if(!videoFolder.exists()){
					videoFolder.mkdirs();
				}
				
				ReadVideoFrames decodeAndCaptureFrames;
				try {
					decodeAndCaptureFrames = new ReadVideoFrames(ventanaPrincipal.getVideoPath());
					List<BufferedImage>images = decodeAndCaptureFrames.getImages();
					
					FilenameFilter imgFilter = new FilenameFilter() { 
						public boolean accept(File dir, String name) { 
			                name = name.toLowerCase(); 
			                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png"); 
			            } 
			        }; 
			        
					 String userMainFolderPath = this.ventanaPrincipal.getUtil().getFolderUsersPath()+"/"+userName;
			         File userMainFolder = new File(userMainFolderPath);
			         if(!userMainFolder.exists()){
			        	 userMainFolder.mkdirs();
			          }
				       
				    File[] userMainImages = userMainFolder.listFiles(imgFilter);
				        
				        
				    int cont = userMainImages.length+1;
					for (BufferedImage image : images) {
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						ImageIO.write(image, "jpg", os);
						InputStream is = new ByteArrayInputStream(os.toByteArray());
						
						Mat frame = readInputStreamIntoMat(is);
						Mat frame_gray = new Mat();
						
						cont = cont + reconocimientoFacialPrueba.reconocerRostroYGuardar(frame, frame_gray, cont, userFolder, cameraSelectedName);
					}
					
					JOptionPane.showMessageDialog(null, "Se han detectado y almacenado los rostros correctamente");
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
			}
			else{
				JOptionPane.showMessageDialog(null, "El nombre del individuo no está especificado", "Error nombre individuo", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "No existe ningún video", "Error video", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private static Mat readInputStreamIntoMat(InputStream inputStream) throws IOException {
	    // Read into byte-array
	    byte[] temporaryImageInMemory = readStream(inputStream);

	    // Decode into mat. Use any IMREAD_ option that describes your image appropriately
	    //Mat outputImage = Imgcodecs.imdecode(new MatOfByte(temporaryImageInMemory), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
	    Mat outputImage = Imgcodecs.imdecode(new MatOfByte(temporaryImageInMemory), -1);
	    
	    return outputImage;
	}
	
	
	
	private static byte[] readStream(InputStream stream) throws IOException {
	    // Copy content of the image to byte-array
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    int nRead;
	    byte[] data = new byte[16384];

	    while ((nRead = stream.read(data, 0, data.length)) != -1) {
	        buffer.write(data, 0, nRead);
	    }

	    buffer.flush();
	    byte[] temporaryImageInMemory = buffer.toByteArray();
	    buffer.close();
	    stream.close();
	    return temporaryImageInMemory;
	}

}
