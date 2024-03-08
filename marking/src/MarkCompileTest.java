import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class MarkCompileTest {

  public static String[] filesPresent = null;
  
  @Test(timeout=10000)
  public void testAllFilesPresent() {
    MarkRunner.incrementScore("javaFilesPresent", 0);
    MarkRunner.logCategory("javaFilesPresent");
    try {
      if(filesPresent != null) {
        File dir = new File(MarkRunner.path);   
        File[] fileList = dir.listFiles(new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	      return name.endsWith(".java");
	    }
	  });
	boolean ok = true;
	for(String s : filesPresent) {
	  boolean found = false;
          for(File file : fileList) {
	    if(file.getName().equals(s)) {
	      found = true;
	      break;
	    }
	  }
	  if(!found) {
	    ok = false;
            MarkRunner.logFeedback("Missing file: "+s);
	  }
        }
        if(ok) {
          MarkRunner.incrementScore("javaFilesPresent", 1);
	} 
	assertTrue(ok);
      }
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      assertTrue(false);
    }
  }

  @Test(timeout=60000)
  public void testCompile() {
    MarkRunner.incrementScore("compiles", 0);
    MarkRunner.logCategory("compiles");
    try {
      if(filesPresent == null) {
        assertTrue(true);
	return;
      }
      String[] cmd = new String[filesPresent.length+1];
      cmd[0] = "javac";
      System.err.print("Compile command: "+cmd[0]);
      int i=1;
      for(String s : filesPresent) {
	cmd[i++] = MarkRunner.path+"/"+s;
	System.err.print(" "+cmd[i-1]);
      }
      System.err.println();
      Process proc = Runtime.getRuntime().exec(cmd);

      BufferedReader stdInput = new BufferedReader(
	new InputStreamReader(proc.getInputStream()));

      BufferedReader stdError = new BufferedReader(
	new InputStreamReader(proc.getErrorStream()));

      String s = null;
      while ((s = stdInput.readLine()) != null) {
	System.err.println(s);
      }

      while ((s = stdError.readLine()) != null) {
        MarkRunner.logFeedback(s);
      }
       
      int exitCode = proc.waitFor();
      System.err.println("exit code: "+exitCode);
      if(exitCode == 0) {
          MarkRunner.incrementScore("compiles", 1);
      }
      assertEquals(0, exitCode);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      assertTrue(false);
    }
  }
}
