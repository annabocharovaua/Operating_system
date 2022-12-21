public class sProcess {
  public int cputime;
  public int cpudone;

  //new class variables for EDF algorithm
  public int deadline;
  public int period;
  public int timeFinish;
  public int timeProcess;

  public sProcess (int cputime, int cpudone, int deadline, int period, int timeFinish, int timeProcess) {
    this.cputime = cputime;
    this.cpudone = cpudone;
    this.period = period;
    this.deadline = deadline;
    this.timeProcess = timeProcess;
    this.timeFinish = timeFinish;
  }
}