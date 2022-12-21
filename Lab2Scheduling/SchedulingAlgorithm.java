// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  private static int indexNextProcess(Vector processVector) {
    int size = processVector.size();
    int result = -1;
    int processDeadline = -1;

    for (int i = 0; i < size; i++) {
      sProcess process = (sProcess) processVector.elementAt(i);
      if (process.timeProcess != process.timeFinish && process.cputime > process.cpudone) {
        if (result < 0 || processDeadline > process.deadline) {
          processDeadline = process.deadline;
          result = i;
        }
      }
    }
    return result;
  }

  public static Results Run(int runtime, Vector processVector, Results result) {
    int i = 0;
    int comptime = 0;
    int currentProcess = 0;
    int previousProcess = 0;
    int size = processVector.size();
    int completed = 0;
    boolean switchProcess = false;
    String resultsFile = "Summary-Processes";
    result.schedulingType = "Preemptive";
    result.schedulingName = "EDF Earliest Deadline First";
    try {
      //BufferedWriter out = new BufferedWriter(new FileWriter(resultsFile));
      //OutputStream out = new FileOutputStream(resultsFile);
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
      sProcess process = (sProcess) processVector.elementAt(currentProcess);
      currentProcess = indexNextProcess(processVector);
      out.println("Process: " + currentProcess + " registered... " + "( cpu time: " + process.cpudone + "/" + process.cputime + " processing time: " + process.timeProcess + " period time: " + process.period + " deadline: " + process.deadline  +" )");

      while (comptime < runtime) {
        comptime++;

        for (int k = 0; k < size; k++) {
          sProcess sprocess = (sProcess) processVector.elementAt(i);

          if (comptime == process.deadline) {
            switchProcess = true;
            process.timeFinish = 0;
            process.deadline = process.deadline + process.period;
          } else if (currentProcess == k) {
            sprocess.cpudone++;
            sprocess.timeFinish++;
            if (process.timeFinish == process.timeProcess || process.cpudone == process.cputime) {
              switchProcess = true;
            }
          }
        }

        if (switchProcess == true) {
          previousProcess = currentProcess;
          currentProcess = indexNextProcess(processVector);
          if (previousProcess != currentProcess) {
            if (previousProcess == -1) {
              process = (sProcess) processVector.elementAt(currentProcess);
              out.println("Process: " + currentProcess + " registered... " + "( cpu time: " + process.cpudone + "/" + process.cputime + " processing time: " + process.timeProcess + " period time: " + process.period + " deadline: " + process.deadline + ")");
            } else if (currentProcess == -1) {
              out.println("There are no processes available");
            } else {
              out.println("Switched from process: " + previousProcess + " " + "( cpu time: " + process.cpudone + "/" + process.cputime + " processing time: " + process.timeProcess + " period time: " + process.period + " deadline: " + process.deadline + ")");
              process = (sProcess) processVector.elementAt(currentProcess);
              out.println("Process: " + currentProcess + " " + "( cpu time: " + process.cpudone + "/" + process.cputime + " processing time: " + process.timeProcess + " period time: " + process.period + " deadline: " + process.deadline + ")");
            }
          }
        }

        if (currentProcess != -1) {
          if (process.cpudone == process.cputime) {
            completed++;
            out.println("Process: " + currentProcess + "( completed... " + "( cpu time: " + process.cpudone + "/" + process.cputime + " processing time: " + process.timeProcess + " period time: " + process.period + " deadline: " + process.deadline + ")");

            if (process.timeProcess == process.timeFinish) {
              out.println("Process: " + currentProcess + " is unable... " + "( cpu time: " + process.cpudone + "/" + process.cputime + " processing time: " + process.timeProcess + " period time: " + process.period + " deadline: " + process.deadline + ")");
            } else if (completed == size) {
              result.compuTime = comptime;
              out.close();
              return result;
            }
          }
        }
      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
    result.compuTime = comptime;
    return result;
    }
}