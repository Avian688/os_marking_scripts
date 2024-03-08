
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.Class;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarkRunner {

    private static final Map<String, Class> testSuites = initTestSuites();
    public static final String CATEGORY = "CATEGORY: ";
    public static final String FEEDBACK = "FEEDBACK: ";

    public static PrintWriter writer; // moh
    public static PrintWriter writerCsv; // moh

    public static String path = "";
    public static HashMap<String, Integer> scores
            = new HashMap<String, Integer>();

    public static void logCategory(String c) {
        writer.println();             // moh
        writer.println(CATEGORY + c);  // moh
        System.err.println(CATEGORY + c);
    }

    public static void logFeedback(String f) {
        System.err.println(FEEDBACK + f);
        writer.println(FEEDBACK + f); // moh

    }

    public static void incrementScore(String cat, int score) {
        System.err.println("increment score: " + score);
        if (!scores.containsKey(cat)) {
            scores.put(cat, new Integer(0));
        }
        scores.put(cat, new Integer(scores.get(cat).intValue() + score));
    }

    private static Map<String, Class> initTestSuites() {
        Map<String, Class> result = new HashMap<String, Class>();
        result.put("Simulator.java", MarkSimulatorTestSuite.class);
        return Collections.unmodifiableMap(result);
    }

    private static Class detectTestSuite(String dirPath) {
        File dir = new File(dirPath);
        if (dir == null) {
            return null;
        }
        File[] fileList = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        if (fileList == null) {
            return null;
        }
        for (File file : fileList) {
            String f = file.getName();
            Class testSuite = testSuites.get(f);
            if (testSuite != null) {
                return testSuite;
            }
        }
        return null;
    }

    private static Class recursiveDetectTestSuite(String dirPath) {
        Class testSuite = detectTestSuite(dirPath);
        if (testSuite != null) {
            path = dirPath;
            return testSuite;
        }

        File[] directories = new File(dirPath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        for (File dir : directories) {
            testSuite = recursiveDetectTestSuite(dirPath + "/" + dir.getName());
            if (testSuite != null) {
                return testSuite;
            } else {
                if (dir.getName().equals("src")) {
                    path = dirPath + "/" + dir.getName();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String txtFileName = args[1];     // moh
        try {
            writer = new PrintWriter("../results/" + txtFileName + ".txt", "UTF-8");   // moh
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
        }

        writer.println("Operating Systems Coursework 1 Feedback for " + txtFileName);  // moh
        writer.println("============================================== ");             // moh

        try {
            String csvName = "../results/" + txtFileName + ".csv";   // moh
            writerCsv = new PrintWriter(csvName);                 // moh
        } catch (IOException ex) { }
        
        
        StringBuilder sb = new StringBuilder();
        sb.append(" studentId");             // moh
        sb.append(',');                        // moh

        
        
        
        try {
            if (args.length != 2) {
                System.err.println("No input project base directory provided.");
                System.exit(1);
            }
            Class testSuite = recursiveDetectTestSuite(args[0]);
            System.err.println(path);
            if (testSuite == null) {
                System.err.println("Unknown project");
                System.err.println("Using src directory '" + path + "'");
                testSuite = MarkSimulatorTestSuite.class;
//	System.exit(1);
            }

            Result result = JUnitCore.runClasses(testSuite);
            writer.println();
            System.err.print("submission");
            for (String k : scores.keySet()) {
                System.err.print("\t" + k);
                writer.print("\t" + k);   //moh 
                sb.append(k);             // moh
                sb.append(',');           // moh
            }

            sb.append('\n');   // moh
            sb.append(txtFileName);
            sb.append(',');                        // moh

            writer.println();  // moh
            System.err.println();
            for (String k : scores.keySet()) {
                System.out.print("\t" + scores.get(k));
                System.err.print("\t" + scores.get(k));

                writer.print("\t" + scores.get(k));    // moh
                sb.append(scores.get(k));              // moh
                sb.append(',');                        // moh

            }
            System.err.println();
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
            System.exit(1);
        }
        writerCsv.write(sb.toString());   // moh
        writerCsv.close();                // moh
        writer.close();                   // moh

    }

}
