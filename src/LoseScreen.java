import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.JButton;

/**
 * @ Description: LoseScreen class, displays when user loses in the game
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class LoseScreen extends JFrame implements ActionListener {

	final AudioClip audioClip = Applet.newAudioClip(this.getClass().getResource("music/loseScreenAudio.wav"));

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoseScreen frame = new LoseScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoseScreen() {
		JPanel contentPane;

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
			audioClip.loop();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Menu")) {
			audioClip.stop();
			this.dispose();
			Control.menuFrame = new Menu();
			Control.menuFrame.setVisible(true);
		}
		if (evt.getActionCommand().equals("Exit")) {
			int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
		if (evt.getActionCommand().equals("Restart")) {
			audioClip.stop();
			this.dispose();
			Control.gameJFrame = new GameFrame();
			Control.gameJFrame.setVisible(true);
		}

	}
}
