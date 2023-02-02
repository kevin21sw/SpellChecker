package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

  public static ArrayList < String > errors = new ArrayList < String > ();

  public static boolean spellCheck(String input, String[] dic) {
    boolean noErrors = true;

    if (!grammerCheck(input, input.length())) {
      noErrors = false;
    }
    if (input.charAt(input.length() - 1) == '.' || input.charAt(input.length() - 1) == '!' || input.charAt(input.length() - 1) == '?') {
      input = input.substring(0, input.length() - 1);
    }
    System.out.println(input);

    String[] splitted_Input = input.split("\\s+"); //In case of a sentence, split the words wherever there is space.

    for (String currentCheck: splitted_Input) {
      if (!isSpecial(currentCheck)) {
        if (!checkWord(currentCheck, dic)) {
          errors.add(currentCheck + " is spelt incorrectly");
          //System.out.println(currentCheck + " is spelt incorrectly");
          //String result = "Did you mean:\n";
          for (int i = 0; i < currentCheck.length() - 1; i++) {
            String swaped_Chars = swapChars(currentCheck, i, i + 1);
            if (checkWord(swaped_Chars, dic)) {
              errors.add("Did you mean: " + swaped_Chars + "\n");
              //result = result + swaped_Chars + "\n";
            }
          }
          /*
                              if(!result.equals("Did you mean:\n")){
                                  System.out.println(result);
                              }*/
          noErrors = false;
        }
      }
    }
    return noErrors;
  }

  public static boolean isSpecial(String input) {
    Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE); //Regex matches everything except single character or number.
    Matcher match = pattern.matcher(input);
    return match.find();
  }

  public static boolean checkWord(String input, String[] dic) {
    boolean valid = false;
    int length = dic.length;
    int i = 0;
    while (!valid && i < length) {
      if (input.trim().equalsIgnoreCase(dic[i].trim())) {
        valid = true;
        if (input.trim().equals("I")) {
          valid = true;
        } else if (input.trim().equals("i")) {
          valid = false;
        }
      } // Checks if the word 'I' is in the right form.
      i++;
    }
    return valid;
  }

  public static boolean grammerCheck(String input, int length) {
    boolean validGrammar = true;
    int lastCharacter = length - 1;

    char punctuation = input.charAt(lastCharacter);

    if (punctuation != '.' && punctuation != '!' && punctuation != '?') { // Condition that checks is there's a full stop at the end of the sentence.
      errors.add("No punctuation at the end of the sentence");
      //System.out.println("Missing full stop at the end of the sentences");
      validGrammar = false;
    }
    if (!Character.isUpperCase(input.charAt(0)) && !Character.isDigit(input.charAt(0))) { // Condition that checks if the sentence starts with an uppercase letter.
      errors.add("Must starts with an uppercase character or number");
      //validGrammar= false;
      //System.out.println("Must starts with an uppercase character or number");
    }
    for (String element: errors) {
      //System.out.println(element);
    }
    return validGrammar;
  }
  public static String[] readDictionary(String filepath) {

    ArrayList < String > records = new ArrayList < > ();
    try {
      Scanner scan;
      scan = new Scanner(new File(filepath));
      scan.useDelimiter("[,\n]"); // One index (word) for each new line.
      while (scan.hasNext()) {
        records.add(scan.next());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    String[] recordsArray = new String[records.size()];
    recordsArray = records.toArray(recordsArray);
    return recordsArray;
  }

  private static String swapChars(String str, int lIdx, int rIdx) {
    StringBuilder sb = new StringBuilder(str); // Take the value of string str and put it on a StringBuilder so we can manipulate it.
    char l = sb.charAt(lIdx), r = sb.charAt(rIdx); // Temporarely save the character on the left and the character on the rigt.
    sb.setCharAt(lIdx, r);
    sb.setCharAt(rIdx, l); // Swap.
    return sb.toString(); // Return the string with swapped characters.
  }

}