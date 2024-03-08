import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FilenameFilter;

public class MarkFeedbackRRSchedulerTest {

  @Test(timeout=10000)
  public void dummy() {
    MarkRunner.incrementScore("FeedbackRRScheduler", 0);
    MarkChecks.dumpFile(new File(MarkRunner.path+"/"+"FeedbackRRScheduler.java"));
  }

  @Test(timeout=10000)
  public void testSource1() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    if(MarkChecks.checkSource(".get",  "FeedbackRRScheduler.java"))
      MarkRunner.incrementScore("FeedbackRRScheduler", 1);
    else
      MarkRunner.logFeedback("timeQuantum not read from parameters.");
  }

  @Test(timeout=10000)
  public void testSource2() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    if(MarkChecks.checkSource("new",  "FeedbackRRScheduler.java"))
      MarkRunner.incrementScore("FeedbackRRScheduler", 1);
    else
      MarkRunner.logFeedback("Ready queue not allocated.");
  }

  @Test(timeout=10000)
  public void testSource3() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    if(MarkChecks.checkSource(new String[]{"offer", "add"},  "FeedbackRRScheduler.java"))
      MarkRunner.incrementScore("FeedbackRRScheduler", 1);
    else
      MarkRunner.logFeedback("Process not added to ready queue.");
  }

  @Test(timeout=10000)
  public void testSource4() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    if(MarkChecks.checkSource(new String[]{"poll", "remove"},  "FeedbackRRScheduler.java"))
      MarkRunner.incrementScore("FeedbackRRScheduler", 1);
    else
      MarkRunner.logFeedback("Process not removed from ready queue.");
  }

  @Test(timeout=10000)
  public void testSource5() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    if(MarkChecks.checkSource("getPriority",  "FeedbackRRScheduler.java"))
      MarkRunner.incrementScore("FeedbackRRScheduler", 1);
    else
      MarkRunner.logFeedback("Priority not retrieved.");
  }

  @Test(timeout=10000)
  public void testSource6() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    MarkRunner.logCategory("FeedbackRRScheduler");
    if(MarkChecks.checkSource(new String[]{"setPriority", ".priority"},  "FeedbackRRScheduler.java"))
      MarkRunner.incrementScore("FeedbackRRScheduler", 1);
    else
      MarkRunner.logFeedback("Priority not set.");
  }

  @Test(timeout=10000)
  public void testSource7() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    if(MarkChecks.checkSource("timeQuantum",  "FeedbackRRScheduler.java"))
      MarkRunner.incrementScore("FeedbackRRScheduler", 1);
    else
      MarkRunner.logFeedback("timeQuantum not used.");
  }

  @Test(timeout=10000)
  public void testSource8() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    if(MarkChecks.checkSource("if",  "FeedbackRRScheduler.java"))
      MarkRunner.incrementScore("FeedbackRRScheduler", 1);
    else
      MarkRunner.logFeedback("Usage of time slice not checked.");
  }

  @Test(timeout=10000)
  public void testSource9() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    if(MarkChecks.checkSource("catch",  "FeedbackRRScheduler.java"))
      MarkRunner.incrementScore("FeedbackRRScheduler", 1);
    else
      MarkRunner.logFeedback("NumberFormatException not properly handled.");
  }


  @Test(timeout=30000)
  public void testOutput1() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    String sim = "scheduler=FeedbackRRScheduler\ntimeLimit=15000\ntimeQuantum=10\ninitialBurstEstimate=0\n";
    String inputs = "0 0 50\n0 15 9 5 9 5 9\n0 15 8 1 8 1 8";
    MarkChecks.makeFile("frrsim1.prp", sim);
    MarkChecks.makeFile("frrinput1.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "frrsim1.prp", "frroutput1.out", "frrinput1.in"};
    String[] expected1 = new String[]{"0: CREATE process 1",
                                      "10: TIMER process 1",
                                      "15: CREATE process 2",
                                      "15: CREATE process 3",
                                      "23: BLOCK process 3",
                                      "24: UNBLOCK process 3",
                                      "32: BLOCK process 3",
                                      "33: UNBLOCK process 3",
                                      "41: TERMINATE process 3",
                                      "48: BLOCK process 2",
                                      "53: UNBLOCK process 2",
                                      "62: BLOCK process 2",
                                      "67: UNBLOCK process 2",
                                      "76: TERMINATE process 2",
                                      "86: TIMER process 1",
                                      "96: TIMER process 1",
                                      "101: TERMINATE process 1"};
    String[] expected2 = new String[]{"0: CREATE process 1",
                                      "10: TIMER process 1",
                                      "15: CREATE process 2",
                                      "15: CREATE process 3",
                                      "25: TIMER process 1",
                                      "33: BLOCK process 3",
                                      "34: UNBLOCK process 3",
                                      "44: TIMER process 1",
                                      "52: BLOCK process 3",
                                      "53: UNBLOCK process 3",
                                      "63: TIMER process 1",
                                      "71: TERMINATE process 3",
                                      "78: BLOCK process 2",
                                      "83: TERMINATE process 1",
                                      "83: UNBLOCK process 2",
                                      "92: BLOCK process 2",
                                      "97: UNBLOCK process 2",
                                      "106: TERMINATE process 2"};
      
    String[][] expected = new String[][]{expected1, expected2};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("FeedbackRRScheduler", score);
  }
  
  @Test(timeout=30000)
  public void testOutput2() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    String sim = "scheduler=FeedbackRRScheduler\ntimeLimit=15000\ntimeQuantum=15\ninitialBurstEstimate=0\n";
    String inputs = "0 0 70\n0 5 80\n0 60 9 5 9 5 9\n";
    MarkChecks.makeFile("frrsim2.prp", sim);
    MarkChecks.makeFile("frrinput2.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "frrsim2.prp", "frroutput2.out", "frrinput2.in"};
    String[] expected1 = new String[]{"0: CREATE process 1",
                                      "5: CREATE process 2",
                                      "20: TIMER process 2",
                                      "35: TIMER process 1",
                                      "50: TIMER process 2",
                                      "60: CREATE process 3",
                                      "69: BLOCK process 3",
                                      "74: UNBLOCK process 3",
                                      "83: BLOCK process 3",
                                      "88: UNBLOCK process 3",
                                      "97: TERMINATE process 3",
                                      "112: TIMER process 1",
                                      "127: TIMER process 2",
                                      "142: TERMINATE process 1",
                                      "157: TIMER process 2",
                                      "172: TIMER process 2",
                                      "177: TERMINATE process 2"};
    String[] expected2 = new String[]{"0: CREATE process 1",
                                      "5: CREATE process 2",
                                      "20: TIMER process 1",
                                      "35: TIMER process 1",
                                      "50: TIMER process 1",
                                      "60: CREATE process 3",
                                      "69: BLOCK process 3",
                                      "74: UNBLOCK process 3",
                                      "83: BLOCK process 3",
                                      "88: TERMINATE process 1",
                                      "88: UNBLOCK process 3",
                                      "97: TERMINATE process 3",
                                      "112: TIMER process 2",
                                      "127: TIMER process 2",
                                      "142: TIMER process 2",
                                      "157: TIMER process 2",
                                      "172: TIMER process 2",
                                      "177: TERMINATE process 2"};
    String[] expected3 = new String[]{"0: CREATE process 1",
                                      "5: CREATE process 2",
                                      "20: TIMER process 2",
                                      "35: TIMER process 1",
                                      "50: TIMER process 1",
                                      "60: CREATE process 3",
                                      "69: BLOCK process 3",
                                      "74: UNBLOCK process 3",
                                      "83: BLOCK process 3",
                                      "88: UNBLOCK process 3",
                                      "97: TERMINATE process 3",
                                      "112: TIMER process 2",
                                      "127: TIMER process 2",
                                      "142: TIMER process 1",
                                      "157: TIMER process 1",
                                      "172: TERMINATE process 2",
                                      "177: TERMINATE process 1"};
    String[][] expected = new String[][]{expected1, expected2, expected3};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("FeedbackRRScheduler", score);
  }

  @Test(timeout=30000)
  public void testOutput3() {
    MarkRunner.logCategory("FeedbackRRScheduler");
    String sim = "scheduler=FeedbackRRScheduler\ntimeLimit=15000\ntimeQuantum=20\ninitialBurstEstimate=0\n";
    String inputs = "0 0 92 7 35 5 133 32 21 36 96 50 85 39 1 46 37 11 88 1 24 1 26 3 85\n0 3 3 25 44 10 84 4 7 11 83 14 53 16 28 3 77 8 26 17 30\n0 50 3 57 26 54 5 1 32 21 15\n";
    MarkChecks.makeFile("frrsim3.prp", sim);
    MarkChecks.makeFile("frrinput3.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "frrsim3.prp", "frroutput3.out", "frrinput3.in"};
    String[] expected1 = new String[]{"0: CREATE process 1",
                                     "3: CREATE process 2",
                                     "6: BLOCK process 2",
                                     "26: TIMER process 1",
                                     "31: UNBLOCK process 2",
                                     "50: CREATE process 3",
                                     "53: BLOCK process 3",
                                     "73: TIMER process 2",
                                     "93: TIMER process 1",
                                     "98: BLOCK process 2",
                                     "108: UNBLOCK process 2",
                                     "110: UNBLOCK process 3",
                                     "130: TIMER process 3",
                                     "150: TIMER process 2",
                                     "156: BLOCK process 3",
                                     "176: TIMER process 2",
                                     "196: TIMER process 1",
                                     "210: UNBLOCK process 3",
                                     "215: BLOCK process 3",
                                     "216: UNBLOCK process 3",
                                     "236: TIMER process 3",
                                     "248: BLOCK process 3",
                                     "268: TIMER process 2",
                                     "269: UNBLOCK process 3",
                                     "284: TERMINATE process 3",
                                     "297: BLOCK process 1",
                                     "304: BLOCK process 2",
                                     "304: UNBLOCK process 1",
                                     "308: UNBLOCK process 2",
                                     "328: TIMER process 1",
                                     "335: BLOCK process 2",
                                     "346: BLOCK process 1",
                                     "346: UNBLOCK process 2",
                                     "351: UNBLOCK process 1",
                                     "371: TIMER process 1",
                                     "391: TIMER process 2",
                                     "411: TIMER process 1",
                                     "431: TIMER process 2",
                                     "451: TIMER process 1",
                                     "471: TIMER process 2",
                                     "491: TIMER process 1",
                                     "509: BLOCK process 2",
                                     "523: UNBLOCK process 2",
                                     "543: TIMER process 2",
                                     "563: TIMER process 1",
                                     "583: TIMER process 2",
                                     "602: BLOCK process 1",
                                     "615: BLOCK process 2",
                                     "631: UNBLOCK process 2",
                                     "634: UNBLOCK process 1",
                                     "654: TIMER process 1",
                                     "674: TIMER process 2",
                                     "675: BLOCK process 1",
                                     "680: BLOCK process 2",
                                     "683: UNBLOCK process 2",
                                     "703: TIMER process 2",
                                     "711: UNBLOCK process 1",
                                     "731: TIMER process 1",
                                     "751: TIMER process 2",
                                     "771: TIMER process 1",
                                     "791: TIMER process 2",
                                     "811: TIMER process 1",
                                     "820: BLOCK process 2",
                                     "828: UNBLOCK process 2",
                                     "848: TIMER process 2",
                                     "868: TIMER process 1",
                                     "874: BLOCK process 2",
                                     "882: BLOCK process 1",
                                     "891: UNBLOCK process 2",
                                     "911: TIMER process 2",
                                     "921: TERMINATE process 2",
                                     "932: UNBLOCK process 1",
                                     "952: TIMER process 1",
                                     "972: TIMER process 1",
                                     "992: TIMER process 1",
                                     "1012: TIMER process 1",
                                     "1017: BLOCK process 1",
                                     "1056: UNBLOCK process 1",
                                     "1057: BLOCK process 1",
                                     "1103: UNBLOCK process 1",
                                     "1123: TIMER process 1",
                                     "1140: BLOCK process 1",
                                     "1151: UNBLOCK process 1",
                                     "1171: TIMER process 1",
                                     "1191: TIMER process 1",
                                     "1211: TIMER process 1",
                                     "1231: TIMER process 1",
                                     "1239: BLOCK process 1",
                                     "1240: UNBLOCK process 1",
                                     "1260: TIMER process 1",
                                     "1264: BLOCK process 1",
                                     "1265: UNBLOCK process 1",
                                     "1285: TIMER process 1",
                                     "1291: BLOCK process 1",
                                     "1294: UNBLOCK process 1",
                                     "1314: TIMER process 1",
                                     "1334: TIMER process 1",
                                     "1354: TIMER process 1",
                                     "1374: TIMER process 1",
                                     "1379: TERMINATE process 1"};
    String[] expected2 = new String[]{"0: CREATE process 1",
                                      "3: CREATE process 2",
                                      "6: BLOCK process 2",
                                      "26: TIMER process 1",
                                      "31: UNBLOCK process 2",
                                      "50: CREATE process 3",
                                      "53: BLOCK process 3",
                                      "73: TIMER process 2",
                                      "78: BLOCK process 2",
                                      "88: UNBLOCK process 2",
                                      "108: TIMER process 2",
                                      "110: UNBLOCK process 3",
                                      "130: TIMER process 3",
                                      "136: BLOCK process 3",
                                      "156: TIMER process 2",
                                      "176: TIMER process 2",
                                      "190: UNBLOCK process 3",
                                      "195: BLOCK process 3",
                                      "196: UNBLOCK process 3",
                                      "216: TIMER process 3",
                                      "228: BLOCK process 3",
                                      "235: BLOCK process 2",
                                      "239: UNBLOCK process 2",
                                      "246: BLOCK process 2",
                                      "249: UNBLOCK process 3",
                                      "257: UNBLOCK process 2",
                                      "264: TERMINATE process 3",
                                      "284: TIMER process 2",
                                      "304: TIMER process 2",
                                      "324: TIMER process 2",
                                      "344: TIMER process 2",
                                      "347: BLOCK process 2",
                                      "361: UNBLOCK process 2",
                                      "381: TIMER process 2",
                                      "401: TIMER process 2",
                                      "414: BLOCK process 2",
                                      "430: UNBLOCK process 2",
                                      "450: TIMER process 2",
                                      "458: BLOCK process 2",
                                      "461: UNBLOCK process 2",
                                      "481: TIMER process 2",
                                      "501: TIMER process 2",
                                      "521: TIMER process 2",
                                      "538: BLOCK process 2",
                                      "546: UNBLOCK process 2",
                                      "566: TIMER process 2",
                                      "572: BLOCK process 2",
                                      "578: BLOCK process 1",
                                      "585: UNBLOCK process 1",
                                      "589: UNBLOCK process 2",
                                      "609: TIMER process 2",
                                      "619: TERMINATE process 2",
                                      "639: TIMER process 1",
                                      "650: BLOCK process 1",
                                      "655: UNBLOCK process 1",
                                      "675: TIMER process 1",
                                      "695: TIMER process 1",
                                      "715: TIMER process 1",
                                      "735: TIMER process 1",
                                      "755: TIMER process 1",
                                      "775: TIMER process 1",
                                      "788: BLOCK process 1",
                                      "820: UNBLOCK process 1",
                                      "840: TIMER process 1",
                                      "841: BLOCK process 1",
                                      "877: UNBLOCK process 1",
                                      "897: TIMER process 1",
                                      "917: TIMER process 1",
                                      "937: TIMER process 1",
                                      "957: TIMER process 1",
                                      "973: BLOCK process 1",
                                      "1023: UNBLOCK process 1",
                                      "1043: TIMER process 1",
                                      "1063: TIMER process 1",
                                      "1083: TIMER process 1",
                                      "1103: TIMER process 1",
                                      "1108: BLOCK process 1",
                                      "1147: UNBLOCK process 1",
                                      "1148: BLOCK process 1",
                                      "1194: UNBLOCK process 1",
                                      "1214: TIMER process 1",
                                      "1231: BLOCK process 1",
                                      "1242: UNBLOCK process 1",
                                      "1262: TIMER process 1",
                                      "1282: TIMER process 1",
                                      "1302: TIMER process 1",
                                      "1322: TIMER process 1",
                                      "1330: BLOCK process 1",
                                      "1331: UNBLOCK process 1",
                                      "1351: TIMER process 1",
                                      "1355: BLOCK process 1",
                                      "1356: UNBLOCK process 1",
                                      "1376: TIMER process 1",
                                      "1382: BLOCK process 1",
                                      "1385: UNBLOCK process 1",
                                      "1405: TIMER process 1",
                                      "1425: TIMER process 1",
                                      "1445: TIMER process 1",
                                      "1465: TIMER process 1",
                                      "1470: TERMINATE process 1"};
    String[][] expected = new String[][]{expected1, expected2};
    int score = MarkChecks.checkOutput(cmd, expected);
    if (score < 10) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Trace score: "+score);
    MarkRunner.incrementScore("FeedbackRRScheduler", score);
  }  
}
