package dpigPersonCapture.facialDetection;

import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import dpigPersonCapture.utils.Util;

public class FacialDetection {
	 
    private CascadeClassifier Cascade;
    //private CascadeClassifier CascadeEyes;
    
    private MatOfRect rostros;//Guarda los rostros que va capturando
    
    private Map<String, File[]> imagesFalsesPostives;
    
    public FacialDetection(){
    	this.Cascade = new CascadeClassifier(Util.CASCADE_PATH);
    	this.imagesFalsesPostives = new HashMap<String, File[]>();
    	//this.CascadeEyes = new CascadeClassifier("C:\\opencv\\sources\\data\\haarcascades\\haarcascade_eye.xml");
    }

    //Mat frame_gray = new Mat();
    public int detectAndSave(Mat frame, Mat frame_gray, int cont, String camerasFolderPath, String cameraName) throws Exception{
    	this.rostros = new MatOfRect();
    	
    	Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY);//Colvierte la imagene a color a blanco y negro
        Imgproc.equalizeHist(frame_gray, frame_gray);//Valanzeamos los tonos grises
        double w = frame.width();
        double h = frame.height();
        
        //Cascade.detectMultiScale(frame, faceDetections);
        Cascade.detectMultiScale(frame_gray, rostros, 1.1, 2, 0|CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(w, h));
        Rect[] rostrosLista = rostros.toArray();
        
        Rect rectCrop = new Rect();
        
        int numRostro = 0;
        for (Rect rostro : rostrosLista) {
        	
    		//Se recorta la imagen
    		rectCrop = new Rect(rostro.x, rostro.y, rostro.width, rostro.height); 
    		Mat frameRecortado = new Mat(frame,rectCrop);
    		
        	/*MatOfRect ojos = new MatOfRect();
        	CascadeEyes.detectMultiScale(frameRecortado, ojos);
        	Lojos.toArray();
        	for (Rect rect : ojos) {
				
			}*/
        	
    		String srcSalida = camerasFolderPath+"/"+cameraName+"_img"+cont+".jpg";
    		
    		Mat frameFinal = new Mat();
    		Imgproc.resize(frameRecortado, frameFinal, new Size(52,52));
    		
    		//Se guarda la imagen
    		Imgcodecs.imwrite(srcSalida, frameFinal);
    		
    		if(!this.imagesFalsesPostives.containsKey(cameraName)){//Si no se quiere utilizar la bbdd de falsos positivos -> actua normal
    			numRostro++;
        		cont++;
    		}
    		else{
    			if(isImageFalsePositive(cameraName, new File(srcSalida))){
        			new File(srcSalida).delete();
        		}
        		else{
        			numRostro++;
            		cont++;
            		/*
            		 * Test
            		 * Imgcodecs.imwrite(srcSalida, frame);
            		 */
        		}
    		}
        } 
        return numRostro;
    }
    
	public void saveFalsesPositivesImages(String ipCameraName, String imagesFalsePostivePath){
		//Recoger todas las imágenes de la carpeta donde las guardo
		FilenameFilter imgFilter = new FilenameFilter() { 
			public boolean accept(File dir, String name) { 
                name = name.toLowerCase(); 
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png"); 
            } 
        }; 
        
    	File root = new File(imagesFalsePostivePath); 
    	
    	if(!imagesFalsesPostives.containsKey(ipCameraName)){
    		imagesFalsesPostives.put(ipCameraName, root.listFiles(imgFilter));
		}
	}
	
    public boolean isImageFalsePositive(String cameraName, File newImage){
		File[] imagesFalsePositive = imagesFalsesPostives.get(cameraName);
    	for (File imageFalsePositive : imagesFalsePositive) {
			if(compareImage(imageFalsePositive, newImage)){
				return true;
			}
		}
    	return false;
    }
	
	private boolean compareImage(File fileA, File fileB) {        
	    try {
	        //take buffer data from botm image files //
	        BufferedImage biA = ImageIO.read(fileA);
	        DataBuffer dbA = biA.getData().getDataBuffer();
	        int sizeA = dbA.getSize();                      
	        BufferedImage biB = ImageIO.read(fileB);
	        DataBuffer dbB = biB.getData().getDataBuffer();
	        int sizeB = dbB.getSize();
	        //compare data-buffer objects //
	        if(sizeA == sizeB) {
	            for(int i=0; i<sizeA; i++) { 
	                if(dbA.getElem(i) != dbB.getElem(i)) {
	                    return false;
	                }
	            }
	            return true;
	        }
	        else {
	            return false;
	        }
	    } 
	    catch (Exception e) { 
	        //"Failed to compare image files ..."
	        return  false;
	    }
	}
    
    public static void resizePrueba(BufferedImage src, OutputStream output, int width, int height) throws Exception {
    	//BufferedImage src = GraphicsUtilities.createThumbnail(ImageIO.read(file), 300);
	    BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = dest.createGraphics();
	    AffineTransform at = AffineTransform.getScaleInstance
	    		((double)width / src.getWidth(), 
	    				(double)height / src.getHeight());
	    g.drawRenderedImage(src, at);
	    ImageIO.write(dest, "JPG", output);
	    output.close();
	}
    
    public Mat readInputStreamIntoMat(InputStream inputStream) throws IOException {
	    // Read into byte-array
	    byte[] temporaryImageInMemory = readStream(inputStream);
	    Mat outputImage = Imgcodecs.imdecode(new MatOfByte(temporaryImageInMemory), -1);
	    return outputImage;
	}
    
    private byte[] readStream(InputStream stream) throws IOException {
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

	public Map<String, File[]> getImagesFalsesPostives() {
		return imagesFalsesPostives;
	}
	
	
}

