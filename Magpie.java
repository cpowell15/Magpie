import java.util.Random;

/**
 * A program to carry on conversations with a human user.
 * This is the initial version that:  
 * <ul><li>
 *       Uses indexOf to find strings
 * </li><li>
 *       Handles responding to simple words and phrases 
 * </li></ul>
 * This version uses a nested if to handle default responses.
 * @author Laurie White
 * @version April 2012
 */
public class Magpie
{
  /**
   * Get a default greeting  
   * @return a greeting
   */
  public String getGreeting()
  {
    return "Hello, let's talk.";
  }
  
  /**
   * Gives a response to a user statement
   * 
   * @param statement
   *            the user statement
   * @return a response based on the rules given
   */
  public String getResponse(String statement)
  {
    String response = "";
    if (statement.length() == 0)
    {
      response = "Being silent doesn't help anybody.";
    }
    else if (findKeyword(statement, "no") >= 0)
    {
      response = "Why so negative?";
    }
    else if (findKeyword(statement, "mother") >= 0
               || findKeyword(statement, "father") >= 0
               || findKeyword(statement, "sister") >= 0
               || findKeyword(statement, "brother") >= 0)
    {
      response = "Tell me more about your family.";
    }
    else if (findKeyword(statement, "dog") >= 0
               || findKeyword(statement, "cat") >= 0)
    {
      response = "Tell me more about your pets.";
    }
    else if (findKeyword(statement, "kiang") >= 0
               || findKeyword(statement, "landgraf") >= 0)
    {
      response = "He sounds like the best teacher that ever lived.";
    }
    else if (findKeyword(statement, "hungry") >= 0)
    {
      response = "Wow I really want food.";
    }
    else if (findKeyword(statement, "stress") >= 0
               || findKeyword(statement, "stressful") >= 0
               || findKeyword(statement, "stressed") >= 0)
    {
      response = "I'm sorry, you ought to not be that stressed.";
    }
    else if (findKeyword(statement, "casual") >= 0)
    {
      response = "Heh. Casual.";
    }
    
    else if (findKeyword(statement, "I want to", 0) >= 0)
    {
      response = transformIWantToStatement(statement);
    }
    
    else if (findKeyword(statement, "I want", 0) >= 0)
    {
      response = transformIWantStatement(statement);
    }
    
    else
    {
      // Look for a two word (you <something> me)
      // pattern
      int psn = findKeyword(statement, "you", 0);
      
      if (psn >= 0
            && findKeyword(statement, "me", psn) >= 0)
      {
        response = transformYouMeStatement(statement);
      }
      
      else
      {
        int psm = findKeyword(statement, "i", 0);
        
        if (psm >= 0
              && findKeyword(statement, "you", psm) >= 0)
        {
          response = transformIYouStatement(statement);
        }
        
        else
        {
          response = getRandomResponse();
        }
      }
    }
    return response;
  }
  
  private String transformIWantToStatement(String statement)
  {
    //  Remove the final period, if there is one
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement.length() - 1);
    }
    int psn = findKeyword (statement, "I want to", 0);
    String restOfStatement = statement.substring(psn + 9).trim();
    return "What would it mean to " + restOfStatement + "?";
  }
  
  private String transformIWantStatement(String statement)
  {
    //  Remove the final period, if there is one
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement.length() - 1);
    }
    int psn = findKeyword (statement, "I want", 0);
    String restOfStatement = statement.substring(psn + 6).trim();
    return "Would you be really happy if you had " + restOfStatement + "?";
  }
  
  private String transformYouMeStatement(String statement)
  {
    //  Remove the final period, if there is one
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement.length() - 1);
    }
    
    int psnOfYou = findKeyword (statement, "you", 0);
    int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
    
    String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
    return "What makes you think that I " + restOfStatement + " you?";
  }
  
  private String transformIYouStatement(String statement)
  {
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement.length() - 1);
    }
    
    int psnOfI = findKeyword (statement, "i", 0);
    int psnOfYou = findKeyword (statement, "you", psnOfI + 1);
    
    String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
    return "Why do you " + restOfStatement + " me?";
  }
  
  private int findKeyword(String statement, String goal, int startPos)
  {
    String phrase = statement.trim();
    // The only change to incorporate the startPos is in
    // the line below
    int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
    
    // Refinement--make sure the goal isn't part of a
    // word
    while (psn >= 0)
    {
      // Find the string of length 1 before and after
      // the word
      String before = " ", after = " ";
      if (psn > 0)
      {
        before = phrase.substring(psn - 1, psn)
          .toLowerCase();
      }
      if (psn + goal.length() < phrase.length())
      {
        after = phrase.substring(psn + goal.length(), psn + goal.length() + 1)
          .toLowerCase();
      }
      
      // If before and after aren't letters, we've
      // found the word
      if (((before.compareTo("a") < 0) || (before.compareTo("z") > 0)) // before is not a
            // letter
            && ((after.compareTo("a") < 0) || (after.compareTo("z") > 0)))
      {
        return psn;
      }
      
      // The last position didn't work, so let's find
      // the next, if there is one.
      psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
      
    }
    
    return -1;
  }
  
  private int findKeyword(String statement, String goal)
  {
    return findKeyword(statement, goal, 0);
  }
  
  private String getRandomResponse ()
 {
  Random r = new Random ();
  return randomResponses [r.nextInt(randomResponses.length)];
 }
 
 private String [] randomResponses = {"Interesting, tell me more",
   "Hmmm.",
   "Do you really think so?",
   "You don't say.",
   "Compelling.",
   "Fascinating.",
   "Why is that?",
   "Really now."
 };
 
}