import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.BeforeClass;

@RunWith(Suite.class)

@Suite.SuiteClasses({ 
      MarkCompileTest.class,
      MarkSimulatorTest.class,
      MarkRRSchedulerTest.class,
      MarkIdealSJFSchedulerTest.class,
      MarkSJFSchedulerTest.class,
      MarkFeedbackRRSchedulerTest.class,
      MarkMetricsTest.class	
})

public class MarkSimulatorTestSuite {

  @BeforeClass
  public static void setUpClass() {
    MarkCompileTest.filesPresent = new String[]{"AbstractScheduler.java", "FcfsScheduler.java", "Process.java", "BurstProcess.java", "FeedbackRRScheduler.java", "ProcessModel.java", "Event.java", "IdealSJFScheduler.java", "RRScheduler.java", "EventProcessor.java", "InputGenerator.java", "SJFScheduler.java", "ExponentialGenerator.java", "InputReader.java", "Simulator.java"};
  };
}
