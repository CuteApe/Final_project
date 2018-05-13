import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.*;

public class GUI_Test extends Main{

	public JFrame frmSpooderfi;
	public static long musicTime = 0;
	public static Clip sound;
	public static double increase;
	public static FloatControl volume;
	public static float dB;
	public JButton play;
	public JSlider slider;
	public JButton stop;
	public JButton pause;
	public String[] displaySongs;
	public JList<String> playList;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GUI_Test window = new GUI_Test();
					window.frmSpooderfi.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_Test() 
	{
		initialize();
	}
	
	public void stop() {
		musicTime = 0;
		sound.setMicrosecondPosition(musicTime);
		sound.stop();
	}
	
	public void volume(float slider)
	{
		increase = slider/100;
		volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
		dB = (float)(Math.log(increase)/Math.log(10.0)*20.0);
		volume.setValue(dB);
	}
	
	public void pause() 
	{
		if(sound.isActive()) 
		{
	        musicTime = sound.getMicrosecondPosition();
	        sound.stop();
	        volume(slider.getValue());
		}
	}
	
	public void playMusic(String musicName) 
	{
		if(!sound.isActive())
		{
			try 
			{
			    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(musicName).getAbsoluteFile());
			    sound = AudioSystem.getClip();
			    sound.open(audioInputStream);
			    sound.setMicrosecondPosition(musicTime);
			    musicTime = 0;
			    sound.start();
			    volume(slider.getValue());
		    }
		   catch(Exception ex) 
			{
			    System.out.println("Error with playing sound.");
			    ex.printStackTrace();
		    }
		}
	 }
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmSpooderfi = new JFrame();
		frmSpooderfi.setResizable(false);
		frmSpooderfi.setTitle("Sp�tiphy");
		frmSpooderfi.setBounds(100, 100, 450, 300);
		frmSpooderfi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSpooderfi.getContentPane().setLayout(null);
		try 
		{
			sound = AudioSystem.getClip();
		} 
		catch (LineUnavailableException e1) 
		{
			e1.printStackTrace();
		}
		
		play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = songsTabel.find(songs.get(playList.getSelectedIndex()));
				playMusic(path);
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
		
		//Creates a JList to contain and display all the possible songs
		displaySongs = new String[songs.size()];
		for (int i = 0; i < songs.size(); i++)
		{
			displaySongs[i] = songs.get(i).getArtist() + " - " + songs.get(i).getAlbum() + " - " + songs.get(i).getSongName() + " - " + songs.get(i).getDuration();
		}
		playList = new JList<String>(displaySongs);
		playList.setLayoutOrientation(JList.VERTICAL); //Set the layout so it looks like a list
		playList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Set selection to single
		JScrollPane listScroller = new JScrollPane(playList);
		listScroller.setBounds(50, 20, 330, 150);
		
		playList.setVisible(true);
		
		frmSpooderfi.getContentPane().add(listScroller);
		
		
		/*textPane.setEditable(false);
		textPane.setBounds(55, 74, 325, 123);
		frmSpooderfi.getContentPane().add(textPane);*/
		
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
