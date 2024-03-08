import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.FileReader;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.LinkedList;
import java.util.List;

public class MarkChecks {
  
  public static boolean checkSource(String str, String filename) {
    boolean found = false;
    try {
      File file = new File(MarkRunner.path+"/"+filename);

      BufferedReader source = new BufferedReader(
        new InputStreamReader(new FileInputStream(file)));
      
      String s = null;
      while ((s = source.readLine()) != null) {
        if(s.contains(str)) {
          found = true;
          break;
        }
      }

//      assertTrue(found);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
//      assertTrue(false);
    }
    return found;
  }

  public static boolean checkSource(String[] str, String filename) {
    boolean found = false;
    try {
      File file = new File(MarkRunner.path+"/"+filename);

      BufferedReader source = new BufferedReader(
        new InputStreamReader(new FileInputStream(file)));

      String s = null;
      while ((s = source.readLine()) != null) {
        for(String sstr : str) {
          if(s.contains(sstr)) {
            found = true;
            break;
          }
        }
        if (found)
          break;
      }

//      assertTrue(found);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
//      assertTrue(false);
    }
    return found;
  }

  public static int checkOutput(String[] cmd, String[][] expected) {
    int found = 0;
    try {
      Process proc = Runtime.getRuntime().exec(cmd);

      BufferedReader stdError = new BufferedReader(
        new InputStreamReader(proc.getErrorStream()));

      String s = null;
      List<String> ss = new LinkedList<String>();
      while ((s = stdError.readLine()) != null) {
        ss.add(s);
        System.err.println(s);
      }

      int which = 0;
      for (int i=0; i<expected.length; i++) {
        int score = 0;
        for (String se : ss) {
          for (String e : expected[i]) {
            if (se.equals(e))
              score++;
          }
        }        
        if (score > found) {
          found = score;
          which = i;
        }
      }
      
      int exitCode = proc.waitFor();

      if(exitCode != 0) {
        System.err.println("Error code: "+exitCode);
        MarkRunner.logFeedback("Program crashed:");
        for (String e : ss) 
          MarkRunner.logFeedback(e);
        found = 0;
      } else {
        if (found < expected[which].length) {
          for (String e : ss) 
            MarkRunner.logFeedback("You: '"+e+"'");
          for (String e : expected[which]) 
            MarkRunner.logFeedback("Expected: '"+e+"'");
        }
        found = (10*found)/expected[which].length;
      }
      //assertTrue(found > 0);
      //assertEquals(0, exitCode);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      //assertTrue(false);
      found = 0;
    }
    return found;
  }
  
  public static int checkOutput(String[] cmd, String[] expected) {
    int found = 0;
    try {
      Process proc = Runtime.getRuntime().exec(cmd);

      BufferedReader stdError = new BufferedReader(
        new InputStreamReader(proc.getErrorStream()));

      String s = null;
      List<String> ss = new LinkedList<String>();
      while ((s = stdError.readLine()) != null) {
        ss.add(s);
        System.err.println(s);
        for (String e : expected) {
          if (s.equals(e))
            found++;
        }
      }
      
      int exitCode = proc.waitFor();

      if(exitCode != 0) {
        System.err.println("Error code: "+exitCode);
        MarkRunner.logFeedback("Program crashed:");
        for (String e : ss) 
          MarkRunner.logFeedback(e);
        found = 0;
      } else {
        if (found < expected.length) {
          for (String e : ss) 
            MarkRunner.logFeedback("You: '"+e+"'");
          for (String e : expected) 
            MarkRunner.logFeedback("Expected: '"+e+"'");
        }
        found = (10*found)/expected.length;
      }
      //assertTrue(found > 0);
      //assertEquals(0, exitCode);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      //assertTrue(false);
      found = 0;
    }
    return found;
  }

  public static int checkTabbedFile(String fileName, int column, String[] expected) {
    int found = 0;
    try {
      FileReader reader = new FileReader(new File(fileName));
      BufferedReader bufferedReader = new BufferedReader(reader);

      String s = null;
      int line = 0;
      while ((s = bufferedReader.readLine()) != null) {
        if (line >= expected.length+1)
          break;

        if (line > 0) { // skip first line
          String[] tokens = s.split("\\t");
          for (int i=0; i<tokens.length; i++) {
            if (column == i) {
              System.err.println(tokens[i]+" == "+expected[line-1]);
              if (tokens[i].equals(expected[line-1]))
                found++;
              else
                MarkRunner.logFeedback("You: '"+tokens[i]+"', expected: '"+expected[line-1]+"'");
            }
          }
        }
        line++;
      }

      reader.close();
      //assertTrue(found > 0);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      //assertTrue(false);
      found = 0;
    } 
    return found;
  }
  
  public static void makeFile(String fileName, String text) {
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
      out.write(text);
      out.flush();
      out.close();   
    } catch (IOException e) {
      System.err.println("Problem writing file");
      System.err.println(e.toString());
      e.printStackTrace();
      //assertTrue(false);
    }
  }

  public static void dumpFile(File f) {
    try {
      BufferedReader fileReader = new BufferedReader(new FileReader(f));
      String line;
      while((line = fileReader.readLine()) != null)
      {
        System.err.println(line);
      }
      fileReader.close();
    } catch (Exception e) {
      System.err.println(e);
    }
  }
  
}
