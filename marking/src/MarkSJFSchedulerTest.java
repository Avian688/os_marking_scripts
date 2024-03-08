import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FilenameFilter;

public class MarkSJFSchedulerTest {

  @Test(timeout=10000)
  public void dummy() {
    MarkRunner.incrementScore("SJFScheduler", 0);
    MarkChecks.dumpFile(new File(MarkRunner.path+"/"+"SJFScheduler.java"));
  }

  @Test(timeout=10000)
  public void testSource1() {
    MarkRunner.logCategory("SJFScheduler");
    if(MarkChecks.checkSource("getRecentBurst",  "SJFScheduler.java"))
      MarkRunner.incrementScore("SJFScheduler", 1);
    else
      MarkRunner.logFeedback("getRecentBurst not called.");
  }

  @Test(timeout=10000)
  public void testSource2() {
    MarkRunner.logCategory("SJFScheduler");
    if(MarkChecks.checkSource("new",  "SJFScheduler.java"))
      MarkRunner.incrementScore("SJFScheduler", 1);
    else
      MarkRunner.logFeedback("Ready queue not allocated.");
  }

  @Test(timeout=10000)
  public void testSource3() {
    MarkRunner.logCategory("SJFScheduler");
    if(MarkChecks.checkSource(new String[]{"offer", "add"},  "SJFScheduler.java"))
      MarkRunner.incrementScore("SJFScheduler", 1);
    else
      MarkRunner.logFeedback("Process not added to ready queue.");
  }

  @Test(timeout=10000)
  public void testSource4() {
    MarkRunner.logCategory("SJFScheduler");
    if(MarkChecks.checkSource(new String[]{"poll", "remove"},  "SJFScheduler.java"))
      MarkRunner.incrementScore("SJFScheduler", 1);
    else
      MarkRunner.logFeedback("Process not removed from ready queue.");
  }

  @Test(timeout=10000)
  public void testSource5() {
    MarkRunner.logCategory("SJFScheduler");
    if(MarkChecks.checkSource(".get",  "SJFScheduler.java"))
      MarkRunner.incrementScore("SJFScheduler", 1);
    else
      MarkRunner.logFeedback("initialBurstEstimate and alphaBurstEstimate not read from parameters.");
  }

  @Test(timeout=10000)
  public void testSource6() {
    MarkRunner.logCategory("SJFScheduler");
    if(MarkChecks.checkSource("NumberFormatException",  "SJFScheduler.java"))
      MarkRunner.incrementScore("SJFScheduler", 1);
    else
      MarkRunner.logFeedback("NumberFormatException not properly handled.");
  }

  @Test(timeout=10000)
  public void testSource7() {
    MarkRunner.logCategory("SJFScheduler");
    if(MarkChecks.checkSource("initialBurstEstimate",  "SJFScheduler.java"))
      MarkRunner.incrementScore("SJFScheduler", 1);
    else
      MarkRunner.logFeedback("initialBurstEstimate not used.");
  }

  @Test(timeout=10000)
  public void testSource8() {
    MarkRunner.logCategory("SJFScheduler");
    if(MarkChecks.checkSource("alphaBurstEstimate",  "SJFScheduler.java"))
      MarkRunner.incrementScore("SJFScheduler", 1);
    else
      MarkRunner.logFeedback("alphaBurstEstimate not used.");
  }

