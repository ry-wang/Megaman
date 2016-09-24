import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @ Description: gameFrame class, empty JFrame that holds applet
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class GameFrame extends JFrame {

	private JPanel contentPane;
	static AudioClip audioClip;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame frame = new GameFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GameFrame() {
		setTitle("Megaman X");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		try {
			audioClip = Applet.newAudioClip(this.getClass().getResource("music/gameAudio.wav"));
			audioClip.loop();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Game app = new Game();
		add(app);
		app.init();
		app.start();
	}
}


