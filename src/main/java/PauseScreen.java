package main.java;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.JButton;

/**
 * @ Description: pauseScreen class, displays when user pauses game
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class PauseScreen extends JFrame implements ActionListener {

	private JPanel contentPane;
	private int menu;
	private int exit;
	Runnable tempApplet;

	public PauseScreen(Runnable input) {
		GameFrame.audioClip.stop();
		tempApplet = input;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(325, 230, 450, 340);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPause = new JLabel("Game paused");
		lblPause.setBounds(90, 28, 242, 31);
		lblPause.setHorizontalAlignment(SwingConstants.CENTER);
		lblPause.setForeground(Color.WHITE);
		lblPause.setFont(new Font("SWTOR Trajan", Font.ITALIC, 30));
		contentPane.add(lblPause);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(Color.BLUE);
		btnMenu.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("Menu");
		btnMenu.setBounds(103, 151, 229, 36);
		contentPane.add(btnMenu);
		
		JButton btnExit = new JButton("Exit Game");
		btnExit.setForeground(Color.BLUE);
		btnExit.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnExit.setActionCommand("Exit");
		btnExit.addActionListener(this);
		btnExit.setBounds(103, 207, 229, 36);
		contentPane.add(btnExit);
		
		JButton btnResume = new JButton("Resume Game");
		btnResume.setForeground(Color.BLUE);
		btnResume.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnResume.setActionCommand("Resume");
		btnResume.addActionListener(this);
		btnResume.setBounds(103, 95, 229, 36);
		contentPane.add(btnResume);
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals ("Menu")) {
			menu = JOptionPane.showConfirmDialog(null, "Are you sure you want to return to menu? Your scores will not be saved.");
			if (menu == 0) {
				GameFrame.audioClip.stop();
				this.dispose();
				Control.menuFrame = new Menu();
				Control.menuFrame.setVisible(true);
				Control.gameJFrame.dispose();
			}
		}
		if (evt.getActionCommand().equals ("Exit")) {
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit? Your scores will not be saved.");
			if (exit == 0) {
				System.exit(0);
			}
		}
		if (evt.getActionCommand().equals("Resume")) {
			GameFrame.audioClip.loop();
			Game.th = new Thread(tempApplet);
			Game.th.start();
			this.dispose();
		}
	}
}
