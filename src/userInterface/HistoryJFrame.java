package userInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class HistoryJFrame 
{
	public static final String column[] = {"Date","Exchange"};
	private String data[][];
	
	private JFrame frame;
	
	private JButton clearHistory;
	private JButton goBack;
	
	private JTable historyTable;
	private JScrollPane scroll;
	
	private JPanel panel;
	
	private ArrayList<ArrayList<String>> dataFromHistory;	
	
	public HistoryJFrame()
	{
		frame = new JFrame("Exchange history");
		frame.getContentPane().setBackground(new Color(44,62,80));
		
		panel = new JPanel();
		panel.setBackground(new Color(44,62,80));
		panel.setPreferredSize(new Dimension(1300,400));
		
		dataFromHistory = new ArrayList<ArrayList<String>>();
		fillArrayList();
		changeArrayListToStringArray();
		
		clearHistory = new JButton("Clear history");
		// When the "Clear history" button is pressed, the data inside the history file is deleted and the current window is closed and the main
		// one is opened.
		clearHistory.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PrintWriter out = new PrintWriter("history.txt");
					out.close();
					
					MainFrame mainFrame = new MainFrame();
					mainFrame.run();
					
					frame.dispose();
					JOptionPane.showMessageDialog(null, "The history has been succesfully cleared!");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// When the "Go back" button is pressed, the current "Exchange history" window is closed, and the main window is opened.
		goBack = new JButton("Go back");
		goBack.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						MainFrame mainFrame = new MainFrame();
						mainFrame.run();
						frame.dispose();	
					}
				});
		
		historyTable = new JTable(data,column) 
		{
			// The user can't edit the content inside the table cells.
			public boolean isCellEditable(int data, int columns)
			{
				return false;
			}
			
			// Function used to set the background of the even rows of the table (for example second row, 4th, etc.)
			public Component prepareRenderer(TableCellRenderer renderer, int data, int columns)
			{
				// Variable "value" contains the value of the cells of the table
				Component value = super.prepareRenderer(renderer, data, columns);
				
				if(data%2==0)
				{
					value.setBackground(Color.WHITE);
				}
				else
				{
					value.setBackground(Color.LIGHT_GRAY);
				}
				
				return value;	
			}
		};

		scroll = new JScrollPane(historyTable);
		scroll.setPreferredSize(new Dimension(1300,182));
		
		panel.add(scroll);
		
		frame.add(panel);
		frame.add(clearHistory);
		frame.add(goBack);

		frame.setSize(1320,500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	// Function that reads from the history file, the date and the result of the exchange and put them into an ArrayList<String>.
	// This ArrayList<String> is further added to an ArrayList<ArrayList<String>>. This process is repeated until we read everything from the file.
	public void fillArrayList()
	{
		try {
			File myFile = new File("history.txt");
			Scanner myScanner = new Scanner(myFile);
			while(myScanner.hasNextLine())
			{
				String date = myScanner.nextLine();
				String exchange = myScanner.nextLine();
				
				ArrayList<String> jTableRow = new ArrayList<String>();
				
				jTableRow.add(date);
				jTableRow.add(exchange);
				dataFromHistory.add(jTableRow);
			}
			myScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// Using the ArrayList<ArrayList<String>> created in the above function, we know how many rows the table will have (dataFromHistory.size()).
	// Every element of this list is another list that contains the date (first cell of the row) and the result of the exchange (second cell 
	// of the row).
	public void changeArrayListToStringArray()
	{
		data = new String[dataFromHistory.size()][2];
		for(int i=0;i<dataFromHistory.size();i++)
		{
			ArrayList<String> row = dataFromHistory.get(i);
			data[i][0] = row.get(0);
			data[i][1] = row.get(1);
		}
	}
	
}
