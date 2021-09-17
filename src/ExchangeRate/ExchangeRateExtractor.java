package ExchangeRate;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ExchangeRateExtractor 
{
	public String getExchangeRate(String nameOfCurrency1, String nameOfCurrency2)
	{
		try
		{
			Document doc = Jsoup.connect("https://wise.com/gb/currency-converter/"+nameOfCurrency1+"-to-"+nameOfCurrency2+"-rate?amount=1").get();
			Element temp = doc.select("h3.cc__source-to-target").first();
			String[] tempSplit = temp.text().split(" ");
			return tempSplit[3];
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
