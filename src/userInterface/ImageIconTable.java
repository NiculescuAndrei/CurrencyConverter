package userInterface;

import java.awt.Color;
import java.awt.Component;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ImageIconTable
{
	private JFrame frame;
	
	private JPanel panel;
	
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scroll;
	
	private JTextField searchField;

	private JLabel searchFieldLabel;
	
	private TableRowSorter<DefaultTableModel> rowSorter;
	
	public ImageIconTable()
	{
		frame = new JFrame("Currency List");
		
		searchFieldLabel = new JLabel("Enter the word you want to search for:");
		searchFieldLabel.setBounds(420,20,220,20);
		searchFieldLabel.setForeground(Color.white);
		
		searchField = new JTextField();
	    searchField.setBounds(650,20,150,20);
	    searchField.setBackground(new Color(108,122,137));
	    searchField.setForeground(Color.white);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		model = new DefaultTableModel()
	    {
	         @Override
	         public Class getColumnClass(int column)
	            {
	                return getValueAt(0, column).getClass();
	            }
	    };
	    
	    model.addColumn("Image");
	    model.addColumn("Currency Name");
	    model.addColumn("List of countries");

	    generateTable();
	    
	    table = new JTable(model)
	    {
	    	public boolean isCellEditable(int data, int columns)
			{
				return false;
			}
	    };

	    updateRowHeights();
	    
	    rowSorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(rowSorter);
	    
	    scroll = new JScrollPane(table);
	    scroll.setBounds(0,50,1400,388);
	    
	    // When the user types something in the search field, the data from the table is filtered and only those rows that contain the
	    // content of the field are displayed.
	    searchField.getDocument().addDocumentListener(new DocumentListener()
	    		{
					@Override
					public void insertUpdate(DocumentEvent e) {
						 String text = searchField.getText();
						 	// If there is nothing written in the search field, the data is not filtered.
			                if (text.trim().length() == 0) {
			                    rowSorter.setRowFilter(null);
			                } else {
			                	// We start case-insensitive mode with "?i" and we give the text we want to search for as a parameter and the data
			                	// is filtered.
			                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
			                }
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						String text = searchField.getText();
						// If there is nothing written in the search field, the data is not filtered.
		                if (text.trim().length() == 0) {
		                    rowSorter.setRowFilter(null);
		                } else {
		                	// We start case-insensitive mode with "?i" and we give the text we want to search for as a parameter and the data
		                	// is filtered.
		                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		                }
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						 throw new UnsupportedOperationException("Not supported yet.");
					}
	    		});
	    
	    panel.add(searchFieldLabel);
	    panel.add(searchField);
	    panel.add(scroll);
	    
	    frame.getContentPane().add(panel);
	    panel.setBackground(new Color(44,62,80));
	    frame.setSize(1415,500);
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setVisible(true);
	    
	}
	
	// Function that sets the height of each cell in the table to the maximum between the row height of the table and the preferred size
	// of the height of the current cell.
	private void updateRowHeights()
    {
        try
        {
            for (int row = 0; row < table.getRowCount(); row++)
            {
                int rowHeight = table.getRowHeight();

                for (int column = 0; column < table.getColumnCount(); column++)
                {
                    Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                    rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                }
                table.setRowHeight(row, rowHeight);
            }
        }
        catch(ClassCastException e) {}
    }
	
	// Function that reads from the currency_list file to get the name of the currency and the countries in which the currency is used.
	// This function also populate the table with data (each row consists in one cell that displays one representative image, one cell for the
	// name of the currency and the last one for the countries).
	private void generateTable()
	{	
		try {
			File myFile = new File("currency_list.txt");
			Scanner myScanner = new Scanner(myFile);
			while(myScanner.hasNextLine())
			{
				String currencyName = myScanner.nextLine();
				String countries = myScanner.nextLine();
				
				String[] splitString = currencyName.split(" ");
				model.addRow(new Object[] {new ImageIcon("images/"+splitString[0]+".png"),currencyName,countries});
			}
			myScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

