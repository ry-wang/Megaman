//Imports needed
import java.awt.BorderLayout;
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

import java.awt.SystemColor;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.SwingConstants;
import javax.swing.JButton;

/**
 * @ Description: Credits class, displays the credits of the program
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */

public class credits extends JFrame implements ActionListener {

	private JPanel contentPane;
	private int exit;
	private Clip audioClip;

	//Opens the frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					credits frame = new credits();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor for class
	public credits() {
		//Sets size and title of JFrame, adds it to content pane
		setTitle("Credits");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.desktop);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Creation of all labels shown in the frame
		JLabel lblCredits = new JLabel("Credits");
		lblCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits.setForeground(Color.WHITE);
		lblCredits.setFont(new Font("SWTOR Trajan", Font.ITALIC, 40));
		lblCredits.setBounds(107, 23, 252, 50);
		contentPane.add(lblCredits);

		JLabel lblCredits1 = new JLabel("Design By: Ryan Wang");
		lblCredits1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits1.setForeground(Color.BLUE);
		lblCredits1.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lblCredits1.setBounds(116, 95, 261, 28);
		contentPane.add(lblCredits1);

		JLabel lblCredits2 = new JLabel("Programmed By: Ryan Wang");
		lblCredits2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits2.setForeground(Color.BLUE);
		lblCredits2.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lblCredits2.setBounds(70, 134, 341, 28);
		contentPane.add(lblCredits2);

		JLabel lblCredits3 = new JLabel("Artwork From: MegaMan X");
		lblCredits3.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits3.setForeground(Color.BLUE);
		lblCredits3.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lblCredits3.setBounds(70, 175, 335, 28);
		contentPane.add(lblCredits3);

		JLabel lblCredits4 = new JLabel("Music From: MegaMan Collection");
		lblCredits4.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits4.setForeground(Color.BLUE);
		lblCredits4.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lblCredits4.setBounds(43, 214, 400, 28);
		contentPane.add(lblCredits4);

		JLabel lblThanks = new JLabel("Thank You For Playing!");
		lblThanks.setHorizontalAlignment(SwingConstants.CENTER);
		lblThanks.setForeground(Color.BLUE);
		lblThanks.setFont(new Font("SWTOR Trajan", Font.BOLD | Font.ITALIC, 22));
		lblThanks.setBounds(43, 276, 400, 28);
		contentPane.add(lblThanks);

		//Creation of buttons and their respective actionListeners
		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(Color.BLUE);
		btnMenu.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnMenu.setBounds(84, 331, 134, 41);
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("Menu");
		contentPane.add(btnMenu);

		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.BLUE);
		btnExit.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnExit.setBounds(263, 331, 134, 41);
		btnExit.addActionListener(this);
		btnExit.setActionCommand("Exit");
		contentPane.add(btnExit);

		//Plays the music in a continous loop
		try {
			URL url = this.getClass().getResource("music/creditsAudio.wav");
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
	}//End constructor

	//Method that is run when button is pressed
	public void actionPerformed(ActionEvent evt) {

		//Opens/closes the frame based on whichever command is called
		//Returns to menu, stops music
		if (evt.getActionCommand().equals("Menu")) {
			audioClip.stop();
			this.dispose();
			control.menuFrame = new menu();
			control.menuFrame.setVisible(true);
		}
		//Exits program after user confirmation
		if (evt.getActionCommand(). equals ("Exit")) {
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
	}//End actionPerformed method

}//End credits class