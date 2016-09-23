import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.applet.Applet;
import java.applet.AudioClip;
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
 * @ Version: v2.0
 * September 2016
 */

public class Instructions extends JFrame implements ActionListener {

	private JPanel contentPane;
	private AudioClip audioClip;

    private Font instructionsFont = new Font("SWTOR Trajan", Font.ITALIC, 18);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instructions frame = new Instructions();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Instructions() {
		setTitle("Instructions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 600, 620);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Instructions");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("SWTOR Trajan", Font.BOLD | Font.ITALIC, 35));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(118, 27, 325, 56);
		contentPane.add(lblTitle);

		JLabel lbl1 = new JLabel("Use arrow keys to move left and right");
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1.setForeground(Color.BLUE);
		lbl1.setFont(instructionsFont);
		lbl1.setBounds(52, 106, 479, 28);
		contentPane.add(lbl1);

		JLabel lbl2 = new JLabel("Use up arrow key to stop");
		lbl2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl2.setForeground(Color.BLUE);
		lbl2.setFont(instructionsFont);
		lbl2.setBounds(52, 159, 479, 28);
		contentPane.add(lbl2);

		JLabel lbl3 = new JLabel("Press Space to jump");
		lbl3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl3.setForeground(Color.BLUE);
		lbl3.setFont(instructionsFont);
		lbl3.setBounds(52, 216, 479, 28);
		contentPane.add(lbl3);

		JLabel lbl4 = new JLabel("Press f to shoot");
		lbl4.setHorizontalAlignment(SwingConstants.CENTER);
		lbl4.setForeground(Color.BLUE);
		lbl4.setFont(instructionsFont);
		lbl4.setBounds(52, 273, 479, 28);
		contentPane.add(lbl4);

		JLabel lbl5 = new JLabel("Total score = Points - Health Lost - Time");
		lbl5.setHorizontalAlignment(SwingConstants.CENTER);
		lbl5.setForeground(Color.BLUE);
		lbl5.setFont(instructionsFont);
		lbl5.setBounds(52, 426, 479, 28);
		contentPane.add(lbl5);

		JButton btnPlay = new JButton("Play");
		btnPlay.setForeground(Color.BLUE);
		btnPlay.setFont(instructionsFont);
		btnPlay.setBounds(355, 482, 124, 56);
		btnPlay.addActionListener(this);
		btnPlay.setActionCommand("Play");
		contentPane.add(btnPlay);

		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(Color.BLUE);
		btnMenu.setFont(instructionsFont);
		btnMenu.setBounds(107, 482, 131, 56);
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("Menu");
		contentPane.add(btnMenu);
		
		JLabel lbl6 = new JLabel("You can jump onto platforms from below");
		lbl6.setHorizontalAlignment(SwingConstants.CENTER);
		lbl6.setForeground(Color.BLUE);
		lbl6.setFont(instructionsFont);
		lbl6.setBounds(10, 375, 564, 28);
		contentPane.add(lbl6);
		
		JLabel lbl7 = new JLabel("Press ESC to pause");
		lbl7.setHorizontalAlignment(SwingConstants.CENTER);
		lbl7.setForeground(Color.BLUE);
		lbl7.setFont(instructionsFont);
		lbl7.setBounds(10, 327, 564, 28);
		contentPane.add(lbl7);

		try {
			URL url = this.getClass().getResource("music/instructionsAudio.wav");
			audioClip = Applet.newAudioClip(url);
			audioClip.loop();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Play")) {
			Menu.name = JOptionPane.showInputDialog("Please enter your name: ");
			audioClip.stop();
			this.dispose();
			control.loadFrame = new loadingScreen();
			control.loadFrame.setVisible(true);
		}
		if (evt.getActionCommand().equals("Menu")) {
			audioClip.stop();
			this.dispose();
			control.menuFrame = new Menu();
			control.menuFrame.setVisible(true);
		}
	}
}
