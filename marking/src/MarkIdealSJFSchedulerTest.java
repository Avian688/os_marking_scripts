import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FilenameFilter;

public class MarkIdealSJFSchedulerTest {

  @Test(timeout=10000)
  public void dummy() {
    MarkRunner.incrementScore("IdealSJFScheduler", 0);
    MarkChecks.dumpFile(new File(MarkRunner.path+"/"+"IdealSJFScheduler.java"));
  }

  @Test(timeout=10000)
  public void testSource1() {
    MarkRunner.logCategory("IdealSJFScheduler");
    if(MarkChecks.checkSource("getNextBurst",  "IdealSJFScheduler.java"))
      MarkRunner.incrementScore("IdealSJFScheduler", 1);
    else
      MarkRunner.logFeedback("getNextBurst not called.");
  }

  @Test(timeout=10000)
  public void testSource2() {
    MarkRunner.logCategory("IdealSJFScheduler");
    if(MarkChecks.checkSource("new",  "IdealSJFScheduler.java"))
      MarkRunner.incrementScore("IdealSJFScheduler", 1);
    else
      MarkRunner.logFeedback("Ready queue not allocated.");
  }

  @Test(timeout=10000)
  public void testSource3() {
    MarkRunner.logCategory("IdealSJFScheduler");
    if(MarkChecks.checkSource(new String[]{"offer", "add"},  "IdealSJFScheduler.java"))
      MarkRunner.incrementScore("IdealSJFScheduler", 1);
    else
      MarkRunner.logFeedback("Process not added to ready queue.");
  }

  @Test(timeout=10000)
  public void testSource4() {
    MarkRunner.logCategory("IdealSJFScheduler");
    if(MarkChecks.checkSource(new String[]{"poll", "remove"},  "IdealSJFScheduler.java"))
      MarkRunner.incrementScore("IdealSJFScheduler", 1);
    else
      MarkRunner.logFeedback("Process not removed from ready queue.");
  }

  @Test(timeout=10000)
  public void testSource5() {
    MarkRunner.logCategory("IdealSJFScheduler");
    MarkRunner.incrementScore("IdealSJFScheduler", 1);
    /*
    MarkRunner.logCategory("IdealSJFScheduler");
    if(MarkChecks.checkSource(new String[]{"setPriority", "for"},  "IdealSJFScheduler.java"))
      MarkRunner.incrementScore("IdealSJFScheduler", 1);
    else
    MarkRunner.logFeedback("Process priority not set.");*/
 }

  @Test(timeout=30000)
  public void testOutput1() {
    MarkRunner.logCategory("IdealSJFScheduler");
    String sim = "scheduler=IdealSJFScheduler\ntimeLimit=15000\ntimeQuantum=0\ninitialBurstEstimate=0\n";
    String inputs = "0 0 15\n0 5 20\n0 10 10\n";
    MarkChecks.makeFile("isjfsim1.prp", sim);
    MarkChecks.makeFile("isjfinput1.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "isjfsim1.prp", "isjfoutput1.out", "isjfinput1.in"};
    String[] expected = new String[]{"0: CREATE process 1",
				     "5: CREATE process 2",
				     "10: CREATE process 3",
				     "15: TERMINATE process 1",
				     "25: TERMINATE process 3",
				     "45: TERMINATE process 2"};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("IdealSJFScheduler", score);
  }
  
  @Test(timeout=30000)
  public void testOutput2() {
    MarkRunner.logCategory("IdealSJFScheduler");
    String sim = "scheduler=IdealSJFScheduler\ntimeLimit=15000\ntimeQuantum=0\ninitialBurstEstimate=0\n";
    String inputs = "0 0 20\n0 15 15 20 30 \n0 15 25 5 20\n0 20 25\n";
    MarkChecks.makeFile("isjfsim2.prp", sim);
    MarkChecks.makeFile("isjfinput2.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "isjfsim2.prp", "isjfoutput2.out", "isjfinput2.in"};
    String[] expected = new String[]{"0: CREATE process 1",
				     "15: CREATE process 2",
				     "15: CREATE process 3",
				     "20: TERMINATE process 1",
				     "20: CREATE process 4",
				     "35: BLOCK process 2",
				     "55: UNBLOCK process 2",
				     "60: BLOCK process 3",
				     "65: UNBLOCK process 3",
				     "85: TERMINATE process 4",
				     "105: TERMINATE process 3",
				     "135: TERMINATE process 2"};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("IdealSJFScheduler", score);
  }

  @Test(timeout=30000)
  public void testOutput3() {
    MarkRunner.logCategory("IdealSJFScheduler");
    String sim = "scheduler=IdealSJFScheduler\ntimeLimit=15000\ntimeQuantum=0\ninitialBurstEstimate=0\n";
    String inputs = "0 0 92 7 35 5 133 32 21 36 96 50 23\n0 20 85 39 1 46 37 11 88 1 24 1 26 3 85\n0 68 3 3 25 44 10 84 4 7 11 83 14\n0 53 16 28 3 77 8 26 17 30 17\n0 87 57 26 54 51 32 21 15\n";
    MarkChecks.makeFile("isjfsim3.prp", sim);
    MarkChecks.makeFile("isjfinput3.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "isjfsim3.prp", "isjfoutput3.out", "isjfinput3.in"};
    String[] expected = new String[]{"0: CREATE process 1",
				     "20: CREATE process 2",
				     "53: CREATE process 4",
				     "68: CREATE process 3",
				     "87: CREATE process 5",
				     "92: BLOCK process 1",
				     "95: BLOCK process 3",
				     "98: UNBLOCK process 3",
				     "99: UNBLOCK process 1",
				     "111: BLOCK process 4",
				     "136: BLOCK process 3",
				     "139: UNBLOCK process 4",
				     "171: BLOCK process 1",
				     "174: BLOCK process 4",
				     "176: UNBLOCK process 1",
				     "180: UNBLOCK process 3",
				     "231: BLOCK process 5",
				     "241: BLOCK process 3",
				     "251: UNBLOCK process 4",
				     "257: UNBLOCK process 5",
				     "325: UNBLOCK process 3",
				     "326: BLOCK process 2",
				     "330: BLOCK process 3",
				     "337: UNBLOCK process 3",
				     "338: BLOCK process 4",
				     "349: BLOCK process 3",
				     "364: UNBLOCK process 4",
				     "365: UNBLOCK process 2",
				     "403: BLOCK process 5",
				     "404: BLOCK process 2",
				     "421: BLOCK process 4",
				     "432: UNBLOCK process 3",
				     "450: UNBLOCK process 2",
				     "451: UNBLOCK process 4",
				     "454: UNBLOCK process 5",
				     "554: BLOCK process 1",
				     "568: TERMINATE process 3",
				     "585: TERMINATE process 4",
				     "586: UNBLOCK process 1",
				     "617: BLOCK process 5",
				     "638: BLOCK process 1",
				     "638: UNBLOCK process 5",
				     "674: UNBLOCK process 1",
				     "675: BLOCK process 2",
				     "686: UNBLOCK process 2",
				     "690: TERMINATE process 5",
				     "778: BLOCK process 2",
				     "779: UNBLOCK process 2",
				     "874: BLOCK process 1",
				     "898: BLOCK process 2",
				     "899: UNBLOCK process 2",
				     "924: UNBLOCK process 1",
				     "925: BLOCK process 2",
				     "928: UNBLOCK process 2",
				     "948: TERMINATE process 1",
				     "1033: TERMINATE process 2"};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("IdealSJFScheduler", score);
  }
}
