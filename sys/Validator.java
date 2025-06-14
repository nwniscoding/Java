package sys;

/**
 * A functional interface for validating objects of type T.
 *
 * @param <T> the type of object to validate. 
 */
@FunctionalInterface
public interface Validator<T>{
  /**
   * Validates the given object.
   *
   * @param data the object to validate
   * @return true if the object is valid, false otherwise
   */
  boolean validate(T data);

  /**
   * Checks if the give object has a minimum length or value.
   * @param <T> the type of object to validate
   * @param min the minimum length or value
   * @return a Validator that checks if the object meets the minimum requirement
   */
  static <T> Validator<T> min(double min){
    return e->{
      if(e instanceof Number num){
        return num.doubleValue() >= min;
      }
      else if(e instanceof String str){
        return str.length() >= min;
      }

      throw new IllegalArgumentException("Unsupported type for min validation");
    };
  }

  /**
   * Checks if the give object has a maximum length or value.
   * @param <T> the type of object to validate
   * @param max the maximum length or value
   * @return a Validator that checks if the object meets the maximum requirement
   */
  static <T> Validator<T> max(double max){
    return e->{
      if(e instanceof Number num){
        return num.doubleValue() <= max;
      }
      else if(e instanceof String str){
        return str.length() <= max;
      }

      throw new IllegalArgumentException("Unsupported type for max validation");
    };
  }

  /**
   * Checks if the give object is within a specified range.
   * @param <T> the type of object to validate
   * @param min the minimum length or value
   * @param max the maximum length or value
   * @return a Validator that checks if the object is within the specified range
   */
  static <T> Validator<T> range(double min, double max){
    return e->{
      if(e instanceof Number num){
        return num.doubleValue() >= min && num.doubleValue() <= max;
      }
      else if(e instanceof String str){
        return str.length() >= min && str.length() <= max;
      }

      throw new IllegalArgumentException("Unsupported type for range validation");
    };
  }

  /**
   * Creates a validator that checks if the value matches the provided regular expression.
   * 
   * @param regex the regular expression to match against
   * @return a Validator that checks if the value matches the regex
   */
  static Validator<String> match(String regex){
    return e->{
      return e.matches(regex);
    };
  }

  /**
   * Creates a validator that checks if the value is in the provided set of values.
   * 
   * @param values the values to check against
   * @param <T> the type of the values
   * @return a Validator that checks if the value is in the provided set
   */
  @SafeVarargs
  static <T> Validator<T> in(T ...values){
    return e->{
      for(T value : values){
        if(!value.equals(e)){
          return false;
        }
      }

      return true;
    };
  }
}
