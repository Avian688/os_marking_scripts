import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FilenameFilter;

public class MarkRRSchedulerTest {

  @Test(timeout=10000)
  public void dummy() {
    MarkRunner.incrementScore("RRScheduler", 0);
    MarkChecks.dumpFile(new File(MarkRunner.path+"/"+"RRScheduler.java"));
  }

  @Test(timeout=10000)
  public void testSource1() {
    MarkRunner.logCategory("RRScheduler");
    if(MarkChecks.checkSource("timeQuantum",  "RRScheduler.java")) 
      MarkRunner.incrementScore("RRScheduler", 1);
    else
      MarkRunner.logFeedback("timeQuantum not used.");
  }

  @Test(timeout=10000)
  public void testSource2() {
    MarkRunner.logCategory("RRScheduler");
    if(MarkChecks.checkSource("new",  "RRScheduler.java"))
      MarkRunner.incrementScore("RRScheduler", 1);
    else
      MarkRunner.logFeedback("Ready queue not allocated.");
  }

  @Test(timeout=10000)
  public void testSource3() {
    MarkRunner.logCategory("RRScheduler");
    if(MarkChecks.checkSource(new String[]{"offer", "add"},  "RRScheduler.java"))
      MarkRunner.incrementScore("RRScheduler", 1);
    else
      MarkRunner.logFeedback("Process not added to ready queue.");
  }

  @Test(timeout=10000)
  public void testSource4() {
    MarkRunner.logCategory("RRScheduler");
    if(MarkChecks.checkSource(new String[]{"poll", "remove"},  "RRScheduler.java"))
      MarkRunner.incrementScore("RRScheduler", 1);
    else
      MarkRunner.logFeedback("Process not removed from ready queue.");
  }

  @Test(timeout=10000)
  public void testSource5() {
    MarkRunner.logCategory("RRScheduler");
    MarkRunner.logCategory("RRScheduler");
    if(MarkChecks.checkSource(".get",  "RRScheduler.java"))
      MarkRunner.incrementScore("RRScheduler", 1);
    else
      MarkRunner.logFeedback("timeQuantum not read from parameters.");
  }

  @Test(timeout=10000)
  public void testSource6() {
    MarkRunner.logCategory("RRScheduler");
    if(MarkChecks.checkSource("catch",  "RRScheduler.java"))
      MarkRunner.incrementScore("RRScheduler", 1);
    else
      MarkRunner.logFeedback("NumberFormatException not properly handled.");
  }

  @Test(timeout=30000)
  public void testOutput1() {
    MarkRunner.logCategory("RRScheduler");
    String sim = "scheduler=RRScheduler\ntimeLimit=15000\ntimeQuantum=10\ninitialBurstEstimate=0\n";
    String inputs = "0 0 15\n";
    MarkChecks.makeFile("rrsim1.prp",sim);
    MarkChecks.makeFile("rrinput1.in",inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "rrsim1.prp", "rroutput1.out", "rrinput1.in"};
    String[] expected = new String[]{"0: CREATE process 1",
				     "10: TIMER process 1",
				     "15: TERMINATE process 1"};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("RRScheduler", score);
  }
  
