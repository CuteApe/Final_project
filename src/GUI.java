import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.sampled.*;
import java.awt.event.*;

public class GUI extends Main
{
	public JFrame frmSpooderfi;
	private static long musicTime = 0;
	private static Clip sound;
	private static double increase;
	private static FloatControl volume;
	private static float dB;
	private JSlider slider;
	JProgressBar timeBar;
	Song currentSong;
	private JButton 
		play,
		pause,
		sortArtist,
		sortSong,
		sortDur;
	private String[] displaySongs;
	private JList<Song> playList;
	private String[] displaySearch;
	private String lastSong = "";
	private String path = "";
	private String filter = "";
	private JLabel searchLabel;
	private JTextField searchTextField;
	private DefaultListModel<Song> dlm;
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	/**
	 * The constructor of GUI
	 */
	public GUI() 
	{
		initialize();
	}
	/**
	 *  Stops the clip from playing and resets the start time of the clip to zero
	 */
	public void stop() 
	{
		musicTime = 0;
		sound.setMicrosecondPosition(musicTime);
		volume(slider.getValue());
		sound.stop();
	}
	/**
	 * Changes the volume of sound
	 * @param slider which is a float
	 */
	public void volume(float vSlider)
	{
		if(sound.isActive())
		{
			increase = vSlider/100;
			volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
			dB = (float)(Math.log(increase)/Math.log(10.0)*20.0);
			volume.setValue(dB);
		}
	}
	/**
	 *  Stops sound and remembers when it was stopped
	 */
	public void pause() 
	{
		if(sound.isActive()) 
		{
	        musicTime = sound.getMicrosecondPosition();
	        sound.stop();
	        volume(slider.getValue());
		}
	}
	
	/**
	 * Gets called every second to update the "progress" bar which shows how far into the Song you are.
	 */
	final Runnable UpdateBar = new Runnable() 
	{
       public void run() 
       { 
    	   autoPlay();
    	   	int secPos = (int)sound.getMicrosecondPosition()/1000000;
    	   	double sec = secPos;
    	   	int min = (int) sec/60;
    		sec -= min*60;
    		sec /= 100;
    		double time = min + sec;
   			timeBar.setValue(secPos);
   			DecimalFormat df = new DecimalFormat("0.00");
   			timeBar.setString(String.valueOf(df.format(time).replace(',', ':')));
   			
       }
     };
     
