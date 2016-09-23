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
 * @ Version: v2.0
 * September 2016
 */

public class gameFrame extends JFrame {

	private JPanel contentPane;
	static Clip audioClip;

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

	public gameFrame() {
		setTitle("Megaman X");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
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
		
		game app = new game();
		add(app);
		app.init();
		app.start();
	}
}


