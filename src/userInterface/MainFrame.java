package userInterface;
import javax.swing.*;
import javax.swing.border.Border;

import ExchangeRate.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainFrame 
{
	public static final String currencyNames[] = {"GBP","EUR","USD","INR","CAD","AUD","CHF","MXN","FJD","SCR","BBD","GTQ","CLP","HNL","UGX","ZAR",
			  "TND","BSD","SLL","GMD","TWD","RSD","DOP","KMF","MYR","FKP","XOF","GEL","UYU","MAD","CVE",
			  "TOP","AZN","OMR","PGK","KES","SEK","BTN","UAH","GNF","MZN","SVC","ARS","QAR","CNY","THB",
			  "UZS","XPF","MRU","BDT","BMD","KWD","PHP","RUB","PYG","ISK","JMD","COP","MKD","DZD","PAB",
			  "GGP","SGD","ETB","JEP","KGS","VUV","LAK","BND","XAF","LRD","HRK","ALL","DJF","ZMW","TZS",
			  "VND","ILS","GHS","GYD","BOB","KHR","MDL","IDR","KYD","AMD","BWP","SHP","TRY","LBP","TJS",
			  "JOD","AED","HKD","RWF","LSL","DKK","BGN","MMK","MUR","NOK","IMP","GIP","RON","LKR","NGN",
			  "CRC","CZK","PKR","XCD","ANG","HTG","BHD","KZT","SRD","SZL","SAR","TTD","MVR","AWG","KRW",
			  "NPR","JPY","MNT","AOA","PLN","SBD","BYN","HUF","MWK","MGA","BZD","BAM","EGP","MOP","NAD",
			  "NIO","PEN","NZD","WST","TMT","BRL"};
	private PrintWriter out = null;

	private JFrame frame;
	
	private JLabel firstCurrencyLabel;
	private JLabel secondCurrencyLabel;
	private JLabel amountLabel;
	private JLabel resultLabel;
	private JLabel limitLabel;
	
	private JMenuBar menuBar;
	
	private JMenu helpMenu;
	private JMenu historyMenu;
	private JMenu currencyMenu;
	
	private JMenuItem aboutItem;
	private JMenuItem historyItem;
	private JMenuItem currencyItem;
	
	private JTextField amountOfCurrency;
	
	private JButton button;
	
	private JComboBox<String> comboBox1; 
    private JComboBox<String> comboBox2;
    
	public MainFrame()
	{
		frame = new JFrame("Currency Converter");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(44,62,80));
		
		firstCurrencyLabel = new JLabel("Select first currency:");
		firstCurrencyLabel.setForeground(Color.white);
		
		secondCurrencyLabel = new JLabel("Select second currency:");
		secondCurrencyLabel.setForeground(Color.white);
		
		amountLabel = new JLabel("Amount:");
		amountLabel.setForeground(Color.white);
		
		resultLabel = new JLabel();
		resultLabel.setForeground(Color.white);
		
		limitLabel = new JLabel("(Maximum 10 characters)");
		limitLabel.setForeground(Color.white);
		
		menuBar = new JMenuBar();
		
		helpMenu = new JMenu("Help");
		historyMenu = new JMenu("History");
		currencyMenu = new JMenu("Currency List");
		
		aboutItem = new JMenuItem("About");
		historyItem = new JMenuItem("See history");
		currencyItem = new JMenuItem("See currency list");
		
		amountOfCurrency = new JTextField();
		amountOfCurrency.setBackground(new Color(108,122,137));
		amountOfCurrency.setForeground(Color.white);
		amountOfCurrency.setDocument(new JTextFieldLimit(10));
		amountOfCurrency.setText("1");
		
		button = new JButton("Convert");
		
		comboBox1 = new JComboBox<String>(currencyNames);
		comboBox2 = new JComboBox<String>(currencyNames);
		
		firstCurrencyLabel.setBounds(10,50,120,20);
		secondCurrencyLabel.setBounds(300,50,150,20);
		amountLabel.setBounds(150,160,100,20);
		resultLabel.setBounds(150,220,350,20);
		limitLabel.setBounds(365,162,155,20);
		
		comboBox1.setBounds(140, 50,90,20);
		comboBox2.setBounds(450, 50,90,20);		
		
		amountOfCurrency.setBounds(210, 162, 150, 20);
		
		button.setBounds(225,300,100, 40);
		
		button.setBackground(new Color(74,171,255));
		button.setForeground(Color.white);
		button.setFont(new Font("Times New Roman",Font.BOLD,18));
		
		helpMenu.add(aboutItem);
		historyMenu.add(historyItem);
		currencyMenu.add(currencyItem);
		menuBar.add(helpMenu);
		menuBar.add(historyMenu);
		menuBar.add(currencyMenu);
		
		frame.setJMenuBar(menuBar);

		frame.add(firstCurrencyLabel);
		frame.add(secondCurrencyLabel);
		frame.add(comboBox1);
		frame.add(comboBox2);
		frame.add(amountLabel);
		frame.add(resultLabel);
		frame.add(limitLabel);
		frame.add(amountOfCurrency);
		frame.add(button);

		frame.setSize(600,500);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setVisible(true);
		
	}
	
	public void run()
	{
		// When we click the first drop-down list and select a currency, we won't be able to select the same currency in the second list.
		comboBox1.addActionListener(new ActionListener()
	    		{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						String selectedCurrency = (String) comboBox1.getSelectedItem();
						comboBox2.removeAllItems();
						for(String iterator : currencyNames)
						{
							if(iterator.equals(selectedCurrency)==true)
							{
								continue;
							}
							else
							{
								comboBox2.addItem(iterator);
							}
						}
					}
	    			
	    		});
		
		
		button.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				ExchangeRateExtractor extractor = new ExchangeRateExtractor();
				// I treat the case in which the 2 currencies are the same
				if(comboBox1.getSelectedItem().equals(comboBox2.getSelectedItem())==true)
				{
					try
					{
						BigDecimal amount = new BigDecimal(amountOfCurrency.getText());
						BigDecimal zero = new BigDecimal("0");
						
						// If the amount of money given by the user is less than 0, we give him a message to enter a positive number.
						if(amount.compareTo(zero)<0)
						{
							resultLabel.setText("");
							JOptionPane.showMessageDialog(null, "You entered a negative value! You can't exchange negative amount of money!");
						}
						// If the amount of money is greater or equal than 0, we print the result which is equal to the amount of money
						// (since the 2 currencies are same the exchange rate is 1:1). We also save the result in a history file.
						else
						{
							resultLabel.setText("Result: " + amount + " " + comboBox1.getSelectedItem() + " = " + amount + " " + comboBox2.getSelectedItem());
							
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
							LocalDateTime now = LocalDateTime.now(); 
							String date = dtf.format(now);
							
							out = new PrintWriter(new BufferedWriter(new FileWriter("history.txt",true)));
							out.println(date);
							out.println("Result: " + amount + " " + comboBox1.getSelectedItem() + " = " + amount + " " + comboBox2.getSelectedItem());
						}
						// If the amount of money introduced by the user is not a number, he is warned about it.
					}catch (NumberFormatException exception) {
						  resultLabel.setText("");
						  JOptionPane.showMessageDialog(null, "You didn't enter a number! Try to enter a value next time.");						  
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					finally {
					    if (out != null) {
					        out.close();
					    }
					} 
				}
				// I treat the case in which the 2 currencies are different
				else
				{
					// In variable result I store the exchange rate between the 2 currencies.
					String result = extractor.getExchangeRate(comboBox1.getSelectedItem().toString().toLowerCase(), comboBox2.getSelectedItem().toString().toLowerCase());
					BigDecimal value = new BigDecimal(result);
					try
					{
						BigDecimal amount = new BigDecimal(amountOfCurrency.getText());
						BigDecimal zero = new BigDecimal("0");
						
						// If the amount of money given by the user is less than 0, we give him a message to enter a positive number.
						if(amount.compareTo(zero)<0)
						{
							resultLabel.setText("");
							JOptionPane.showMessageDialog(null, "You entered a negative value! You can't exchange negative amount of money!");
						}
						// If the amount of money is greater or equal than 0, we print the result which is equal to the amount of money introduced
						// by the user * the exchange rate between the currencies. We also save the result in a history file.
						else
						{
							resultLabel.setText("Result: " + amount + " " + comboBox1.getSelectedItem() + " = " + value.multiply(amount) + " " + comboBox2.getSelectedItem());
							
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
							LocalDateTime now = LocalDateTime.now(); 
							String date = dtf.format(now);
							
							out = new PrintWriter(new BufferedWriter(new FileWriter("history.txt",true)));
							out.println(date);
							out.println("Result: " + amount + " " + comboBox1.getSelectedItem() + " = " + value.multiply(amount) + " " + comboBox2.getSelectedItem() + " with an exchange rate of " + "1 : " + value);
							
						}
						// If the amount of money introduced by the user is not a number, he is warned about it.
					}catch (NumberFormatException exception) {
						  resultLabel.setText("");
						  JOptionPane.showMessageDialog(null, "You didn't enter a number! Try to enter a value next time.");
						  
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					finally {
					    if (out != null) {
					        out.close();
					    }
					} 
				}
			}
			
		}); 
		
		// If the user presses the Help->About menu, the frame containing details about the application will appear.
		aboutItem.addActionListener(new ActionListener() 
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						AboutJFrame aboutFrame = new AboutJFrame();
					}
				
				});
		
		// If the user presses the History->See history menu, he can see the history of all the exchanges he did. In the newly appeared window
		// he can clear the history, or he can go back to the main window.
		historyItem.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						HistoryJFrame historyFrame = new HistoryJFrame();
						frame.dispose();
					}
			
				});
		
		// If the user presses Currency list->See currency list, he can see a table containing all the currencies he can select from the drop-down
		// lists, in the main window and the countries in which the currencies are used. There is also a field used to filter the content of the
		// table(only those rows of the table that contain the content of the field will be displayed).
		currencyItem.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						ImageIconTable imageIconTable = new ImageIconTable();

					}
				});
	}	
}
