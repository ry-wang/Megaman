package main.java;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.EventQueue;
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
import javax.swing.SwingConstants;
import javax.swing.JButton;

/**
 * @ Description: Credits class, displays the credits of the program
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class Credits extends JFrame implements ActionListener {

	private final AudioClip audioClip = Applet.newAudioClip(this.getClass().getResource("/main/resources/music/creditsAudio.wav"));

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Credits frame = new Credits();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Credits() {
		Font creditsFont = new Font("SWTOR Trajan", Font.ITALIC, 18);
		JPanel contentPane;

		setTitle("Credits");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCredits = new JLabel("Credits");
		lblCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits.setForeground(Color.WHITE);
		lblCredits.setFont(new Font("SWTOR Trajan", Font.ITALIC, 40));
		lblCredits.setBounds(107, 23, 252, 50);
		contentPane.add(lblCredits);

		JLabel lblCredits1 = new JLabel("Design By: Ryan Wang");
		lblCredits1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits1.setForeground(Color.BLUE);
		lblCredits1.setFont(creditsFont);
		lblCredits1.setBounds(116, 95, 261, 28);
		contentPane.add(lblCredits1);

		JLabel lblCredits2 = new JLabel("Programmed By: Ryan Wang");
		lblCredits2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits2.setForeground(Color.BLUE);
		lblCredits2.setFont(creditsFont);
		lblCredits2.setBounds(70, 134, 341, 28);
		contentPane.add(lblCredits2);

		JLabel lblCredits3 = new JLabel("Artwork From: MegaMan X");
		lblCredits3.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits3.setForeground(Color.BLUE);
		lblCredits3.setFont(creditsFont);
		lblCredits3.setBounds(70, 175, 335, 28);
		contentPane.add(lblCredits3);

		JLabel lblCredits4 = new JLabel("Music From: MegaMan Collection");
		lblCredits4.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits4.setForeground(Color.BLUE);
		lblCredits4.setFont(creditsFont);
		lblCredits4.setBounds(43, 214, 400, 28);
		contentPane.add(lblCredits4);

		JLabel lblThanks = new JLabel("Thank You For Playing!");
		lblThanks.setHorizontalAlignment(SwingConstants.CENTER);
		lblThanks.setForeground(Color.BLUE);
		lblThanks.setFont(new Font("SWTOR Trajan", Font.BOLD | Font.ITALIC, 22));
		lblThanks.setBounds(43, 276, 400, 28);
		contentPane.add(lblThanks);

		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(Color.BLUE);
		btnMenu.setFont(creditsFont);
		btnMenu.setBounds(84, 331, 134, 41);
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("Menu");
		contentPane.add(btnMenu);

		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.BLUE);
		btnExit.setFont(creditsFont);
		btnExit.setBounds(263, 331, 134, 41);
		btnExit.addActionListener(this);
		btnExit.setActionCommand("Exit");
		contentPane.add(btnExit);

		try {
			audioClip.loop();
		}
		catch (Exception e) {
			System.out.println("Error loading main.resources.music");
		}
	}

	public void actionPerformed(ActionEvent evt) {

		if (evt.getActionCommand().equals("Menu")) {
			audioClip.stop();
			this.dispose();
			Control.menuFrame = new Menu();
			Control.menuFrame.setVisible(true);
		}
		if (evt.getActionCommand(). equals ("Exit")) {
			int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
	}

}
