import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;

/**
 * @ Description: loadingScreen class, displays when user is loading up game
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class loadingScreen extends JFrame implements ActionListener, PropertyChangeListener {
	
	private JPanel contentPane;
	private Task Task;
	private JProgressBar progressBar = new JProgressBar();
	private JButton btnStart = new JButton("Start");
	private int menu;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loadingScreen frame = new loadingScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public loadingScreen() {
		setTitle("Loading...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblLoading = new JLabel("Loading...");
		lblLoading.setForeground(Color.WHITE);
		lblLoading.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoading.setFont(new Font("SWTOR Trajan", Font.ITALIC, 30));
		lblLoading.setBounds(114, 21, 196, 60);
		contentPane.add(lblLoading);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(Color.BLUE);
		btnMenu.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnMenu.setBounds(73, 193, 112, 36);
		btnMenu.addActionListener(this);
		contentPane.setLayout(null);
		btnMenu.setActionCommand("Menu");
		contentPane.add(btnMenu);
		
		btnStart.setForeground(Color.BLUE);
		btnStart.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnStart.setEnabled(false);
		btnStart.setBounds(253, 193, 112, 36);
		btnStart.addActionListener(this);
		btnStart.setActionCommand("Start");
		contentPane.add(btnStart);
		progressBar.setForeground(SystemColor.textHighlight);

		progressBar.setValue(0);
		progressBar.setBounds(73, 108, 292, 36);
		contentPane.add(progressBar);

		Task = new Task();
		Task.addPropertyChangeListener(this);
		Task.execute();
	}

	class Task extends SwingWorker<Void, Void> {

		public Void doInBackground() {
			int progress = 0;
			Random random = new Random();
			setProgress(0);
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			while (progress < 100) {
				try {
					Thread.sleep(random.nextInt(800));
				} catch (InterruptedException e) {}

				progress += 10;
				progressBar.setValue(progress);
			}
			return null;
		}

		public void done() {
			btnStart.setEnabled(true);
			setCursor(null); //turn off the wait cursor
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
	
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand() .equals ("Menu")) {
			menu = JOptionPane.showConfirmDialog(null, "Are you sure you want to return to menu?");
			if (menu == 0) {
				this.dispose();
				control.menuFrame = new Menu();
				control.menuFrame.setVisible(true);
			}
		}
		if (evt.getActionCommand() .equals("Start")) {
			this.dispose();
			control.gameJFrame = new gameFrame();
			control.gameJFrame.setVisible(true);
		}
	}
}
