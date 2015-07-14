//Imports needed
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @ Description: gameFrame class, empty JFrame that holds applet
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */

//Frame for the applet
public class gameFrame extends JFrame {

	//Variables needed
	private JPanel contentPane;
	static Clip audioClip;

	//Open frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gameFrame frame = new gameFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor for the frame
	public gameFrame() {
		//Creates title, sets size of the frame
		setTitle("Megaman X");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//Plays the music, tells it to loop continuously
		try {
			URL url = this.getClass().getResource("music/gameAudio.wav");
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
		
		//Adds the game applet to this JFrame, init and starts it
		game app = new game();
		add(app);
		app.init();
		app.start();
	}//End constructor for gameFrame
}//End of gameFrame class


