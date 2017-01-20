package main.java;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.lang.Object;

/**
 * @ Description: Score class, displays the scoreboard and also allows user to search and sort
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */

public class Scores extends JFrame implements ActionListener {

	private JPanel contentPane;
	private final AudioClip audioClip = Applet.newAudioClip(this.getClass().getResource("/main/resources/music/scoresAudio.wav"));
	private JComboBox cbxSortType = new JComboBox();
	private JComboBox cbxSearchType = new JComboBox();

	private JButton btnSearch = new JButton("Search");
	private JButton btnSort = new JButton("Sort");
	private JButton btnViewOriginal;
	private DefaultTableModel model = new DefaultTableModel();

	private String searchInfo;
	private int timesFound = 0;
	private int entryNum = 1;

	private String [] tableHeaders = new String[5];
	private String [][] scoreTable = new String [entryNum][5];

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Scores frame = new Scores();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Scores() {
		setTitle("Scores");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 550);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnPlay = new JButton("Play");
		btnPlay.setForeground(Color.BLUE);
		btnPlay.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnPlay.setBounds(233, 421, 124, 56);
		btnPlay.addActionListener(this);
		contentPane.setLayout(null);
		btnPlay.setActionCommand("Play");
		contentPane.add(btnPlay);

		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(Color.BLUE);
		btnMenu.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnMenu.setBounds(81, 421, 112, 56);
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("Menu");
		contentPane.add(btnMenu);

		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.BLUE);
		btnExit.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnExit.addActionListener(this);
		btnExit.setActionCommand("Exit");
		btnExit.setBounds(394, 421, 124, 56);
		contentPane.add(btnExit);

		JLabel lblScoreBoard = new JLabel("Scoreboard");
		lblScoreBoard.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoreBoard.setForeground(Color.WHITE);
		lblScoreBoard.setFont(new Font("SWTOR Trajan", Font.BOLD | Font.ITALIC, 35));
		lblScoreBoard.setBounds(10, 29, 589, 56);
		contentPane.add(lblScoreBoard);

		cbxSortType.setForeground(Color.BLUE);
		cbxSortType.setFont(new Font("SWTOR Trajan", Font.PLAIN, 13));
		cbxSortType.setModel(new DefaultComboBoxModel(new String[] {"Most Points", "Least Health Lost", "Fastest Time", "Total Score"}));
		cbxSortType.setBounds(173, 321, 211, 29);
		cbxSortType.setSelectedIndex(-1);
		contentPane.add(cbxSortType);

		btnSort.setForeground(Color.BLUE);
		btnSort.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnSort.setBounds(414, 321, 141, 29);
		btnSort.addActionListener(this);
		btnSort.setActionCommand("Sort");
		contentPane.add(btnSort);

		btnSearch.setForeground(Color.BLUE);
		btnSearch.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnSearch.setBounds(414, 361, 141, 29);
		btnSearch.addActionListener(this);
		btnSearch.setActionCommand("Search");
		contentPane.add(btnSearch);

		cbxSearchType.setForeground(Color.BLUE);
		cbxSearchType.setModel(new DefaultComboBoxModel(new String[] {"Name", "Points", "Health Lost", "Time", "Total Score"}));
		cbxSearchType.setFont(new Font("SWTOR Trajan", Font.PLAIN, 13));
		cbxSearchType.setBounds(173, 361, 211, 29);
		cbxSearchType.setSelectedIndex(-1);
		contentPane.add(cbxSearchType);

		JLabel lblSortBy = new JLabel("Sort By:");
		lblSortBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblSortBy.setForeground(Color.WHITE);
		lblSortBy.setFont(new Font("SWTOR Trajan", Font.PLAIN, 16));
		lblSortBy.setBounds(34, 320, 98, 32);
		contentPane.add(lblSortBy);

		JLabel lblSearchBy = new JLabel("Search For:");
		lblSearchBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchBy.setForeground(Color.WHITE);
		lblSearchBy.setFont(new Font("SWTOR Trajan", Font.PLAIN, 16));
		lblSearchBy.setBounds(39, 360, 124, 32);
		contentPane.add(lblSearchBy);

		btnViewOriginal = new JButton("View Original");
		btnViewOriginal.setForeground(Color.BLUE);
		btnViewOriginal.setEnabled(false);
		btnViewOriginal.setFont(new Font("SWTOR Trajan", Font.PLAIN, 12));
		btnViewOriginal.setVisible(false);
		btnViewOriginal.addActionListener(this);
		btnViewOriginal.setActionCommand("View Original");
		btnViewOriginal.setBounds(403, 328, 152, 56);
		contentPane.add(btnViewOriginal);

		tableHeaders();
		inputFromFile();
		setModelTable();
		displayScoreTable();

		try {
			audioClip.loop();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent evt) {

		if (evt.getActionCommand().equals("Play")) {
			Menu.name = JOptionPane.showInputDialog("Please enter your name: ");
			audioClip.stop();
			this.dispose();
			Control.loadFrame = new LoadingScreen();
			Control.loadFrame.setVisible(true);
		}

		else if (evt.getActionCommand().equals("Menu")) {
			audioClip.stop();
			this.dispose();
			Control.menuFrame = new Menu();
			Control.menuFrame.setVisible(true);
		}

		else if (evt.getActionCommand().equals("Exit")) {
			int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
		else if (evt.getActionCommand().equals("Sort")) {
			//Will only sort if user has selected something in the combobox, otherwise will tell the user to make a choice first
			if (cbxSortType.getSelectedIndex() >= 0) {
				sortScoreTable();
			}
			else {
				JOptionPane.showMessageDialog(null, "Please first select your sort type.");
			}
		}
		else if (evt.getActionCommand().equals("Search")) {
			timesFound = 0;
			//Will only search if user has selected something in the combobox, otherwise will tell the user to make a choice first
			if (cbxSearchType.getSelectedIndex() >= 0) {
				searchScoreTable();
			}
			else {
				JOptionPane.showMessageDialog(null, "Please first select your search type.");
			}
		}
		else if (evt.getActionCommand().equals("View Original")) {
			model.setRowCount(0);

			for (String info[]: scoreTable) {
				model.addRow(new Object []{info[0], info[1], info[2], info[3], info[4]});
			}

			displayScoreTable();
			btnSort.setVisible(true);
			btnSearch.setVisible(true);
			btnViewOriginal.setVisible(false);
			btnViewOriginal.setEnabled(false);
		}
	}

	private void tableHeaders(){
		tableHeaders[0]=("Name");
		tableHeaders[1]=("Points");
		tableHeaders[2]=("Health Lost");
		tableHeaders[3]=("Time (s)");
		tableHeaders[4]=("Total Score");
	}

	private void inputFromFile() {
		String tempData[];
		String inputText;
		entryNum = 0;
		scoreTable = new String[entryNum][5];
		try {
			FileReader inputFile = new FileReader(this.getClass().getClassLoader().getResource("scores.txt").getFile());
			BufferedReader bufferReader = new BufferedReader(inputFile);

			while ((inputText = bufferReader.readLine()) != null) {
				try {
					tempData = inputText.split("\t");
					resizeScoreTable();
					scoreTable[entryNum - 1][0] = tempData[0];
					scoreTable[entryNum - 1][1] = tempData[1];
					scoreTable[entryNum - 1][2] = tempData[2];
					scoreTable[entryNum - 1][3] = tempData[3];
					scoreTable[entryNum - 1][4] = tempData[4];
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			bufferReader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void displayScoreTable() {
		JTable table = new JTable(model) {
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		table.getColumnModel().getColumn(0).setPreferredWidth(175);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		table.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(50, 97, 505, 199);
		contentPane.add(scrollPane);
	}

	private void resizeScoreTable() {
		String[][] tempScoreTable = new String [entryNum][5];
		entryNum++;
		System.arraycopy(scoreTable, 0, tempScoreTable, 0, scoreTable.length);

		scoreTable = new String[entryNum][5];
		System.arraycopy(tempScoreTable, 0, scoreTable, 0, tempScoreTable.length);
	}

	private void setModelTable() {
		//Adds the headers to the model
		for (String header: tableHeaders) {
			model.addColumn(header);
		}
		//Adds the info in the scoreTable array to the model
		for (String info[]: scoreTable) {
			model.addRow(new Object []{info[0], info[1], info[2], info[3], info[4]});
		}
	}

	private void sortScoreTable() {
		//Sort for most points
		if (cbxSortType.getSelectedIndex() == 0) {
			descendingSort(1);
		}
		//Sort for least health lost
		else if (cbxSortType.getSelectedIndex() == 1) {
			ascendingSort(2);
		}
		//Sort for best time
		else if (cbxSortType.getSelectedIndex() == 2) {
			ascendingSort(3);
		}
		//Sort for best overall score
		else if (cbxSortType.getSelectedIndex() == 3) {
			descendingSort(4);
		}
		model.setRowCount(0);
		//Adds the sorted array into the table model
		for (String info[]: scoreTable) {
			model.addRow(new Object []{info[0], info[1], info[2], info[3], info[4]});
		}

		displayScoreTable();
	}

	//All sorting is done with selection sort---------------------------------------------------------------------------------------------

	private void ascendingSort(int input) {
		String [][] tempArray = new String[1][5];
		int num;
		int x;

		for (num = 0; num < (scoreTable.length - 1); num++) {
			for (x = (num + 1); x < scoreTable.length; x++) {
				//If the number on the left is less than the number on the right, the values are swapped
				if ((Integer.parseInt(scoreTable[num][input])) > (Integer.parseInt(scoreTable[x][input]))) {
					//Copies the entire row on the left to the temp array
					for (int column = 0; column < scoreTable[num].length; column++) {
						tempArray[0][column] = scoreTable[num][column];
					}
					//Copies the entire row on the right to the left
					for (int column = 0; column < scoreTable[x].length; column++) {
						scoreTable[num][column] = scoreTable[x][column];
					}
					//Copies the entire row in the temp array to the right
					for (int column = 0; column < tempArray[0].length; column++) {
						scoreTable[x][column] = tempArray[0][column];
					}
				}
			}
		}
	}

	private void descendingSort(int input) {
		String [][] tempArray = new String[1][5];
		int num;
		int x;

		for (num = 0; num < (scoreTable.length - 1); num++) {
			for (x = (num + 1); x < scoreTable.length; x++) {
				//If the number on the left is less than the number on the right, the values are swapped
				if ((Integer.parseInt(scoreTable[num][input])) < (Integer.parseInt(scoreTable[x][input]))) {
					//Copies the entire row on the left to the temp array
					for (int column = 0; column < scoreTable[num].length; column++) {
						tempArray[0][column] = scoreTable[num][column];
					}
					//Copies the entire row on the right to the left
					for (int column = 0; column < scoreTable[x].length; column++) {
						scoreTable[num][column] = scoreTable[x][column];
					}
					//Copies the entire row in the temp array to the right
					for (int column = 0; column < tempArray[0].length; column++) {
						scoreTable[x][column] = tempArray[0][column];
					}
				}
			}
		}
	}

	private void searchScoreTable() {
		//Search for name
		if (cbxSearchType.getSelectedIndex() == 0) {
			searchInfo = JOptionPane.showInputDialog("Please enter the name you would like to search for: ");
			if (searchInfo != null) {
				searchTable(0);
				checkIfFound(timesFound, "Name not found.");
			}
		}
		//Search for points
		else if (cbxSearchType.getSelectedIndex() == 1) {
			validateInput("Please enter the number of points you would like to search for: ");
			if (searchInfo != null) {
				searchTable(1);
				checkIfFound(timesFound, "Points value not found.");
			}
		}
		//Search for health lost
		else if (cbxSearchType.getSelectedIndex() == 2) {
			validateInput("Please enter the amount of health lost you would like to search for: ");
			if (searchInfo != null) {
				searchTable(2);
				checkIfFound(timesFound, "Health Lost value not found.");
			}
		}
		//Search for time
		else if (cbxSearchType.getSelectedIndex() == 3) {
			validateInput("Please enter the time you would like to search for: ");
			if (searchInfo != null) {
				searchTable(3);
				checkIfFound(timesFound, "Time not found.");
			}
		}
		//Search for total score
		else if (cbxSearchType.getSelectedIndex() == 4) {
			validateInput("Please enter the Total Score value you would like to search for: ");
			if (searchInfo != null) {
				searchTable(4);
				checkIfFound(timesFound, "Total Score not found.");
			}
		}
	}


	private void displaySearchResults() {
		displayScoreTable();
		btnSort.setVisible(false);
		btnSearch.setVisible(false);
		btnViewOriginal.setVisible(true);
		btnViewOriginal.setEnabled(true);
	}

	private void checkIfFound(int timesFound, String displayMessage) {
		if (timesFound > 0) {
			displaySearchResults();
		}
		else {
			JOptionPane.showMessageDialog(null, displayMessage);
		}
	}

	private void searchTable(int colNum) {
		boolean tableCleared = false;
		for (int i = 0; i < scoreTable.length; i++) {
			if (searchInfo.equalsIgnoreCase(scoreTable[i][colNum])) {
				if (!tableCleared) {
					model.setRowCount(0);
					tableCleared = true;
				}
				timesFound++;
				model.addRow(new Object[] {scoreTable[i][0], scoreTable[i][1], scoreTable[i][2], scoreTable[i][3], scoreTable[i][4]});
			}
		}
	}

	private void validateInput(String inputMessage) {
		boolean isValid = false;
		do {
			searchInfo = null;
			//Asks user to input what they want to search for, loop will continue until a valid input
			searchInfo = JOptionPane.showInputDialog(inputMessage);
			try {
				if (searchInfo == null) {
					isValid = true;
				}
				else {
					int temp = Integer.valueOf(searchInfo);
					isValid = true;
				}
			}
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter a valid number.");
			}
		} while (!isValid);
	}
}

