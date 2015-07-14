//Imports needed
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.SwingConstants;

/**
 * @ Description: Credits class, displays the credits of the program
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */

public class instructions extends JFrame implements ActionListener {

	//Creation of variables
	private JPanel contentPane;
	private Clip audioClip;

	//Opens the frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					instructions frame = new instructions();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor for the frame
	public instructions() {
		//Sets title and size of frame, adds it to content pane
		setTitle("Instructions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 600, 620);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Creation of all JLabels that show instructions to user
		JLabel lblTitle = new JLabel("Instructions");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("SWTOR Trajan", Font.BOLD | Font.ITALIC, 35));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(118, 27, 325, 56);
		contentPane.add(lblTitle);

		JLabel lbl1 = new JLabel("Use arrow keys to move left and right");
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1.setForeground(Color.BLUE);
		lbl1.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lbl1.setBounds(52, 106, 479, 28);
		contentPane.add(lbl1);

		JLabel lbl2 = new JLabel("Use up arrow key to stop");
		lbl2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl2.setForeground(Color.BLUE);
		lbl2.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lbl2.setBounds(52, 159, 479, 28);
		contentPane.add(lbl2);

		JLabel lbl3 = new JLabel("Press Space to jump");
		lbl3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl3.setForeground(Color.BLUE);
		lbl3.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lbl3.setBounds(52, 216, 479, 28);
		contentPane.add(lbl3);

		JLabel lbl4 = new JLabel("Press f to shoot");
		lbl4.setHorizontalAlignment(SwingConstants.CENTER);
		lbl4.setForeground(Color.BLUE);
		lbl4.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lbl4.setBounds(52, 273, 479, 28);
		contentPane.add(lbl4);

		JLabel lbl5 = new JLabel("Total score = Points - Health Lost - Time");
		lbl5.setHorizontalAlignment(SwingConstants.CENTER);
		lbl5.setForeground(Color.BLUE);
		lbl5.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lbl5.setBounds(52, 426, 479, 28);
		contentPane.add(lbl5);

		//Creation of buttons and their respective actionListeners
		JButton btnPlay = new JButton("Play");
		btnPlay.setForeground(Color.BLUE);
		btnPlay.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnPlay.setBounds(355, 482, 124, 56);
		btnPlay.addActionListener(this);
		btnPlay.setActionCommand("Play");
		contentPane.add(btnPlay);

		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(Color.BLUE);
		btnMenu.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnMenu.setBounds(107, 482, 131, 56);
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("Menu");
		contentPane.add(btnMenu);
		
		//More labels
		JLabel lbl6 = new JLabel("You can jump onto platforms from below");
		lbl6.setHorizontalAlignment(SwingConstants.CENTER);
		lbl6.setForeground(Color.BLUE);
		lbl6.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lbl6.setBounds(10, 375, 564, 28);
		contentPane.add(lbl6);
		
		JLabel lbl7 = new JLabel("Press ESC to pause");
		lbl7.setHorizontalAlignment(SwingConstants.CENTER);
		lbl7.setForeground(Color.BLUE);
		lbl7.setFont(new Font("SWTOR Trajan", Font.ITALIC, 18));
		lbl7.setBounds(10, 327, 564, 28);
		contentPane.add(lbl7);

		//Plays music for this frame in a loop
		try {
			URL url = this.getClass().getResource("music/instructionsAudio.wav");
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

		//Different frames are opened depending on which action command
		if (evt.getActionCommand() .equals ("Play")) {
			//Asks user for name, then stops music and loading screen is shown
			menu.name = JOptionPane.showInputDialog("Please enter your name: ");
			audioClip.stop();
			this.dispose();
			control.loadFrame = new loadingScreen();
			control.loadFrame.setVisible(true);
		}
		//Stops music, returns to menu
		if (evt.getActionCommand() .equals ("Menu")) {
			audioClip.stop();
			this.dispose();
			control.menuFrame = new menu();
			control.menuFrame.setVisible(true);
		}
	}//End actionPerformed method
}//End instructions class
