package userInterface;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class AboutJFrame 
{
	private JFrame frame;
	private JLabel name;
	private JLabel numberCurrencies;
	private JLabel limit;
	private JLabel history;
	private JLabel table;
	private JLabel table2;
	
	public AboutJFrame()
	{
		frame = new JFrame("Details about the application");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		name = new JLabel("Currency converter made by Niculescu Marius-Andrei");
		numberCurrencies = new JLabel("- You can choose between 142 currencies, depending on your needs.");
		limit = new JLabel("- The limit for the amount of money you can convert is set to 10 digits.");
		history = new JLabel("- You can see the history of your exchanges and you can clear it whenever you want.");
		table = new JLabel("- You can check a detailed table with the name of all the currencies available in the drop-down list");
		table2 = new JLabel("  and the data inside the table is filtered based on what you type into the search field.");
		
		name.setBounds(80,30,450,20);
		name.setForeground(Color.white);
		name.setFont(new Font("Times New Roman",Font.BOLD,18));
		numberCurrencies.setBounds(35,80,400,20);
		numberCurrencies.setForeground(Color.white);
		limit.setBounds(35,110,400,20);
		limit.setForeground(Color.white);
		history.setForeground(Color.white);
		history.setBounds(35,140,500,20);
		table.setBounds(35,170,550,20);
		table.setForeground(Color.white);
		table2.setBounds(35,200,500,20);
		table2.setForeground(Color.white);
		
		frame.add(name);
		frame.add(numberCurrencies);
		frame.add(limit);
		frame.add(history);
		frame.add(table);
		frame.add(table2);
		
		frame.setSize(620,350);
		frame.getContentPane().setBackground(new Color(44,62,80));
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
}
