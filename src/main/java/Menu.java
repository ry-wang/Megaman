package main.java;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ Description: Menu class, creates the main menu of the game
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class Menu extends JFrame implements ActionListener {

	static String name;
    private final AudioClip audioClip = Applet.newAudioClip(this.getClass().getResource("/main/resources/music/menuAudio.wav"));

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Menu() {
		final Font menuFont = new Font("SWTOR Trajan", Font.PLAIN, 18);
		final JPanel contentPane;

		setTitle("Megaman X");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 550);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("");
		lblTitle.setIcon(new ImageIcon(Menu.class.getResource("/main/resources/menuImages/menu (Custom).png")));
		lblTitle.setBounds(28, 13, 516, 227);
		contentPane.add(lblTitle);

		JButton btnPlay = new JButton("Play");
		btnPlay.setBackground(SystemColor.menu);
		btnPlay.setForeground(Color.BLUE);
		btnPlay.setFont(menuFont);
		btnPlay.setBounds(168, 261, 245, 34);
		btnPlay.addActionListener(this);
		btnPlay.setActionCommand("Play");
		contentPane.add(btnPlay);

		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.setForeground(Color.BLUE);
		btnInstructions.setFont(menuFont);
		btnInstructions.setBackground(SystemColor.menu);
		btnInstructions.setBounds(168, 308, 245, 34);
		btnInstructions.addActionListener(this);
		btnInstructions.setActionCommand("Instructions");
		contentPane.add(btnInstructions);

		JButton btnCredits = new JButton("Credits");
		btnCredits.setForeground(Color.BLUE);
		btnCredits.setFont(menuFont);
		btnCredits.setBackground(SystemColor.menu);
		btnCredits.addActionListener(this);
		btnCredits.setActionCommand("Credits");
		btnCredits.setBounds(168, 398, 245, 34);
		contentPane.add(btnCredits);

		JButton btnScores = new JButton("Scores");
		btnScores.setForeground(Color.BLUE);
		btnScores.setFont(menuFont);
		btnScores.setBackground(SystemColor.menu);
		btnScores.setBounds(168, 353, 245, 34);
		btnScores.addActionListener(this);
		btnScores.setActionCommand("Scores");
		contentPane.add(btnScores);

		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.BLUE);
		btnExit.setFont(menuFont);
		btnExit.setBackground(SystemColor.menu);
		btnExit.setBounds(168, 443, 245, 34);
		btnExit.addActionListener(this);
		btnExit.setActionCommand("Exit");
		contentPane.add(btnExit);

		try {
			audioClip.loop();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Instructions")) {
			audioClip.stop();
			this.dispose();
			Control.instFrame = new Instructions();
			Control.instFrame.setVisible(true);
		}
		else if (evt.getActionCommand().equals("Credits")) {
			audioClip.stop();
			this.dispose();
			Control.creditFrame = new Credits();
			Control.creditFrame.setVisible(true);
		}
		else if (evt.getActionCommand().equals("Play")) {
			int play = JOptionPane.showConfirmDialog(null, "Have you read the instructions?");
			if (play == 0) {
				name = JOptionPane.showInputDialog("Please enter your name: ");
				if (name != null) {
					audioClip.stop();
					this.dispose();
					Control.loadFrame = new LoadingScreen();
					Control.loadFrame.setVisible(true);
				}
			}
			else if (play == 1) {
				audioClip.stop();
				this.dispose();
				Control.instFrame = new Instructions();
				Control.instFrame.setVisible(true);
			}
		}
		else if (evt.getActionCommand().equals("Exit")) {
			int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
		else if (evt.getActionCommand().equals("Scores")) {
			audioClip.stop();
			this.dispose();
			Control.scoreFrame = new Scores();
			Control.scoreFrame.setVisible(true);
		}
	}
}
