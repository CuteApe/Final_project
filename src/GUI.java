import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.sampled.*;
import java.awt.event.*;

public class GUI extends Main{

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
	public String lastSong = "";
	public String path = "";
	public JLabel artistLabel;
	public JLabel songLabel;
	public JTextField artistTextField;
	public JTextField songTextField;
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
					GUI window = new GUI();
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
	public GUI() 
	{
		initialize();
	}
	
	public void stop() {
		musicTime = 0;
		sound.setMicrosecondPosition(musicTime);
		volume(slider.getValue());
		sound.stop();
	}
	
	public void volume(float slider)
	{
		if(sound.isActive())
		{
			increase = slider/100;
			volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
			dB = (float)(Math.log(increase)/Math.log(10.0)*20.0);
			volume.setValue(dB);
		}
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
		frmSpooderfi.setTitle("Spötiphy");
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
				volume(slider.getValue());
				path = songsTabel.find(songs.get(playList.getSelectedIndex()));
				if(sound.isActive())
				{
					pause();
				}
				if(!lastSong.equals(path) && sound.isActive())
				{
					musicTime = 0;
					pause();
				}
				lastSong = path;
				System.out.println(!songTextField.equals(""));
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
				volume(slider.getValue());
			}
		});
		stop.setBounds(55, 210, 97, 25);
		frmSpooderfi.getContentPane().add(stop);
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
				volume(slider.getValue());
			}
		});
		btnPause.setBounds(283, 210, 97, 25);
		frmSpooderfi.getContentPane().add(btnPause);
		
		// Creates textfields and Labels where you can search for your favorite artist or song
		artistLabel = new JLabel("Search for artist");
		artistLabel.setBounds(0,0,100,15);
		frmSpooderfi.getContentPane().add(artistLabel);
		
		artistTextField = new JTextField();
		artistTextField.setBounds(0, 20, 100, 20);
		frmSpooderfi.getContentPane().add(artistTextField);
		
		songLabel = new JLabel("Search for song");
		songLabel.setBounds(120,0,100,15);
		frmSpooderfi.getContentPane().add(songLabel);
		
		songTextField = new JTextField();
		songTextField.setBounds(120, 20, 100, 20);
		frmSpooderfi.getContentPane().add(songTextField);
		
		
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
		listScroller.setBounds(50, 50, 330, 150);
		
		playList.setVisible(true);
		
		frmSpooderfi.getContentPane().add(listScroller);
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e){
				volume(slider.getValue());
			}
		});
		
	}
}
