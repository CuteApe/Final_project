import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;

public class GUI_Test {

	private JFrame frmSpooderfi;
	public static long musicTime = 0;
	public static Clip sound;
	public static double increase;
	public static FloatControl volume;
	public static float dB;
	public JButton play;
	public JSlider slider;
	public JButton stop;
	public JButton pause;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Test window = new GUI_Test();
					window.frmSpooderfi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_Test() {
		initialize();
	}
	
	public void stop() {
		musicTime = 0;
		sound.setMicrosecondPosition(musicTime);
		sound.stop();
	}
	
	public void volume(float slider){
		increase = slider/100;
		volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
		dB = (float)(Math.log(increase)/Math.log(10.0)*20.0);
		volume.setValue(dB);
	}
	
	public void pause() {
		if(sound.isActive()) {
	        musicTime = sound.getMicrosecondPosition();
	        sound.stop();
	        volume(slider.getValue());
		}
	}
	
	public void playMusic(String musicName) {
		if(!sound.isActive())
		{
		try {
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(musicName).getAbsoluteFile());
		    sound = AudioSystem.getClip();
		    sound.open(audioInputStream);
		    sound.setMicrosecondPosition(musicTime);
		    musicTime = 0;
		    sound.start();
		    volume(slider.getValue());
		    }
		   catch(Exception ex) {
		     System.out.println("Error with playing sound.");
		     ex.printStackTrace();
		    }
		 }
	 }
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSpooderfi = new JFrame();
		frmSpooderfi.setResizable(false);
		frmSpooderfi.setTitle("Spötiphy");
		frmSpooderfi.setBounds(100, 100, 450, 300);
		frmSpooderfi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSpooderfi.getContentPane().setLayout(null);
		try {
			sound = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		
		play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playMusic("C:\\Users\\Admin\\Desktop\\Music\\PEWDIEPIE.wav");
			}
		});
		play.setBounds(157, 205, 118, 35);
		frmSpooderfi.getContentPane().add(play);
		
		slider = new JSlider();
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setBounds(0, 74, 43, 191);
		frmSpooderfi.getContentPane().add(slider);
		
		JButton stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		stop.setBounds(55, 210, 97, 25);
		frmSpooderfi.getContentPane().add(stop);
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		btnPause.setBounds(283, 210, 97, 25);
		frmSpooderfi.getContentPane().add(btnPause);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(55, 74, 325, 123);
		frmSpooderfi.getContentPane().add(textPane);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(423, 0, 21, 265);
		frmSpooderfi.getContentPane().add(scrollBar);
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e){
				volume(slider.getValue());
			}
		});
	}
}
