import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FilenameFilter;

public class MarkMetricsTest {

  @Test(timeout=10000)
  public void dummy() {
    MarkRunner.incrementScore("WaitingTime", 0);
    MarkRunner.incrementScore("TurnaroundTime", 0);
    MarkRunner.incrementScore("ResponseTime", 0);
    MarkChecks.dumpFile(new File(MarkRunner.path+"/"+"Process.java"));
  }

  @Test(timeout=30000)
  public void testOutput1() {
    String sim = "scheduler=FcfsScheduler\ntimeLimit=15000\n";
    String inputs = "0 0 15\n";
    MarkChecks.makeFile("sim1.prp", sim);
    MarkChecks.makeFile("input1.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "sim1.prp", "output1.out", "input1.in"};
    String[] expected1 = new String[]{"0: CREATE process 1"};
    MarkChecks.checkOutput(cmd, expected1);
    MarkRunner.logCategory("TurnaroundTime");
    String[] expectedT = new String[]{"15", "15"};
    int scoreT = MarkChecks.checkTabbedFile("output1.out", 7, expectedT);
    if (scoreT < expectedT.length) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Turnaround score: "+scoreT);
    MarkRunner.incrementScore("TurnaroundTime", scoreT);
    MarkRunner.logCategory("WaitingTime");
    String[] expectedW = new String[]{"0", "15"};
    int scoreW = MarkChecks.checkTabbedFile("output1.out", 8, expectedW);
    if (scoreW < expectedW.length) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Waiting score: "+scoreW);
    MarkRunner.incrementScore("WaitingTime", scoreW);
    MarkRunner.logCategory("ResponseTime");
    String[] expectedR = new String[]{"0", "0"};
    int scoreR = MarkChecks.checkTabbedFile("output1.out", 9, expectedR);
    if (scoreR < expectedR.length) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Response score: "+scoreR);
    MarkRunner.incrementScore("ResponseTime", scoreR);
  }

  @Test(timeout=30000)
  public void testOutput2() {
    String sim = "scheduler=FcfsScheduler\ntimeLimit=15000\n";
    String inputs = "0 0 15 15 10\n0 10 20\n";
    MarkChecks.makeFile("sim2.prp", sim);
    MarkChecks.makeFile("input2.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "sim2.prp", "output2.out", "input2.in"};
    String[] expected1 = new String[]{"0: CREATE process 1"};
    MarkChecks.checkOutput(cmd, expected1);
    MarkRunner.logCategory("TurnaroundTime");
    String[] expectedT = new String[]{"25", "45", "45"};
    int scoreT = MarkChecks.checkTabbedFile("output2.out", 7, expectedT);
    if (scoreT < expectedT.length) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Turnaround score: "+scoreT);
    MarkRunner.incrementScore("TurnaroundTime", scoreT);
    MarkRunner.logCategory("WaitingTime");
    String[] expectedW = new String[]{"5", "5", "45"};
    int scoreW = MarkChecks.checkTabbedFile("output2.out", 8, expectedW);
    if (scoreW < expectedW.length) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Waiting score: "+scoreW);
    MarkRunner.incrementScore("WaitingTime", scoreW);
    MarkRunner.logCategory("ResponseTime");
    String[] expectedR = new String[]{"5", "0", "0"};
    int scoreR = MarkChecks.checkTabbedFile("output2.out", 9, expectedR);
    if (scoreR < expectedR.length) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Response score: "+scoreR);
    MarkRunner.incrementScore("ResponseTime", scoreR);
  }

  @Test(timeout=30000)
  public void testOutput3() {
    String sim = "scheduler=FcfsScheduler\ntimeLimit=15000\n";
    String inputs = "0 0 15\n0 15 30\n0 10 20\n0 25 5\n0 75 10\n";
    MarkChecks.makeFile("sim3.prp", sim);
    MarkChecks.makeFile("input3.in", inputs);
    String[] cmd = new String[]{"java", "-cp", MarkRunner.path, "Simulator", "sim3.prp", "output3.out", "input3.in"};
    String[] expected1 = new String[]{"0: CREATE process 1"};
    MarkChecks.checkOutput(cmd, expected1);
    MarkRunner.logCategory("TurnaroundTime");
    String[] expectedT = new String[]{"15", "25", "50", "45", "10", "85"};
    int scoreT = MarkChecks.checkTabbedFile("output3.out", 7, expectedT);
    if (scoreT < expectedT.length) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Turnaround score: "+scoreT);
    MarkRunner.incrementScore("TurnaroundTime", scoreT);
    MarkRunner.logCategory("WaitingTime");
    String[] expectedW = new String[]{"0", "5", "20", "40", "0", "80"};
    int scoreW = MarkChecks.checkTabbedFile("output3.out", 8, expectedW);
    if (scoreW < expectedW.length) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    System.err.println("Waiting score: "+scoreW);
    MarkRunner.incrementScore("WaitingTime", scoreW);
    MarkRunner.logCategory("ResponseTime");
    String[] expectedR = new String[]{"0", "5", "20", "40", "0", "0"};
    int scoreR = MarkChecks.checkTabbedFile("output3.out", 9, expectedR);
    System.err.println("Response score: "+scoreR);
    if (scoreR < expectedR.length) {
      MarkRunner.logFeedback("for test with");
      MarkRunner.logFeedback("Simulator parameters: "+(sim.replace("\n","\\n")));
      MarkRunner.logFeedback("Inputs: "+(inputs.replace("\n","\\n")));
    }
    MarkRunner.incrementScore("ResponseTime", scoreR);
  }
}
