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
//Imports needed
import javax.swing.JButton;

/**
 * @ Description: pauseScreen class, displays when user pauses game
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */

public class pauseScreen extends JFrame implements ActionListener {

	//Variables needed
	private JPanel contentPane;
	private int menu;
	private int exit;
	Runnable tempApplet;

	//Constructor for pauseScreen
	public pauseScreen(Runnable input) {
		//Pauses music
		gameFrame.audioClip.stop();
		//Gets current instance of applet that was running
		tempApplet = input;
		
		//Creates frame, sets size and title and adds them to the content pane
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(325, 230, 450, 340);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label for this frame
		JLabel lblPause = new JLabel("Game paused");
		lblPause.setBounds(90, 28, 242, 31);
		lblPause.setHorizontalAlignment(SwingConstants.CENTER);
		lblPause.setForeground(Color.WHITE);
		lblPause.setFont(new Font("SWTOR Trajan", Font.ITALIC, 30));
		contentPane.add(lblPause);
		
		//Creation of buttons and their respective actionListeners
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
	
	//This is run when user presses button
	public void actionPerformed(ActionEvent evt) {
		//Calls the different frames based on whichever action is called
		if (evt.getActionCommand() .equals ("Menu")) {
			menu = JOptionPane.showConfirmDialog(null, "Are you sure you want to return to menu? Your scores will not be saved.");
			if (menu == 0) {
				gameFrame.audioClip.stop();
				this.dispose();
				control.menuFrame = new menu();
				control.menuFrame.setVisible(true);
				control.gameJFrame.dispose();
			}
		}
		if (evt.getActionCommand() .equals ("Exit")) {
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit? Your scores will not be saved.");
			if (exit == 0) {
				System.exit(0);
			}
		}
		if (evt.getActionCommand() .equals("Resume")) {
			gameFrame.audioClip.start();
			game.th = new Thread(tempApplet);
			game.th.start();
			this.dispose();
		}
	}//End actionPerformed method
}//End of pauseScreen class