  @Test(timeout=30000)
  public void testOutput2() {
    MarkRunner.logCategory("RRScheduler");
    String sim = "scheduler=RRScheduler\ntimeLimit=15000\ntimeQuantum=15\ninitialBurstEstimate=0\n";
    String inputs = "0 0 20\n0 15 20\n";
    MarkChecks.makeFile("rrsim2.prp", sim);
    MarkChecks.makeFile("rrinput2.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "rrsim2.prp", "rroutput2.out", "rrinput2.in"};
    String[] expected = new String[]{"0: CREATE process 1", 
				     "15: CREATE process 2", 
				     "15: TIMER process 1", 
				     "30: TIMER process 2", 
				     "35: TERMINATE process 1", 
				     "40: TERMINATE process 2"};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("RRScheduler", score);
  }

  @Test(timeout=30000)
  public void testOutput3() {
    MarkRunner.logCategory("RRScheduler");
    String sim = "scheduler=RRScheduler\ntimeLimit=15000\ntimeQuantum=20\ninitialBurstEstimate=0\n";
    String inputs = "0 0 92 7 35 5 133 32 21 36 96 50 85 39 1 46 37 11 88 1 24 1 26 3 85\n0 3 3 25 44 10 84 4 7 11 83 14 53 16 28 3 77 8 26 17 30\n0 503 57 26 54 51 32 21 15\n";
    MarkChecks.makeFile("rrsim3.prp", sim);
    MarkChecks.makeFile("rrinput3.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "rrsim3.prp", "rroutput3.out", "rrinput3.in"};
    String[] expected = new String[]{"0: CREATE process 1", 
				     "3: CREATE process 2", 
				     "20: TIMER process 1", 
				     "23: BLOCK process 2", 
				     "43: TIMER process 1", 
				     "48: UNBLOCK process 2", 
				     "63: TIMER process 1", 
				     "83: TIMER process 2", 
				     "103: TIMER process 1", 
				     "123: TIMER process 2", 
				     "135: BLOCK process 1", 
				     "139: BLOCK process 2", 
				     "142: UNBLOCK process 1", 
				     "149: UNBLOCK process 2", 
				     "162: TIMER process 1", 
				     "182: TIMER process 2", 
				     "197: BLOCK process 1", 
				     "202: UNBLOCK process 1", 
				     "217: TIMER process 2", 
				     "237: TIMER process 1", 
				     "257: TIMER process 2", 
				     "277: TIMER process 1", 
				     "297: TIMER process 2", 
				     "317: TIMER process 1", 
				     "321: BLOCK process 2", 
				     "325: UNBLOCK process 2", 
				     "341: TIMER process 1", 
				     "348: BLOCK process 2", 
				     "359: UNBLOCK process 2", 
				     "368: TIMER process 1", 
				     "388: TIMER process 2", 
				     "408: TIMER process 1", 
				     "428: TIMER process 2", 
				     "441: BLOCK process 1", 
				     "461: TIMER process 2", 
				     "473: UNBLOCK process 1", 
				     "481: TIMER process 2", 
				     "501: TIMER process 1", 
				     "503: CREATE process 3", 
				     "504: BLOCK process 2", 
				     "505: BLOCK process 1", 
				     "518: UNBLOCK process 2", 
				     "525: TIMER process 3", 
				     "541: UNBLOCK process 1", 
				     "545: TIMER process 2", 
				     "565: TIMER process 3", 
				     "585: TIMER process 1", 
				     "605: TIMER process 2", 
				     "622: BLOCK process 3", 
				     "642: TIMER process 1", 
				     "648: UNBLOCK process 3", 
				     "655: BLOCK process 2", 
				     "671: UNBLOCK process 2", 
				     "675: TIMER process 1", 
				     "695: TIMER process 3", 
				     "715: TIMER process 2", 
				     "735: TIMER process 1", 
				     "755: TIMER process 3", 
				     "763: BLOCK process 2", 
				     "766: UNBLOCK process 2", 
				     "779: BLOCK process 1", 
				     "793: BLOCK process 3", 
				     "813: TIMER process 2", 
				     "829: UNBLOCK process 1", 
				     "833: TIMER process 2", 
				     "844: UNBLOCK process 3", 
				     "853: TIMER process 1", 
				     "873: TIMER process 2", 
				     "893: TIMER process 3", 
				     "913: TIMER process 1", 
				     "930: BLOCK process 2", 
				     "938: UNBLOCK process 2", 
				     "942: BLOCK process 3", 
				     "962: TIMER process 1", 
				     "963: UNBLOCK process 3", 
				     "982: TIMER process 2", 
				     "1002: TIMER process 1", 
				     "1017: TERMINATE process 3", 
				     "1023: BLOCK process 2", 
				     "1028: BLOCK process 1", 
				     "1040: UNBLOCK process 2", 
				     "1060: TIMER process 2", 
				     "1067: UNBLOCK process 1", 
				     "1070: TERMINATE process 2", 
				     "1071: BLOCK process 1", 
				     "1117: UNBLOCK process 1", 
				     "1137: TIMER process 1", 
				     "1154: BLOCK process 1", 
				     "1165: UNBLOCK process 1", 
				     "1185: TIMER process 1", 
				     "1205: TIMER process 1", 
				     "1225: TIMER process 1", 
				     "1245: TIMER process 1", 
				     "1253: BLOCK process 1", 
				     "1254: UNBLOCK process 1", 
				     "1274: TIMER process 1", 
				     "1278: BLOCK process 1", 
				     "1279: UNBLOCK process 1", 
				     "1299: TIMER process 1", 
				     "1305: BLOCK process 1", 
				     "1308: UNBLOCK process 1", 
				     "1328: TIMER process 1", 
				     "1348: TIMER process 1", 
				     "1368: TIMER process 1", 
				     "1388: TIMER process 1", 
				     "1393: TERMINATE process 1"};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("RRScheduler", score);
  }  
}
