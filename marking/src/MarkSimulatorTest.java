import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.BufferedReader;

public class MarkSimulatorTest {

  @Test(timeout=10000)
  public void dummy() {
    MarkRunner.incrementScore("reportPresent", 0);
  }

  private static int recursiveCountFiles(String dirPath, String suffix, int count) {
    File dir = new File(dirPath);   
    File[] fileList = dir.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          return name.endsWith(suffix);
        }
      });
    count += fileList.length;

    File[] directories = new File(dirPath).listFiles(new FileFilter() {
        public boolean accept(File file) {
          return file.isDirectory();
        }
      });
    for(File d : directories) {
      count = recursiveCountFiles(dirPath+"/"+d.getName(), suffix, count);
    }
    return count;
  }  

  private static int recursiveCountDirs(String dirPath, String prefix, int count) {
    File dir = new File(dirPath);   
    File[] fileList = dir.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          System.err.println("file: "+dirPath+"/"+name);
          return dir.isDirectory() && name.startsWith(prefix);
        }
      });
    count += fileList.length;

    File[] directories = new File(dirPath).listFiles(new FileFilter() {
        public boolean accept(File file) {
          return file.isDirectory();
        }
      });
    for(File d : directories) {
      count = recursiveCountDirs(dirPath+"/"+d.getName(), prefix, count);
    }
    return count;
  }    
  
  @Test(timeout=10000)
  public void experimentsPresent() {
    MarkRunner.incrementScore("experimentsPresent", 0);
    try {
      int count = recursiveCountDirs(MarkRunner.path+"/..", "exp", 0);
      System.err.println("Experiments present: "+count);
      MarkRunner.incrementScore("experimentsPresent", count);
      assertTrue(count>0);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      assertTrue(false);
    }
  }

  @Test(timeout=10000)
  public void prpFilesPresent() {
    MarkRunner.incrementScore("prpFilesPresent", 0);
    try {
      int count = recursiveCountFiles(MarkRunner.path+"/..", ".prp", 0);
      System.err.println("Property files present: "+count);
      MarkRunner.incrementScore("prpFilesPresent", count);
      assertTrue(count>0);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test(timeout=10000)
  public void inFilesPresent() {
    MarkRunner.incrementScore("inFilesPresent", 0);
    try {
      int count = recursiveCountFiles(MarkRunner.path+"/..", ".in", 0);
      System.err.println("Input files present: "+count);
      MarkRunner.incrementScore("inFilesPresent", count);
      assertTrue(count>0);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test(timeout=10000)
  public void outFilesPresent() {
    MarkRunner.incrementScore("outFilesPresent", 0);
    try {
      int count = recursiveCountFiles(MarkRunner.path+"/..", ".out", 0);
      System.err.println("Output files present: "+count);
      MarkRunner.incrementScore("outFilesPresent", count);
      assertTrue(count>0);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      assertTrue(false);
    }
  }
  @Test(timeout=10000)
  public void archiveOk() {
    MarkRunner.logCategory("archiveOk");
    MarkRunner.logCategory(MarkRunner.path);
    try {
        
//      if(!MarkRunner.path.startsWith("./os-coursework1/src")) {
      if(!MarkRunner.path.endsWith("/os-coursework1/src")) {    // moh 
        MarkRunner.logFeedback("Directory structure of the archive does not comply with the specification. There is no os-coursework1 directory in the root of the archive.");
        MarkRunner.logFeedback("Directory structure of the archive does not comply with the specification. There is no os-coursework1 directory in the root of the archive.");
        MarkRunner.scores.put("archiveOk",new Integer(0));
        assertTrue(false);
        return;
      } 
      MarkRunner.scores.put("archiveOk",new Integer(1));
      assertTrue(true);
    } catch(Exception e) {
      MarkRunner.scores.put("archiveOk",new Integer(0));
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  private static boolean reportPresent(String dirPath) {
    File dir = new File(dirPath);   
    File[] fileList = dir.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          return name.equals("report.pdf")
            || name.equals("report.doc")
            || name.equals("report.docx")
            || name.equals("report.odf");
        }
      });
    return fileList.length != 0;
  }

  private static boolean reportPresentMisnamed(String dirPath) {
    File dir = new File(dirPath);   
    File[] fileList = dir.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          return (name.toLowerCase().endsWith(".pdf")
                  || name.toLowerCase().endsWith(".doc")
                  || name.toLowerCase().endsWith(".docx")
                  || name.toLowerCase().endsWith(".odf"))
            && !name.startsWith("~");
        }
      });

    if(fileList.length != 0) {
      MarkRunner.logFeedback("Report found in: "+fileList[0]);
    }
    return fileList.length != 0;
  }
  
  @Test(timeout=10000)
  public void hasReport() {
    try {
      if(!(reportPresent(MarkRunner.path+"/../..") ||
           reportPresent(MarkRunner.path+"/.."))) {
        MarkRunner.scores.put("reportPresent",new Integer(0));
        assertTrue(false);
        return;
      } 
      MarkRunner.scores.put("reportPresent",new Integer(1));
      assertTrue(true);
    } catch(Exception e) {
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      assertTrue(false);
    }
  }

  @Test(timeout=10000)
  public void hasMisnamedReport() {
    MarkRunner.logCategory("reportPresentMisnamed");
    try {
      if(!(reportPresentMisnamed(MarkRunner.path)
           || reportPresentMisnamed(MarkRunner.path+"/..")
           || reportPresentMisnamed(MarkRunner.path+"/../..")
           || reportPresentMisnamed(MarkRunner.path+"/../../..")
           || reportPresentMisnamed(MarkRunner.path+"/../../../.."))) {
        MarkRunner.scores.put("reportPresentMisnamed",new Integer(0));
        assertTrue(false);
        return;
      } 
      MarkRunner.scores.put("reportPresentMisnamed",new Integer(1));
      assertTrue(true);
    } catch(Exception e) {
      MarkRunner.scores.put("reportPresentMisnamed",new Integer(0));
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      assertTrue(false);
    }
  }

  @Test(timeout=10000)
  public void hasRunScript() {
    MarkRunner.logCategory("runScriptPresent");
    try {
      File dir = new File(MarkRunner.path);   
      File[] fileList = dir.listFiles(new FilenameFilter() {
          public boolean accept(File dir, String name) {
            return name.equals("run.bat")
              || name.equals("run.sh");
          }
        });
      if(fileList.length == 0) {
        MarkRunner.scores.put("runScriptPresent",new Integer(0));
        assertTrue(false);
        return;
      }
      System.err.println("Run script:");
      MarkChecks.dumpFile(fileList[0]);
      MarkRunner.scores.put("runScriptPresent",new Integer(1));
      assertTrue(true);
    } catch(Exception e) {
      MarkRunner.scores.put("runScriptPresent",new Integer(0));
      System.err.println("Test did not finish.");
      System.err.println(e.toString());
      e.printStackTrace();
      assertTrue(false);
    }
  }
}