  @Test(timeout=30000)
  public void testOutput1() {
    MarkRunner.logCategory("SJFScheduler");
    String sim = "scheduler=SJFScheduler\ntimeLimit=15000\ntimeQuantum=0\ninitialBurstEstimate=10\nalphaBurstEstimate=0.5\n";
    String inputs = "0 0 20\n0 15 20\n";
    MarkChecks.makeFile("sjfsim1.prp", sim);
    MarkChecks.makeFile("sjfinput1.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "sjfsim1.prp", "sjfoutput1.out", "sjfinput1.in"};
    String[] expected = new String[]{"0: CREATE process 1",
                                     "15: CREATE process 2",
                                     "20: TERMINATE process 1",
                                     "40: TERMINATE process 2"};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("SJFScheduler", score);
  }
  
  @Test(timeout=30000)
  public void testOutput2() {
    MarkRunner.logCategory("SJFScheduler");
    String sim = "scheduler=SJFScheduler\ntimeLimit=15000\ntimeQuantum=0\ninitialBurstEstimate=8\nalphaBurstEstimate=0.7";
    String inputs = "0 0 10 5 10 5 10 5 10 5 10\n0 5 20 5 20 5 20 5 20 5 20\n0 10 30 5 30 5 30 5 30 5 30\n";
    MarkChecks.makeFile("sjfsim2.prp", sim);
    MarkChecks.makeFile("sjfinput2.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "sjfsim2.prp", "sjfoutput2.out", "sjfinput2.in"};
    String[] expected1 = new String[]{"0: CREATE process 1",
                                     "5: CREATE process 2",
                                     "10: BLOCK process 1",
                                     "10: CREATE process 3",
                                     "15: UNBLOCK process 1",
                                     "30: BLOCK process 2",
                                     "35: UNBLOCK process 2",
                                     "60: BLOCK process 3",
                                     "65: UNBLOCK process 3",
                                     "70: BLOCK process 1",
                                     "75: UNBLOCK process 1",
                                     "100: BLOCK process 3",
                                     "105: UNBLOCK process 3",
                                     "110: BLOCK process 1",
                                     "115: UNBLOCK process 1",
                                     "130: BLOCK process 2",
                                     "135: UNBLOCK process 2",
                                     "140: BLOCK process 1",
                                     "145: UNBLOCK process 1",
                                     "160: BLOCK process 2",
                                     "165: UNBLOCK process 2",
                                     "170: TERMINATE process 1",
                                     "190: BLOCK process 2",
                                     "195: UNBLOCK process 2",
                                     "220: BLOCK process 3",
                                     "225: UNBLOCK process 3",
                                     "240: TERMINATE process 2",
                                     "270: BLOCK process 3",
                                     "275: UNBLOCK process 3",
                                     "305: TERMINATE process 3"};
    String[] expected2 = new String[]{"0: CREATE process 1",
                                      "5: CREATE process 2",
                                      "10: BLOCK process 1",
                                      "10: CREATE process 3",
                                      "15: UNBLOCK process 1",
                                      "30: BLOCK process 2",
                                      "35: UNBLOCK process 2",
                                      "60: BLOCK process 3",
                                      "65: UNBLOCK process 3",
                                      "80: BLOCK process 2",
                                      "85: UNBLOCK process 2",
                                      "110: BLOCK process 3",
                                      "115: UNBLOCK process 3",
                                      "120: BLOCK process 1",
                                      "125: UNBLOCK process 1",
                                      "140: BLOCK process 2",
                                      "145: UNBLOCK process 2",
                                      "150: BLOCK process 1",
                                      "155: UNBLOCK process 1",
                                      "170: BLOCK process 2",
                                      "175: UNBLOCK process 2",
                                      "180: BLOCK process 1",
                                      "185: UNBLOCK process 1",
                                      "200: TERMINATE process 2",
                                      "210: TERMINATE process 1",
                                      "240: BLOCK process 3",
                                      "245: UNBLOCK process 3",
                                      "275: BLOCK process 3",
                                      "280: UNBLOCK process 3",
                                      "310: TERMINATE process 3"};
    String[] expected3 = new String[]{"0: CREATE process 1",
                                      "5: CREATE process 2",
                                      "10: BLOCK process 1",
                                      "10: CREATE process 3",
                                      "15: UNBLOCK process 1",
                                      "30: BLOCK process 2",
                                      "35: UNBLOCK process 2",
                                      "60: BLOCK process 3",
                                      "65: UNBLOCK process 3",
                                      "70: BLOCK process 1",
                                      "75: UNBLOCK process 1",
                                      "90: BLOCK process 2",
                                      "95: UNBLOCK process 2",
                                      "100: BLOCK process 1",
                                      "105: UNBLOCK process 1",
                                      "120: BLOCK process 2",
                                      "125: UNBLOCK process 2",
                                      "130: BLOCK process 1",
                                      "135: UNBLOCK process 1",
                                      "150: BLOCK process 2",
                                      "155: UNBLOCK process 2",
                                      "160: TERMINATE process 1",
                                      "180: TERMINATE process 2",
                                      "210: BLOCK process 3",
                                      "215: UNBLOCK process 3",
                                      "245: BLOCK process 3",
                                      "250: UNBLOCK process 3",
                                      "280: BLOCK process 3",
                                      "285: UNBLOCK process 3",
                                      "315: TERMINATE process 3"};
    String[][] expected = new String[][]{expected1, expected2, expected3};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("SJFScheduler", score);
  }

  @Test(timeout=30000)
  public void testOutput3() {
    MarkRunner.logCategory("SJFScheduler");
    String sim = "scheduler=SJFScheduler\ntimeLimit=15000\ntimeQuantum=0\ninitialBurstEstimate=20\nalphaBurstEstimate=0.4";
    String inputs = "0 0 92 7 35 5 133 32 21 36 96 50 23\n0 20 85 39 1 46 37 11 88 1 24 1 26 3 85\n0 68 3 3 25 44 10 84 4 7 11 83 14\n0 53 16 28 3 77 8 26 17 30 17\n0 87 57 26 54 51 32 21 15\n";
    MarkChecks.makeFile("sjfsim3.prp", sim);
    MarkChecks.makeFile("sjfinput3.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "sjfsim3.prp", "sjfoutput3.out", "sjfinput3.in"};
    String[] expected1 = new String[]{"0: CREATE process 1",
				     "20: CREATE process 2",
				     "53: CREATE process 4",
				     "68: CREATE process 3",
				     "87: CREATE process 5",
				     "92: BLOCK process 1",
				     "99: UNBLOCK process 1",
				     "177: BLOCK process 2",
				     "216: UNBLOCK process 2",
				     "234: BLOCK process 5",
				     "250: BLOCK process 4",
				     "253: BLOCK process 3",
				     "254: BLOCK process 2",
				     "256: UNBLOCK process 3",
				     "260: UNBLOCK process 5",
				     "278: UNBLOCK process 4",
				     "289: BLOCK process 1",
				     "294: UNBLOCK process 1",
				     "300: UNBLOCK process 2",
				     "314: BLOCK process 3",
				     "317: BLOCK process 4",
				     "354: BLOCK process 2",
				     "358: UNBLOCK process 3",
				     "365: UNBLOCK process 2",
				     "394: UNBLOCK process 4",
				     "408: BLOCK process 5",
				     "418: BLOCK process 3",
				     "426: BLOCK process 4",
				     "452: UNBLOCK process 4",
				     "459: UNBLOCK process 5",
				     "502: UNBLOCK process 3",
				     "514: BLOCK process 2",
				     "515: UNBLOCK process 2",
				     "531: BLOCK process 4",
				     "535: BLOCK process 3",
				     "542: UNBLOCK process 3",
				     "561: UNBLOCK process 4",
				     "668: BLOCK process 1",
				     "685: TERMINATE process 4",
				     "696: BLOCK process 3",
				     "700: UNBLOCK process 1",
				     "728: BLOCK process 5",
				     "749: BLOCK process 1",
				     "749: UNBLOCK process 5",
				     "773: BLOCK process 2",
				     "774: UNBLOCK process 2",
				     "779: UNBLOCK process 3",
				     "785: UNBLOCK process 1",
				     "788: TERMINATE process 5",
				     "802: TERMINATE process 3",
				     "898: BLOCK process 1",
				     "924: BLOCK process 2",
				     "927: UNBLOCK process 2",
				     "948: UNBLOCK process 1",
				     "1012: TERMINATE process 2",
				     "1035: TERMINATE process 1"};
    String[] expected2 = new String[]{"0: CREATE process 1",
                                      "20: CREATE process 2",
                                      "53: CREATE process 4",
                                      "68: CREATE process 3",
                                      "87: CREATE process 5",
                                      "92: BLOCK process 1",
                                      "99: UNBLOCK process 1",
                                      "177: BLOCK process 2",
                                      "216: UNBLOCK process 2",
                                      "234: BLOCK process 5",
                                      "250: BLOCK process 4",
                                      "253: BLOCK process 3",
                                      "254: BLOCK process 2",
                                      "256: UNBLOCK process 3",
                                      "260: UNBLOCK process 5",
                                      "278: UNBLOCK process 4",
                                      "289: BLOCK process 1",
                                      "294: UNBLOCK process 1",
                                      "300: UNBLOCK process 2",
                                      "314: BLOCK process 3",
                                      "317: BLOCK process 4",
                                      "354: BLOCK process 2",
                                      "358: UNBLOCK process 3",
                                      "365: UNBLOCK process 2",
                                      "394: UNBLOCK process 4",
                                      "408: BLOCK process 5",
                                      "416: BLOCK process 4",
                                      "426: BLOCK process 3",
                                      "442: UNBLOCK process 4",
                                      "459: UNBLOCK process 5",
                                      "510: UNBLOCK process 3",
                                      "514: BLOCK process 2",
                                      "515: UNBLOCK process 2",
                                      "531: BLOCK process 4",
                                      "535: BLOCK process 3",
                                      "542: UNBLOCK process 3",
                                      "561: UNBLOCK process 4"/*,
                                      "668: BLOCK process 1",
                                      "679: BLOCK process 3",
                                      "696: TERMINATE process 4",
                                      "700: UNBLOCK process 1",
                                      "728: BLOCK process 5",
                                      "749: UNBLOCK process 5",
                                      "752: BLOCK process 2",
                                      "753: UNBLOCK process 2",
                                      "762: UNBLOCK process 3",
                                      "767: TERMINATE process 5",
                                      "781: TERMINATE process 3",
                                      "807: BLOCK process 2",
                                      "810: UNBLOCK process 2",
                                      "828: BLOCK process 1",
                                      "864: UNBLOCK process 1",
                                      "913: TERMINATE process 2",
                                      "1009: BLOCK process 1",
                                      "1059: UNBLOCK process 1",
                                      "1082: TERMINATE process 1"*/};
    String[][] expected = new String[][]{expected1, expected2};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("SJFScheduler", score);
  }
}
