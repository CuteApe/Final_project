import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class GUI extends JFrame
{
	private JButton pAP; // Play and Pause
	public GUI()
	{
		super("Spötiphy");
		setLayout(new FlowLayout());
		pAP = new JButton("Play");
		add(pAP);
			
		actionHandler handler = new actionHandler();
		pAP.addActionListener(handler);
		
	}
	
	public static void main (String[] args)
	{
			GUI testGUI = new GUI();
			testGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			testGUI.setSize(500, 500);
			testGUI.setVisible(true);
	}
	
	private class actionHandler implements ActionListener
	{
		
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == pAP)
			{
				if(event.getActionCommand().equals("Play"))
				{
					pAP.setText("Pause");
					System.out.println("Playing");

				}
				else
				{
					pAP.setText("Play");
					System.out.println("Paused");
				}
			}
		}
	}
	
}
