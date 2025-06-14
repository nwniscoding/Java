package csv;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.lang.StringBuilder;

/**
 * A class to parse CSV files.
 * It reads the content of a CSV file and stores it as a string.
 * This class can be used to process CSV data in various ways.
 * 
 * @author nwnisworking
 * @version 1.0
 * @since 14/6/2025
 */
public final class CSVParser{
	/**
	 * The text of the CSV file.
	 */
	private String text;

	/**
	 * The current row in the CSV file.
	 */
	private int row = 1;

	/**
	 * The current column in the CSV file.
	 */
	private int column = 1;

	/**
	 * The current cursor position in the CSV file.
	 */
	private int cursor = 0;

  private final StringBuilder sb = new StringBuilder();

	/**
	 * Constructs a CSVParser with an empty text.
	 */
	public CSVParser(){
		text = "";
	}
	
	/**
	 * Constructs a CSVParser with the given text.
	 * @param text the text of the CSV file
	 */
	public CSVParser(String text){
		this.text = text;
	}

	/**
	 * Constructs a CSVParser by reading the content of a file.
	 * @param file the file to read
	 * @throws IOException if an I/O error occurs while reading the file
	 */
	public CSVParser(File file) throws IOException{
		Scanner scanner = new Scanner(file, "UTF-8");

		scanner.useDelimiter("\\A");
		
		text = scanner.hasNext() ? scanner.next() : "";

		scanner.close();
	}

	/**
	 * Returns the current row in the CSV file.
	 * @return the current row
	 */
	public int getRow(){
		return row;
	}

	/**
	 * Returns the current column in the CSV file.
	 * @return the current column
	 */
	public int getColumn(){
		return column;
	}

	/**
	 * Returns the text of the CSV file.
	 * @return the text of the CSV file
	 */
	public String getText(){
		return text;
	}

	/**
	 * Set the text of the CSV file. This will reset the row and column counters.
	 */
	public void setText(String text){
		this.text = text;
		reset();
	}

	/**
	 * Resets the row, column, and cursor counters to their initial values.
	 */
	public void reset(){
		row = 1;
		column = 1;
		cursor = 0;
	}

  /**
   * Checks if the end of the file (EOF) has been reached.
   * @return true if the end of the file has been reached, false otherwise
   */
	public boolean isEOF(){
		return cursor >= text.length();
	}

  /**
   * Peeks at the next character in the text without advancing the cursor.
   * @return the next character, or '\0' if there are no more characters
   */
  public char peekNextChar(){
    if(isEOF()){
      return '\0';
    }
    return text.charAt(cursor + 1);
  }

  /**
   * Get the current short value from the CSV text.
   * @return the short value parsed from the CSV text
   * @throws NumberFormatException if the text cannot be parsed as a short
   */
  public short getShort(){
    return Short.parseShort(advanceUntil());
  }

  /**
   * Get the current integer value from the CSV text.
   * @return the integer value parsed from the CSV text
   * @throws NumberFormatException if the text cannot be parsed as an integer
   */
  public int getInt(){
    return Integer.parseInt(advanceUntil());
  }

  /**
   * Get the current long value from the CSV text.
   * @return the long value parsed from the CSV text
   * @throws NumberFormatException if the text cannot be parsed as a long
   */
  public long getLong(){
    return Long.parseLong(advanceUntil());
  }

  /**
   * Get the current float value from the CSV text.
   * @return the float value parsed from the CSV text
   * @throws NumberFormatException if the text cannot be parsed as a float
   */
  public float getFloat(){
    return Float.parseFloat(advanceUntil());
  }

  /**
   * Get the current double value from the CSV text.
   * @return the double value parsed from the CSV text
   * @throws NumberFormatException if the text cannot be parsed as a double
   */
  public double getDouble(){
    return Double.parseDouble(advanceUntil());
  }

  /**
   * Get the current string value from the CSV text.
   * @return the string value parsed from the CSV text
   * @throws IllegalStateException if no more data is available or if there is an unclosed quote
   */
  public String getString(){
    return advanceUntil();
  }

  public boolean getBoolean(){
    String value = advanceUntil().toLowerCase();
    if(value.equals("true") || value.equals("1")){
      return true;
    }
    else if(value.equals("false") || value.equals("0")){
      return false;
    }
    throw new IllegalArgumentException("Invalid boolean value: " + value);
  }


  /**
   * Advances the cursor until a comma or newline is found, while handling quoted strings.
   * @return the text from the current cursor position until a comma or newline is found
   */
	public String advanceUntil(){
    char c, nc = ' ';
    boolean is_quote = false;

    while(cursor < text.length()){
      c = text.charAt(cursor);
      nc = peekNextChar();
      
      if((nc == ',' && !is_quote) || nc == '\n'){
        cursor++;
        sb.append(c);
        break;
      }
      else if(c == '"' && !is_quote){
        is_quote = true;
      }
      else if(c == '"' && is_quote){
        if(nc == '"'){
          sb.append(c);
          cursor++;
        }
        else{
          is_quote = false;
        }
      }
      else if(c == ',' && !is_quote){
        column++;
      }
      else if(c == '\n'){
        column = 1;
        row++;
      }
      else{
        sb.append(c);
      }

      cursor++;
    }

    String text = sb.toString();
    sb.setLength(0); 
    
    if(text.length() == 0){
      throw new IllegalStateException("No more data to read from the CSV text.");
    }
    else if(is_quote){
      throw new IllegalStateException("Unclosed quote in CSV text.");
    }

    return text.trim();
	}
}