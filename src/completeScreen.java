//Imports needed
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.SwingConstants;
import javax.swing.JButton;

/**
 * @ Description: completeScreen class, shows after user completes game
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */


public class completeScreen extends JFrame implements ActionListener {

	//Variables needed
	private JPanel contentPane;
	private int time;
	private int points;
	private int healthLost;
	private int score;
	private int exit;
	private Clip audioClip;

	//Opens the frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					completeScreen frame = new completeScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor for frame
	public completeScreen() {
		//Creates title, sets size of the frame
		setBackground(Color.BLACK);
		setTitle("Game Complete!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Gets the time, points, healthLost from the applet class
		time = game.time;
		points = game.points;
		healthLost = (100 - game.health);
		//Calculates score
		score = points - healthLost - time;
		//Lowest score possible is 0, won't give a negative score
		if (score < 0) {
			score = 0;
		}

		//Creates all the labels in the frame
		JLabel lblTitle = new JLabel("Game Complete");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("SWTOR Trajan", Font.ITALIC, 30));
		lblTitle.setBounds(75, 22, 318, 57);
		contentPane.add(lblTitle);

		JLabel lblPoints = new JLabel("Points:");
		lblPoints.setForeground(Color.BLUE);
		lblPoints.setFont(new Font("SWTOR Trajan", Font.ITALIC, 16));
		lblPoints.setBounds(83, 90, 90, 31);
		contentPane.add(lblPoints);

		JLabel lblHealth = new JLabel("Health Lost:");
		lblHealth.setForeground(Color.BLUE);
		lblHealth.setFont(new Font("SWTOR Trajan", Font.ITALIC, 16));
		lblHealth.setBounds(80, 132, 153, 31);
		contentPane.add(lblHealth);

		JLabel lblTime = new JLabel("Time Taken:");
		lblTime.setForeground(Color.BLUE);
		lblTime.setFont(new Font("SWTOR Trajan", Font.ITALIC, 16));
		lblTime.setBounds(77, 174, 153, 31);
		contentPane.add(lblTime);

		JLabel lblScore = new JLabel("Final Score:");
		lblScore.setForeground(Color.BLUE);
		lblScore.setFont(new Font("SWTOR Trajan", Font.ITALIC, 16));
		lblScore.setBounds(75, 216, 140, 31);
		contentPane.add(lblScore);

		//Buttons in the frame, also adds action commands to them
		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(Color.BLUE);
		btnMenu.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("Menu");
		btnMenu.setBounds(95, 270, 112, 56);
		contentPane.add(btnMenu);

		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.BLUE);
		btnExit.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnExit.addActionListener(this);
		btnExit.setActionCommand("Exit");
		btnExit.setBounds(250, 270, 124, 56);
		contentPane.add(btnExit);

		//Creates the label for all of the info on the game data
		JLabel lblNum1 = new JLabel(String.valueOf(points));
		lblNum1.setForeground(Color.BLUE);
		lblNum1.setFont(new Font("SWTOR Trajan", Font.ITALIC, 16));
		lblNum1.setBounds(354, 90, 90, 31);
		contentPane.add(lblNum1);

		JLabel lblNum2 = new JLabel(String.valueOf(healthLost));
		lblNum2.setForeground(Color.BLUE);
		lblNum2.setFont(new Font("SWTOR Trajan", Font.ITALIC, 16));
		lblNum2.setBounds(354, 132, 90, 31);
		contentPane.add(lblNum2);

		JLabel lblNum3 = new JLabel(String.valueOf(time) + "s");
		lblNum3.setForeground(Color.BLUE);
		lblNum3.setFont(new Font("SWTOR Trajan", Font.ITALIC, 16));
		lblNum3.setBounds(354, 174, 90, 31);
		contentPane.add(lblNum3);

		JLabel lblNum4 = new JLabel(String.valueOf(score));
		lblNum4.setForeground(Color.BLUE);
		lblNum4.setFont(new Font("SWTOR Trajan", Font.ITALIC, 16));
		lblNum4.setBounds(354, 216, 90, 31);
		contentPane.add(lblNum4);

		//Plays music, tells it to loop continuously
		try {
			URL url = this.getClass().getResource("music/completeScreenMusic.wav");
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

		//Calls appendToText method
		appendToText();
	}

	//Method that is run when a button is pressed
	public void actionPerformed(ActionEvent evt) {
		//Opens/closes the frame based on whichever command is called
		if (evt.getActionCommand().equals("Menu")) {
			audioClip.stop();
			//Closes this frame, opens menu
			this.dispose();
			control.menuFrame = new menu();
			control.menuFrame.setVisible(true);
		}
		//Exits program if user confirms
		if (evt.getActionCommand(). equals ("Exit")) {
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
	}//End actionPerformed method

	//Append to text method
	public void appendToText() {
		BufferedWriter bw = null;
		//Tries to append to the text file
		try{
			bw = new BufferedWriter(new FileWriter("scores.txt", true));
			//Appends the line properly formatted
			bw.write(menu.name + "\t" + points + "\t" + healthLost + "\t" + time + "\t" + score); 
			bw.newLine();
			bw.flush();
		}
		//Catch statement for exceptions
		catch(IOException e){
		}
		finally{
			if(bw !=null){
				//Tries closing the buffered writer after append is finished
				try{
					bw.close();
				}
				catch(IOException e2){
				}
			}
		}
	}//End of append to text method

}//End of completeScreen class
