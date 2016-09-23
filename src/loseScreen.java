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
 * @ Version: v2.0
 * September 2016
 */

public class loseScreen extends JFrame implements ActionListener {

	private JPanel contentPane;
	private Clip audioClip;
	private int exit;

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

	public loseScreen() {
		setBackground(Color.BLACK);
		setTitle("Game Over!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 330);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGameOver = new JLabel("Game Over");
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameOver.setForeground(Color.WHITE);
		lblGameOver.setFont(new Font("SWTOR Trajan", Font.ITALIC, 30));
		lblGameOver.setBounds(91, 29, 242, 31);
		contentPane.add(lblGameOver);

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
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Menu")) {
			audioClip.stop();
			this.dispose();
			control.menuFrame = new Menu();
			control.menuFrame.setVisible(true);
		}
		if (evt.getActionCommand().equals("Exit")) {
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
		if (evt.getActionCommand().equals("Restart")) {
			audioClip.stop();
			this.dispose();
			control.gameJFrame = new gameFrame();
			control.gameJFrame.setVisible(true);
		}

	}
}
