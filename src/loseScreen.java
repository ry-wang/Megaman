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

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.SwingConstants;
import javax.swing.JButton;

/**
 * @ Description: loseScreen class, displays when user loses in the game
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */

public class loseScreen extends JFrame implements ActionListener {

	private JPanel contentPane;
	private Clip audioClip;
	private int exit;

	//Open frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loseScreen frame = new loseScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor for frame
	public loseScreen() {
		//Creates title, sets size of frame, adds it to content pane
		setBackground(Color.BLACK);
		setTitle("Game Over!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 330);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Game over label
		JLabel lblGameOver = new JLabel("Game Over");
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameOver.setForeground(Color.WHITE);
		lblGameOver.setFont(new Font("SWTOR Trajan", Font.ITALIC, 30));
		lblGameOver.setBounds(91, 29, 242, 31);
		contentPane.add(lblGameOver);

		//Creation of all buttons and respective action listeners
		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(Color.BLUE);
		btnMenu.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnMenu.setBounds(142, 149, 155, 36);
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("Menu");
		contentPane.add(btnMenu);

		JButton btnRestart = new JButton("Restart");
		btnRestart.setForeground(Color.BLUE);
		btnRestart.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnRestart.setBounds(142, 87, 155, 36);
		btnRestart.addActionListener(this);
		btnRestart.setActionCommand("Restart");
		contentPane.add(btnRestart);

		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.BLUE);
		btnExit.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnExit.setBounds(142, 211, 155, 36);
		btnExit.addActionListener(this);
		btnExit.setActionCommand("Exit");
		contentPane.add(btnExit);

		//Plays music for this frame
		try {
			URL url = this.getClass().getResource("music/loseScreenAudio.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			audioClip = AudioSystem.getClip();
			audioClip.open(audioIn);
			audioClip.start();
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
		//Opens different frames depending on which action command, also stops the audio that's currently playing, hides this frame
		if (evt.getActionCommand().equals("Menu")) {
			audioClip.stop();
			this.dispose();
			control.menuFrame  = new menu();
			control.menuFrame.setVisible(true);
		}
		if (evt.getActionCommand().equals("Exit")) {
			//Exits the game after user confirmation
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
		//Stops music, closes this frame and opens game frame again where the applet runs
		if (evt.getActionCommand().equals("Restart")) {
			audioClip.stop();
			this.dispose();
			control.gameJFrame  = new gameFrame();
			control.gameJFrame.setVisible(true);
		}

	}//End actionPerformed method
	
}//End of loseScreen class
