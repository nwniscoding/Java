package sys;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IO{
  private static final Scanner scanner = new Scanner(System.in);

  /**
   * Prompts the user for a string input with validation.
   * @param msg the message to display to the user
   * @param validator an array of validators to apply to the input
   * @return the validated string input
   */
  @SafeVarargs
  public static <T> String getString(String msg, Validator<String> ...validator){
    return getInput(msg, String.class, validator);
  }

  /**
   * Prompts the user for a short input with validation.
   * @param msg the message to display to the user
   * @param validator an array of validators to apply to the input
   * @return the validated short input
   */
  @SafeVarargs
  public static Short getShort(String msg, Validator<Short> ...validator){
    return getInput(msg, Short.class, validator);
  }

  /**
   * Prompts the user for an integer input with validation.
   * @param msg the message to display to the user
   * @param validator an array of validators to apply to the input
   * @return the validated integer input
   */
  @SafeVarargs
  public static Integer getInt(String msg, Validator<Integer> ...validator){
    return getInput(msg, Integer.class, validator);
  }

  /**
   * Prompts the user for a long input with validation.
   * @param msg the message to display to the user
   * @param validator an array of validators to apply to the input
   * @return the validated long input
   */
  @SafeVarargs
  public static Long getLong(String msg, Validator<Long> ...validator){
    return getInput(msg, Long.class, validator);
  }

  /**
   * Prompts the user for a float input with validation.
   * @param msg the message to display to the user
   * @param validator an array of validators to apply to the input
   * @return the validated float input
   */
  @SafeVarargs
  public static Float getFloat(String msg, Validator<Float> ...validator){
    return getInput(msg, Float.class, validator);
  }

  /**
   * Prompts the user for a double input with validation.
   * @param msg the message to display to the user
   * @param validator an array of validators to apply to the input
   * @return the validated float input
   */
  @SafeVarargs
  public static Double getDouble(String msg, Validator<Double> ...validator){
    return getInput(msg, Double.class, validator);
  }

  /**
   * Prints the text to the console with a newline. 
   * @param <T> the type of the object to print
   * @param text the text to print
   */
  @SafeVarargs
  public static <T> void line(T ...text){
    for(T t : text){
      System.out.println(t);
    }
  }

  /**
   * Prints the text to the console with space. 
   * @param <T> the type of the object to print
   * @param text the text to print
   */
  @SafeVarargs
  public static <T> void print(T ...text){
    for(T t : text){
      System.out.print(t);
    }
  }

  /**
   * Validates the given data against the provided validators.
   * @param <T> the type of the data to validate
   * @param data the data to validate
   * @param validators the array of validators to apply
   * @return true if all validators pass, false otherwise
   */
  private static <T> boolean validate(T data, Validator<T> validators[]){
    for(Validator<T> v : validators)
      if(!v.validate(data)) return false;

    return true;
  }

  /**
   * Prompts the user for input of a specified type and applies the provided validators.
   * @param <T> the type of the input
   * @param msg the message to display to the user
   * @param type the class type of the input
   * @param validators an array of validators to apply to the input
   * @return the validated input of the specified type
   */
  private static <T> T getInput(String msg, Class<T> type, Validator<T> validators[]){
    while(true){
      try{
        print(msg);

        T input = null;

        if(type == String.class)
          input = type.cast(scanner.nextLine());

        else if(type == Short.class)
          input = type.cast(scanner.nextShort());

        else if(type == Long.class)
          input = type.cast(scanner.nextLong());

        else if(type == Double.class)
          input = type.cast(scanner.nextDouble());

        else if(type == Float.class)
          input = type.cast(scanner.nextFloat());

        else if(type == Integer.class)
          input = type.cast(scanner.nextInt());

        if(validate(input, validators)) return input;
      }
      catch(InputMismatchException e){}
    }
  }
}