        public void autoPlay() 
        { 
        	if(timeBar.getValue() == timeBar.getMaximum())
        	{
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		if(playList.getSelectedIndex()+1 == dlm.getSize() )
        		{
        			currentSong = dlm.getElementAt(0);
        			playList.setSelectedIndex(0);
        		}
        		else
        		{
        			currentSong = dlm.getElementAt(playList.getSelectedIndex()+1);
        			playList.setSelectedIndex(playList.getSelectedIndex()+1);
        		}
				path = songsTabel.find(currentSong);
				lastSong = path;
				playMusic(path, slider.getValue());
				timeBar.setMaximum(currentSong.getSeconds());
        	}
        }
	/**
	 * Plays sound at and uses the right time if sound has been paused
	 * @param musicName
	 */
	public void playMusic(String musicName, float vSlider) 
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
			    increase = vSlider/100;
				volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
				dB = (float)(Math.log(increase)/Math.log(10.0)*20.0);
				volume.setValue(dB);
		    }
		   catch(Exception ex) 
			{
			    System.out.println("Error with playing sound.");
			    ex.printStackTrace();
		    }
		}
	 }
	/**
	 * Sorts the DefaultListModel depending on s 
	 * @param s - value to sort by
	 * @see DefaultListModel
	 */
	public void sortBy(String s)
	{
		Comparator<Song> comp;
		
		if(s == "Duration")
			comp = new CompDuration();
		else if(s == "Artist")
			comp = new CompArtist();
		else 
			comp = new CompSong();
		
		Collections.sort(songs, comp);
		
		dlm.clear();
		for (int i = 0; i < songs.size(); i++)
			dlm.addElement(songs.get(i));
		filterList(filter);
	}
	/**
	 * Filters the songlist
	 * @param filter - the String to filter the list by
	 */
	public void filterList(String filter)
	{
		dlm.clear();
		for(Song s: songs)
			if(s.toString().toLowerCase().contains(filter.toLowerCase()))
				dlm.addElement(s);
	}
	/**
	 * Initialize the contents of the frame.
	 * And the function of the JButtons,JList and JSlider are here too
	 */
	private void initialize() 
	{
		executor.scheduleAtFixedRate(UpdateBar, 100, 1000, TimeUnit.MILLISECONDS);
	
		//Creates a dlm and a frame
		dlm = new DefaultListModel<Song>();
		
		frmSpooderfi = new JFrame();
		frmSpooderfi.setResizable(false);
		frmSpooderfi.setTitle("Sp�tiphy");
		frmSpooderfi.setBounds(100, 100, 460, 400);
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
		
		// Creates the Play Button and gives its function and adds it to the frame
		play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volume(slider.getValue());	
				currentSong = dlm.getElementAt(playList.getSelectedIndex());
				path = songsTabel.find(currentSong);
						
				if(sound.isActive())
				{
					stop();
				}
				if(!lastSong.equals(path))
				{
					musicTime = 0;
					volume(slider.getValue());
				}
				lastSong = path;
				playMusic(path, slider.getValue());
				timeBar.setMaximum(currentSong.getSeconds());
			}
		});
		play.setBounds(55, 320, 120, 35);
		frmSpooderfi.getContentPane().add(play);
	
		timeBar = new JProgressBar(0, 1);
		timeBar.setStringPainted(true);
		timeBar.setBounds(55, 300, 370, 20);
		timeBar.setString("");
		frmSpooderfi.getContentPane().add(timeBar);
		
		// Creates the Title Button and gives its function and adds it to the frame
		JButton sortSong = new JButton("Titel");
		sortSong.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sortBy("Song");
			}
		});
		sortSong.setBounds(55, 75, 75, 25);
		frmSpooderfi.getContentPane().add(sortSong);
		
		// Creates the Artist Button and gives its function and adds it to the frame
		JButton sortArtist = new JButton("Artist");
		sortArtist.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sortBy("Artist");
			}
		});
		sortArtist.setBounds(130, 75, 75, 25);
		frmSpooderfi.getContentPane().add(sortArtist);
		
		// Creates the Duration Button and gives its function and adds it to the frame
		JButton sortDuration = new JButton("Duration");
		sortDuration.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sortBy("Duration");
			}
		});
		sortDuration.setBounds(205, 75, 90, 25);
		frmSpooderfi.getContentPane().add(sortDuration);
		
		// Creates the Pause Button and gives its function and adds it to the frame
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				pause();
				volume(slider.getValue());
			}
		});
		btnPause.setBounds(180, 320, 120, 35);
		frmSpooderfi.getContentPane().add(btnPause);
		
		// Creates Textfields and Labels where you can search for your favorite artist or song
		// If somthing is written in the TextField then it will use filterList
		searchLabel = new JLabel("Search");
		searchLabel.setBounds(0,0,100,15);
		frmSpooderfi.getContentPane().add(searchLabel);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(0, 20, 100, 20);
		searchTextField.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() 
            {
                filter = searchTextField.getText();
                filterList(filter);
            }
        });
		frmSpooderfi.getContentPane().add(searchTextField);
		
		// dlm gets everysong
		for (Song song: songs)
			dlm.addElement(song);
		
		// playList gets its value and we set layout of it
		playList = new JList<Song>(dlm);
		playList.setLayoutOrientation(JList.VERTICAL); //Set the layout so it looks like a list
		playList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Set selection to single
		playList.setModel(dlm);
		
		// We put playList in a JScrollPane
		JScrollPane listScroller = new JScrollPane(playList);
		listScroller.setBounds(55, 100, 370, 200);
		
		// Makes playList visable
		playList.setVisible(true);
		
		// We add listScroller to our frame
		frmSpooderfi.getContentPane().add(listScroller);
		
		// Creates the volume slider and gives its function and adds it to the frame
		slider = new JSlider();
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setBounds(0, 170, 43, 191);
		frmSpooderfi.getContentPane().add(slider);
		slider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e)
			{
				volume(slider.getValue());
			}
		});
		
	}
}
