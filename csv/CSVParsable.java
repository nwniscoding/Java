package csv;

/**
 * Represents an object that can be parsed from a CSV file.
 */
public interface CSVParsable{
	/**
	 * Parses the object using the provided CSVParser.
	 * @param parser the CSVParser to use for parsing
	 */
	void parse(CSVParser parser);
}