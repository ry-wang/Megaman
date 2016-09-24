import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * @ Description: CompleteScreen class, shows after user completes game
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */


public class CompleteScreen extends JFrame implements ActionListener {

	private JPanel contentPane;
	private int time;
	private int points;
	private int healthLost;
	private int score;
	private int exit;
	private final AudioClip audioClip = Applet.newAudioClip(this.getClass().getResource("music/completeScreenMusic.wav"));

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompleteScreen frame = new CompleteScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CompleteScreen() {
		setBackground(Color.BLACK);
		setTitle("Game Complete!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		time = Game.time;
		points = Game.points;
		healthLost = (100 - Game.health);
		score = points - healthLost - time;
		if (score < 0) {
			score = 0;
		}

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

		try {
			audioClip.loop();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		appendToText();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Menu")) {
			audioClip.stop();
			this.dispose();
			Control.menuFrame = new Menu();
			Control.menuFrame.setVisible(true);
		}
		if (evt.getActionCommand(). equals ("Exit")) {
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
	}

	public void appendToText() {
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter("scores.txt", true));
			bw.write(Menu.name + "\t" + points + "\t" + healthLost + "\t" + time + "\t" + score);
			bw.newLine();
			bw.flush();
		}
		catch(IOException e){
		}
		finally{
			if(bw != null){
				try{
					bw.close();
				}
				catch(IOException e2) {
				}
			}
		}
	}
}
