package userInterface;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

// Class used to create a JTextField in which the user can introduce only a certain amount of characters based on the limit given to the field.
public class JTextFieldLimit extends PlainDocument
{
	private int limit;
	
	public JTextFieldLimit(int limitation)
	{
		this.limit = limitation;
	}
	
	public void insertString(int offset, String str, AttributeSet set) throws BadLocationException
	{
		if(str==null)
		{
			return;
		}
		if( (getLength() + str.length()) <= limit )
		{
			super.insertString(offset, str, set);
		}
	}
}
