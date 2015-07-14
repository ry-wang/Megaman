//Imports needed
import java.awt.EventQueue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

/**
 * @ Description: Menu class, creates the main menu of the game
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 12th, 2015
 */

public class menu extends JFrame implements ActionListener {

	//Creation of all variables
	private JPanel contentPane;
	private int exit;
	private int play;
	static String name;
	private Clip audioClip;

	//Opens the frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menu frame = new menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor for the frame
	public menu() {
		//Sets title and size of frame, adds it to the contentpane
		setTitle("Megaman X");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 550);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Sets the image logo of megaMan onto the frame
		JLabel lblTitle = new JLabel("");
		lblTitle.setIcon(new ImageIcon(menu.class.getResource("/menuImages/menu (Custom).png")));
		lblTitle.setBounds(28, 13, 516, 227);
		contentPane.add(lblTitle);

		//Creation of all buttons and their respective actionListeners
		JButton btnPlay = new JButton("Play");
		btnPlay.setBackground(SystemColor.menu);
		btnPlay.setForeground(Color.BLUE);
		btnPlay.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnPlay.setBounds(168, 261, 245, 34);
		btnPlay.addActionListener(this);
		btnPlay.setActionCommand("Play");
		contentPane.add(btnPlay);

		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.setForeground(Color.BLUE);
		btnInstructions.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnInstructions.setBackground(SystemColor.menu);
		btnInstructions.setBounds(168, 308, 245, 34);
		btnInstructions.addActionListener(this);
		btnInstructions.setActionCommand("Instructions");
		contentPane.add(btnInstructions);

		JButton btnCredits = new JButton("Credits");
		btnCredits.setForeground(Color.BLUE);
		btnCredits.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnCredits.setBackground(SystemColor.menu);
		btnCredits.addActionListener(this);
		btnCredits.setActionCommand("Credits");
		btnCredits.setBounds(168, 398, 245, 34);
		contentPane.add(btnCredits);

		JButton btnScores = new JButton("Scores");
		btnScores.setForeground(Color.BLUE);
		btnScores.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnScores.setBackground(SystemColor.menu);
		btnScores.setBounds(168, 353, 245, 34);
		btnScores.addActionListener(this);
		btnScores.setActionCommand("Scores");
		contentPane.add(btnScores);

		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.BLUE);
		btnExit.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnExit.setBackground(SystemColor.menu);
		btnExit.setBounds(168, 443, 245, 34);
		btnExit.addActionListener(this);
		btnExit.setActionCommand("Exit");
		contentPane.add(btnExit);

		//Starts the music, keeps it looping continuously
		try {
			URL url = this.getClass().getResource("music/menuAudio.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			audioClip = AudioSystem.getClip();
			audioClip.open(audioIn);
			audioClip.start();
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (IOException e) {	
		}
		catch (UnsupportedAudioFileException e) {	
		}
		catch (LineUnavailableException e) {	
		}
	}//End of constructor

	//Method that is run when a button is pressed
	public void actionPerformed(ActionEvent evt) {

		//Opens different frames depending on which action command, also stops the audio that's currently playing, hides this frame
		if (evt.getActionCommand(). equals ("Instructions")) {
			audioClip.stop();
			this.dispose();
			control.instFrame  = new instructions();
			control.instFrame.setVisible(true);
		}
		if (evt.getActionCommand(). equals ("Credits")) {
			audioClip.stop();
			this.dispose();
			control.creditFrame = new credits();
			control.creditFrame.setVisible(true);
		}
		if (evt.getActionCommand(). equals ("Play")) {
			//Makes sure players read instructions before starting game; if they haven't read instructions, they get sent there
			play = JOptionPane.showConfirmDialog(null, "Have you read the instructions?");
			if (play == 0) {
				name = JOptionPane.showInputDialog("Please enter your name: ");
				//Code will only run if a name has been inputed
				if (name != null) {
					audioClip.stop();
					this.dispose();
					control.loadFrame = new loadingScreen();
					control.loadFrame.setVisible(true);
				}
			}
			if (play == 1) {
				audioClip.stop();
				this.dispose();
				control.instFrame  = new instructions();
				control.instFrame.setVisible(true);
			}
		}
		if (evt.getActionCommand(). equals ("Exit")) {
			//Exits the game after user confirmation
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
		//Opens the scores frame
		if (evt.getActionCommand(). equals ("Scores")) {
			audioClip.stop();
			this.dispose();
			control.scoreFrame  = new scores();
			control.scoreFrame.setVisible(true);
		}
	}//End of actionPerformed method
}//End of menu class
