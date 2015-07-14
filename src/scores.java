//Imports needed
import java.awt.EventQueue;
import java.awt.Font;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

/**
 * @ Description: Score class, displays the scoreboard and also allows user to search and sort
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */


public class scores extends JFrame implements ActionListener {

	//Creation of all variables and objects in the JFrame
	private JPanel contentPane;
	private JTable table;
	private Clip audioClip;
	private JComboBox cbxSortType = new JComboBox();
	private JComboBox cbxSearchType = new JComboBox();

	private JButton btnSearch = new JButton("Search");
	private JButton btnSort = new JButton("Sort");
	private JButton btnViewOriginal;
	private DefaultTableModel model = new DefaultTableModel();
	private DefaultTableModel searchModel = new DefaultTableModel();


	private String searchInfo;
	private int timesFound = 0;
	private int exit;
	private int entryNum = 1;
	private int sortType;

	private String fileName = "scores.txt";
	private String [] tableHeaders = new String[5];
	private String [][] scoreTable = new String [entryNum][5];
	private String [][] searchArray;
	private String tempData[];
	private String inputText;

	//Opens the frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					scores frame = new scores();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor for class
	public scores() {
		//Creates title, sets size and adds it to the content pane
		setTitle("Scores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 550);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		//Creation of all buttons and their respective actionListeners
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

		//Label for the frame
		JLabel lblScoreBoard = new JLabel("Scoreboard");
		lblScoreBoard.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoreBoard.setForeground(Color.WHITE);
		lblScoreBoard.setFont(new Font("SWTOR Trajan", Font.BOLD | Font.ITALIC, 35));
		lblScoreBoard.setBounds(10, 29, 589, 56);
		contentPane.add(lblScoreBoard);

		//Combobox for selecting sort type
		cbxSortType.setForeground(Color.BLUE);
		cbxSortType.setFont(new Font("SWTOR Trajan", Font.PLAIN, 13));
		cbxSortType.setModel(new DefaultComboBoxModel(new String[] {"Most Points", "Least Health Lost", "Fastest Time", "Total Score"}));
		cbxSortType.setBounds(173, 321, 211, 29);
		cbxSortType.setSelectedIndex(-1);
		contentPane.add(cbxSortType);

		//Sort button
		btnSort.setForeground(Color.BLUE);
		btnSort.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnSort.setBounds(414, 321, 141, 29);
		btnSort.addActionListener(this);
		btnSort.setActionCommand("Sort");
		contentPane.add(btnSort);

		//Search button
		btnSearch.setForeground(Color.BLUE);
		btnSearch.setFont(new Font("SWTOR Trajan", Font.PLAIN, 18));
		btnSearch.setBounds(414, 361, 141, 29);
		btnSearch.addActionListener(this);
		btnSearch.setActionCommand("Search");
		contentPane.add(btnSearch);

		//Combobox for selecting search type
		cbxSearchType.setForeground(Color.BLUE);
		cbxSearchType.setModel(new DefaultComboBoxModel(new String[] {"Name", "Points", "Health Lost", "Time", "Total Score"}));
		cbxSearchType.setFont(new Font("SWTOR Trajan", Font.PLAIN, 13));
		cbxSearchType.setBounds(173, 361, 211, 29);
		cbxSearchType.setSelectedIndex(-1);
		contentPane.add(cbxSearchType);

		//Labels that tell what each combobox is
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

		//Button to view original table after search has been done
		btnViewOriginal = new JButton("View Original");
		btnViewOriginal.setForeground(Color.BLUE);
		btnViewOriginal.setEnabled(false);
		btnViewOriginal.setFont(new Font("SWTOR Trajan", Font.PLAIN, 12));
		btnViewOriginal.setVisible(false);
		btnViewOriginal.addActionListener(this);
		btnViewOriginal.setActionCommand("View Original");
		btnViewOriginal.setBounds(403, 328, 152, 56);
		contentPane.add(btnViewOriginal);

		//Calls the methods that initializes table, reads info from file, and then displays to frame
		tableHeaders();
		inputFromFile();
		setModelTable();
		displayScoreTable();

		//Starts the music, keeps it looping continuously
		try {
			URL url = this.getClass().getResource("music/scoresAudio.wav");
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

	//Method that is called when specific buttons are pressed
	public void actionPerformed(ActionEvent evt) {

		//Different frames are opened depending on which action command
		if (evt.getActionCommand().equals("Play")) {
			//Gets name of user before closing this screen and opening loading screen
			menu.name = JOptionPane.showInputDialog("Please enter your name: ");
			audioClip.stop();
			this.dispose();
			control.loadFrame = new loadingScreen();
			control.loadFrame.setVisible(true);
		}
		//Returns to main menu
		if (evt.getActionCommand().equals("Menu")) {
			audioClip.stop();
			this.dispose();
			control.menuFrame = new menu();
			control.menuFrame.setVisible(true);
		}
		//Exits program after user confirmation
		if (evt.getActionCommand().equals("Exit")) {
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (exit == 0) {
				System.exit(0);
			}
		}
		//Calls the sort method
		if (evt.getActionCommand().equals("Sort")) {
			//Will only sort if user has selected something in the combobox, otherwise will tell the user to make a choice first
			if (cbxSortType.getSelectedIndex() >= 0) {
				sortScoreTable();
			}
			else {
				JOptionPane.showMessageDialog(null, "Please first select your sort type.");
			}
		}
		//Calls the search method
		if (evt.getActionCommand().equals("Search")) {
			timesFound = 0;
			//Will only search if user has selected something in the combobox, otherwise will tell the user to make a choice first
			if (cbxSearchType.getSelectedIndex() >= 0) {
				searchScoreTable();
			}
			else {
				JOptionPane.showMessageDialog(null, "Please first select your search type.");
			}
		}
		//When view original is pressed, all the data in the searchArray is removed from the default model
		if (evt.getActionCommand().equals("View Original")) {
			for (int i = 0; i < searchArray.length; i++) {
				model.removeRow(0);
			}
			//Default table is added back into the model
			for (int i = 0; i < scoreTable.length; i++) {
				model.addRow(new Object[] {scoreTable[i][0], scoreTable[i][1], scoreTable[i][2], scoreTable[i][3], scoreTable[i][4]});
			}
			//Displays the scoretable and resets the buttons to original state
			displayScoreTable();
			btnSort.setVisible(true);
			btnSearch.setVisible(true);
			btnViewOriginal.setVisible(false);
			btnViewOriginal.setEnabled(false);
		}
	}//End of actionPerformed method

	//tableHeaders method. used to initialize the TableHeaders Array.
	public void tableHeaders(){
		tableHeaders[0]=("Name");
		tableHeaders[1]=("Points");
		tableHeaders[2]=("Health Lost");
		tableHeaders[3]=("Time (s)");
		tableHeaders[4]=("Total Score");
	}//End of tableHeaders method

	//Method that reads textFile and copies that data into the array when program starts
	public void inputFromFile() {
		entryNum = 0;
		scoreTable = new String[entryNum][5];
		try {
			//Create FileReader and BufferReader to read input from text file
			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);

			//Loop to continue reading each line of text in file until there is none left
			while ((inputText = bufferReader.readLine()) != null) {
				//Prevents errors when splitting string
				try {
					//Removes the tabs from each line, and puts it into an array
					tempData = inputText.split("\t");
					//Sets values into the scoreTable based on text input file
					//Calls resizeScoreTable, which resizes the array and then sets all the data in
					resizeScoreTable();
					scoreTable[entryNum - 1][0] = tempData[0];
					scoreTable[entryNum - 1][1] = tempData[1];
					scoreTable[entryNum - 1][2] = tempData[2];
					scoreTable[entryNum - 1][3] = tempData[3];
					scoreTable[entryNum - 1][4] = tempData[4];
				}
				catch (Exception e) {
				}
			}
			//Close reader
			bufferReader.close();
		}
		//Prints error if try can't be run
		catch (IOException e) {
			e.printStackTrace();
		}
	}//End of inputFromTextFile method

	//Method that displays the table on the frame
	public void displayScoreTable() {
		//refresh the Jtable and set the data showing to the default table model
		table = new JTable(model) {
			//Makes all cells uneditable
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		//Sets the width for all the headers in the table
		table.getColumnModel().getColumn(0).setPreferredWidth(175);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		table.getTableHeader().setReorderingAllowed(false);
		//creating scrollPane with Jtable and scroll bars within it
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(50, 97, 505, 199);
		try {
			contentPane.add(scrollPane);
		}
		catch (NullPointerException e) {
		}
	}//End displayScoreTable method

	//resizeScoreTable method. Used to resize scoreTable array in order to make space for a new entry
	public void resizeScoreTable() {
		//Create a temp array to hold the data
		String[][] tempScoreTable = new String [entryNum][5];
		entryNum++;
		//Copies current array data into the temporary one
		for (int row = 0; row < scoreTable.length;row++) {
			for (int column = 0; column < scoreTable[0].length;column++) {
				tempScoreTable[row][column] = scoreTable[row][column];
			}
		}
		//Create a new scoreTable array. with increased size
		//Info in the tempArray is then copied back into the resized array
		scoreTable = new String[entryNum][5];
		for (int row = 0; row < tempScoreTable.length;row++){
			for (int column = 0; column < tempScoreTable[0].length;column++) {
				scoreTable[row][column] = tempScoreTable[row][column];
			}
		}
	}//End of resizeScoreTable method

	//Method that initializes the default table model, so it can be displayed
	public void setModelTable() {
		//Adds the headers to the model
		for (int i = 0; i < tableHeaders.length; i++) {
			model.addColumn(tableHeaders[i]);
			searchModel.addColumn(tableHeaders[i]);
		}
		//Adds the info in the scoreTable array to the model
		for (int i = 0; i < scoreTable.length; i++) {
			model.addRow(new Object[] {scoreTable[i][0], scoreTable[i][1], scoreTable[i][2], scoreTable[i][3], scoreTable[i][4]});
		}
	}//End setModelTable method

	//Method that calls various sort algorithms depending on user selecting
	public void sortScoreTable() {
		//Sort for most points
		if (cbxSortType.getSelectedIndex() == 0) {
			//Calls descending sort, since points is from highest to lowest. The parameter is the column number for points
			descendingSort(1);
		}
		//Sort for least health lost
		else if (cbxSortType.getSelectedIndex() == 1) {
			//Calls ascending sort, since least health lost is from least to most. The parameter is the column number for least health lost
			ascendingSort(2);
		}
		//Sort for best time
		else if (cbxSortType.getSelectedIndex() == 2) {
			//Calls ascending sort, since best time is from least to most. The parameter is the column number for time
			ascendingSort(3);
		}
		//Sort for best overall score
		else if (cbxSortType.getSelectedIndex() == 3) {
			//Calls descending sort, since score is from highest to lowest. The parameter is the column number for total score
			descendingSort(4);
		}
		//Removes the original scoreTable from the table model
		for (int i = 0; i < scoreTable.length; i++) {
			model.removeRow(0);
		}
		//Adds the sorted array into the table model
		for (int i = 0; i < scoreTable.length; i++) {
			model.addRow(new Object[] {scoreTable[i][0], scoreTable[i][1], scoreTable[i][2], scoreTable[i][3], scoreTable[i][4]});
		}
		//Displays table after sort is complete
		displayScoreTable();
	}

	//All sorting is done with selection sort---------------------------------------------------------------------------------------------

	//Method for ascending sort
	public void ascendingSort(int input) {
		//Create temporary array to store info, allows swapping
		String [][] tempArray = new String[1][5];
		int num = 0;
		int x;
		//First loop runs until the second last number in the array
		for (num = 0; num < (scoreTable.length - 1); num++) {
			//Second loop starts always at the array position right after the num position
			//Loop goes to the last number
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
	}//End ascendingSort method

	//Method that sorts from highest to lowest
	public void descendingSort(int input) {
		//Create temporary array to store info, allows swapping
		String [][] tempArray = new String[1][5];
		int num = 0;
		int x;
		//First loop runs until the second last number in the array
		for (num = 0; num < (scoreTable.length - 1); num++) {
			//Second loop starts always at the array position right after the num position
			//Loop goes to the last number
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
	}//End descendingSort method

	//Searching method
	public void searchScoreTable() {
		//Search for name
		if (cbxSearchType.getSelectedIndex() == 0) {
			//Asks user to input what they want to search for
			searchInfo = JOptionPane.showInputDialog("Please enter the name you would like to search for: ");
			if (searchInfo != null) {
				//Runs through the array, if found, timesFound++
				for (int i = 0; i < scoreTable.length; i++) {
					if (searchInfo.equalsIgnoreCase(scoreTable[i][0])) {
						timesFound++;
					}
				}
				//Will only displaySearchResults is input is found, otherwise tells user their entry was not found
				if (timesFound > 0) {
					displaySearchResults(0);
				}
				else {
					JOptionPane.showMessageDialog(null, "Your search entry was not found.");
				}
			}
		}
		//Search for points
		else if (cbxSearchType.getSelectedIndex() == 1) {
			boolean isValid = false;
			do {
				searchInfo = null;
				//Asks user to input what they want to search for, loop will continue until a valid entry is made
				searchInfo = JOptionPane.showInputDialog("Please enter the number of points you would like to search for: ");
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
					JOptionPane.showMessageDialog(null, "Please enter a number.");
				}
			} while (isValid == false);
			if (searchInfo != null) {
				//Runs through the array, if found, timesFound++
				for (int i = 0; i < scoreTable.length; i++) {
					if (searchInfo.equalsIgnoreCase(scoreTable[i][1])) {
						timesFound++;
					}
				}
				//Will only displaySearchResults is input is found, otherwise tells user their entry was not found
				if (timesFound > 0) {
					displaySearchResults(1);
				}
				else {
					JOptionPane.showMessageDialog(null, "Your number was not found.");
				}
			}
		}
		//Search for health lost
		else if (cbxSearchType.getSelectedIndex() == 2) {
			boolean isValid = false;
			do {
				searchInfo = null;
				//Asks user to input what they want to search for, loop will continue until a valid input
				searchInfo = JOptionPane.showInputDialog("Please enter the amount of health lost you would like to search for: ");
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
					JOptionPane.showMessageDialog(null, "Please enter a number.");
				}
			} while (isValid == false);
			if (searchInfo != null) {
				//Runs through the array, if found, timesFound++
				for (int i = 0; i < scoreTable.length; i++) {
					if (searchInfo.equalsIgnoreCase(scoreTable[i][2])) {
						timesFound++;
					}
				}
				//Will only displaySearchResults is input is found, otherwise tells user their entry was not found
				if (timesFound > 0) {
					displaySearchResults(2);
				}
				else {
					JOptionPane.showMessageDialog(null, "Your search entry was not found.");
				}
			}
		}
		//Search for time
		else if (cbxSearchType.getSelectedIndex() == 3) {
			boolean isValid = false;
			do {
				searchInfo = null;
				//Asks user to input what they want to search for, loop will continue until a valid input
				searchInfo = JOptionPane.showInputDialog("Please enter the time you would like to search for: ");
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
					JOptionPane.showMessageDialog(null, "Please enter a number.");
				}
			} while (isValid == false);
			if (searchInfo != null) {
				//Runs through the array, if found, timesFound++
				for (int i = 0; i < scoreTable.length; i++) {
					if (searchInfo.equalsIgnoreCase(scoreTable[i][3])) {
						timesFound++;
					}
				}
				//Will only displaySearchResults is input is found, otherwise tells user their entry was not found
				if (timesFound > 0) {
					displaySearchResults(3);
				}
				else {
					JOptionPane.showMessageDialog(null, "Your search entry was not found.");
				}
			}
		}
		//Search for total score
		else if (cbxSearchType.getSelectedIndex() == 4) {
			boolean isValid = false;
			do {
				searchInfo = null;
				//Asks user to input what they want to search for, loop will continue until a valid input
				searchInfo = JOptionPane.showInputDialog("Please enter the total score you would like to search for: ");
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
					JOptionPane.showMessageDialog(null, "Please enter a number.");
				}
			} while (isValid == false);
			if (searchInfo != null) {
				//Runs through the array, if found, timesFound++
				for (int i = 0; i < scoreTable.length; i++) {
					if (searchInfo.equalsIgnoreCase(scoreTable[i][4])) {
						timesFound++;
					}
				}
				//Will only displaySearchResults is input is found, otherwise tells user their entry was not found
				if (timesFound > 0) {
					displaySearchResults(4);
				}
				else {
					JOptionPane.showMessageDialog(null, "Your search entry was not found.");
				}
			}
		}
	}//End searchScoreTable method

	//Method that puts the search results into the table model to be displayed, parameter is the column user is searching in
	public void displaySearchResults(int searchNum) {
		//Creates a new array based on how many times something was found
		searchArray = new String[timesFound][5];
		int searchRow = 0;
		//Runs through the array
		for (int k = 0; k < scoreTable.length; k++) {
			//If user input is found, the entire row of data is copied into the searchArray
			if (searchInfo.equalsIgnoreCase(scoreTable[k][searchNum])) {
				for (int j = 0; j < scoreTable[k].length; j++) {
					searchArray[searchRow][j] = scoreTable[k][j];
				}
				searchRow++;
			}
		}
		//Empties the current table model
		for (int i = 0; i < scoreTable.length; i++) {
			model.removeRow(0);
		}
		//Copies the data of the searchArray into the model
		for (int i = 0; i < searchArray.length; i++) {
			model.addRow(new Object[] {searchArray[i][0], searchArray[i][1], searchArray[i][2], searchArray[i][3], searchArray[i][4]});
		}
		//Displays the table, hides search and sort buttons, shows viewOriginal button
		displayScoreTable();
		btnSort.setVisible(false);
		btnSearch.setVisible(false);
		btnViewOriginal.setVisible(true);
		btnViewOriginal.setEnabled(true);
	}//End displaySearchResults method

}//End of scores class

