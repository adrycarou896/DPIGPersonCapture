package dpigPersonCapture.igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import dpigPersonCapture.igu.actions.ResetImagesNamesActionButtom;
import dpigPersonCapture.igu.actions.SaveFacesActionButtom;
import dpigPersonCapture.igu.actions.SendForUseActionButtom;
import dpigPersonCapture.igu.actions.ViewVideoActionButtom;
import dpigPersonCapture.model.IPCamera;
import dpigPersonCapture.smartThings.IPCameraManager;
import dpigPersonCapture.utils.Messages;
import dpigPersonCapture.utils.Util;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class AdminView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	IPCameraManager ipCamerasManager;  
	List<IPCamera> ipCameras;
	
	private String videoPath;
	private JPanel panelTop;
	private JLabel lblCmaras;
	private JComboBox<String> comboCamaras;
	private JLabel label;
	private JLabel label_2;
	private JPanel panelVideo;
	private JPanel panelDown;
	private JLabel label_1;
	private JButton btVerVideo;
	private JLabel lblNombreDelUsuario;
	private JTextField campoUsuario;
	private JButton btGuardarRostros;
	private JButton btRestablecerNombres;
	
	private final JFXPanel jfxPanel = new JFXPanel(); 
	private JPanel panel_1;
	private JPanel panel_2;
	private JButton btGuardarParaUso;
	
	private Util util;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		args = new String[3];
		args[0] = "img/pruebas";
		args[1] = "D:/TFG/PRUEBA/entrenamiento";
		//args[1] = "D:/TFG/DPIGServer/src/test/java/files/trainingImages/individuo1";
		args[2] = "82c908bc-daec-4b43-b643-08b90273923e";
		String folderCamerasPath = args[0];
		String folderUsersPath = args[1];
		String smartThingsToken = args[2];
		
		Util util = new Util();
		util.setFolderCamerasPath(folderCamerasPath);
		util.setFolderPersonsPath(folderUsersPath);
		util.setSmartThingsToken(smartThingsToken);
		
		IPCameraManager ipCameraManager = new IPCameraManager(smartThingsToken);
		try {
			ipCameraManager.getSimpleRequest(Util.SMARTTHINGS_DEVICES);
			if(!new File(folderCamerasPath).exists()){
				new File(folderCamerasPath).mkdirs();
			}
			if(!new File(folderUsersPath).exists()){
				new File(folderUsersPath).mkdirs();
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						AdminView frame = new AdminView(util, ipCameraManager);
						frame.setVisible(true);	
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			System.out.println("El token introducido no es v·lido");
		}
	}

	/**
	 * Create the frame.
	 */
	public AdminView(Util util, IPCameraManager ipCameraManager) {
		setTitle("DPIGPersonCapture");
		System.load(Util.LOAD_OPENCV_PATH);
		
		this.util = util;
		this.ipCamerasManager = ipCameraManager;
		this.ipCameras = ipCamerasManager.getIPCameras();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 973, 777);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		getPanel();
		
		
	}
	
	private JPanel getPanel(){
		contentPane.setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(getPanelTop(), BorderLayout.NORTH);
		panel.add(getPanelVideo(), BorderLayout.CENTER);
		panel.add(getPanelDown(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	public void createScene(String videoPath){
		this.videoPath = videoPath;
        Platform.runLater(new Runnable() {
             @Override
             public void run() {                 
                File file = new File(videoPath);                                  
                    MediaPlayer oracleVid = new MediaPlayer(                                       
                        new Media(file.toURI().toString())
                    );
                    //se a√±ade video al jfxPanel
                    jfxPanel.setScene(new Scene(new Group(new MediaView(oracleVid))));                    
                    oracleVid.setVolume(0.7);//volumen
                    oracleVid.setCycleCount(MediaPlayer.INDEFINITE);//repite video
                    oracleVid.play();//play video
             }
        });
    }
	
	public List<IPCamera> getIPCameras(){
		return this.ipCameras;
	}
	
	public String getCameraVideoURL(IPCamera camera){
		try {
			return ipCamerasManager.getIPCameraVideoURL(camera.getDeviceId());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveFile(String videoURL, String destinationFile){
		try {
			this.ipCamerasManager.saveVideoFromURL(videoURL, destinationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getVideoPath(){
		return this.videoPath;
	}
	private JPanel getPanelTop() {
		if (panelTop == null) {
			panelTop = new JPanel();
			panelTop.setBackground(Color.WHITE);
			panelTop.setLayout(new GridLayout(1, 0, 0, 0));
			panelTop.add(getLblCmaras());
			panelTop.add(getComboCamaras());
			panelTop.add(getBtVerVideo());
			panelTop.add(getLabel_1());
			panelTop.add(getLabel());
			panelTop.add(getLabel_2());
		}
		return panelTop;
	}
	private JLabel getLblCmaras() {
		if (lblCmaras == null) {
			lblCmaras = new JLabel("C\u00E1maras: ");
			lblCmaras.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblCmaras;
	}
	public JComboBox<String> getComboCamaras() {
		if (comboCamaras == null) {
			comboCamaras = new JComboBox<String>();
			comboCamaras.setForeground(Color.BLACK);
			
			for (IPCamera ipCamera : ipCameras) {
				this.comboCamaras.addItem(ipCamera.getName());
			}
		}
		return comboCamaras;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("");
		}
		return label;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("");
		}
		return label_2;
	}
	private JPanel getPanelVideo() {
		if (panelVideo == null) {
			panelVideo = new JPanel();
			panelVideo.setLayout(new BorderLayout(0, 0));
			jfxPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			
			jfxPanel.setBounds(10, 90, 882, 604);
			panelVideo.add(jfxPanel);
			panelVideo.add(getPanel_1(), BorderLayout.NORTH);
			panelVideo.add(getPanel_2(), BorderLayout.SOUTH);
		}
		return panelVideo;
	}
	private JPanel getPanelDown() {
		if (panelDown == null) {
			panelDown = new JPanel();
			panelDown.setBackground(Color.WHITE);
			panelDown.setLayout(new GridLayout(1, 0, 0, 0));
			panelDown.add(getLblNombreDelUsuario());
			panelDown.add(getCampoUsuario());
			panelDown.add(getBtGuardarRostros());
			panelDown.add(getBtGuardarParaUso());
			panelDown.add(getBtRestablecerNombres());
		}
		return panelDown;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("");
		}
		return label_1;
	}
	private JButton getBtVerVideo() {
		if (btVerVideo == null) {
			btVerVideo = new JButton("Ver video");
			btVerVideo.setToolTipText("Visualizar el \u00FAltimo video de la c\u00E1mara seleccionada");
			btVerVideo.addActionListener(new ViewVideoActionButtom(this));
		}
		return btVerVideo;
	}
	private JLabel getLblNombreDelUsuario() {
		if (lblNombreDelUsuario == null) {
			lblNombreDelUsuario = new JLabel("Nombre del individuo: ");
			lblNombreDelUsuario.setBackground(Color.WHITE);
			lblNombreDelUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblNombreDelUsuario;
	}
	public JTextField getCampoUsuario() {
		if (campoUsuario == null) {
			campoUsuario = new JTextField();
			campoUsuario.setColumns(10);
		}
		return campoUsuario;
	}
	private JButton getBtGuardarRostros() {
		if (btGuardarRostros == null) {
			btGuardarRostros = new JButton("Guardar rostros");
			btGuardarRostros.setFocusPainted(false);
			btGuardarRostros.setToolTipText("Guardar todos los rostros detectados en el actual video");
			btGuardarRostros.addActionListener(new SaveFacesActionButtom(this));
		}
		return btGuardarRostros;
	}
	private JButton getBtRestablecerNombres() {
		if (btRestablecerNombres == null) {
			btRestablecerNombres = new JButton("Restablecer nombres");
			btRestablecerNombres.setFocusPainted(false);
			btRestablecerNombres.addActionListener(new ResetImagesNamesActionButtom(this));
		}
		return btRestablecerNombres;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setBackground(Color.WHITE);
		}
		return panel_1;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setBackground(Color.WHITE);
		}
		return panel_2;
	}
	private JButton getBtGuardarParaUso() {
		if (btGuardarParaUso == null) {
			btGuardarParaUso = new JButton("Guardar para uso");
			btGuardarParaUso.setFocusPainted(false);
			btGuardarParaUso.setToolTipText("Enviar im\u00E1genes al directorio destino para su uso");
			btGuardarParaUso.addActionListener(new SendForUseActionButtom(this));
		}
		return btGuardarParaUso;
	}
	
	public Util getUtil(){
		return this.util;
	}
	
	public void showMessage(String message){
		switch (message) {
		case Messages.saveFramesSuccesfully:
			JOptionPane.showMessageDialog(null, Messages.saveFramesSuccesfully);
			break;
		case Messages.personNameError:
			JOptionPane.showMessageDialog(null, Messages.saveFramesSuccesfully, Messages.personNameErrorHeader, JOptionPane.ERROR_MESSAGE);
			break;
		case Messages.cameraNameError:
			JOptionPane.showMessageDialog(null, Messages.cameraNameError, Messages.cameraNameErrorHeader, JOptionPane.ERROR_MESSAGE);
			break;
		case Messages.videoError:
			JOptionPane.showMessageDialog(null, Messages.videoError, Messages.videoErrorHeader, JOptionPane.ERROR_MESSAGE);
			break;
		case Messages.pathError:
			JOptionPane.showMessageDialog(null, Messages.pathError, Messages.pathErrorHeader, JOptionPane.ERROR_MESSAGE);
			break;
		case Messages.resetImagesNamesSuccesfuly:
			JOptionPane.showMessageDialog(null, Messages.resetImagesNamesSuccesfuly);
			break;
		case Messages.trainingPathNotExistError:
			JOptionPane.showMessageDialog(null, Messages.trainingPathNotExistError, Messages.trainingPathNotExistErrorHeader, JOptionPane.ERROR_MESSAGE);
			break;
		case Messages.viewVideoError:
			JOptionPane.showMessageDialog(null, Messages.viewVideoError, Messages.viewVideoErrorHeader, JOptionPane.ERROR_MESSAGE);
			break;
		case Messages.sendForUseSuccesfuly:
			JOptionPane.showMessageDialog(null, Messages.sendForUseSuccesfuly);
			break;
		default:
			break;
		}
		
		
	}
	
}
