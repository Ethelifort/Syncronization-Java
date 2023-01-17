import java.util.Random;

public class TransactThread extends Thread {
  private static Random random = new Random();
  private Account account;
  private boolean role; // true - debit, false - credit

  public TransactThread(String name, Account account, boolean role) {
    super(name);
    this.account = account;
    this.role = role;
  }

  @Override
  public void run() {

    while (true) {
      try {
        if (role) {
          account.transact(-random.nextInt(50));
          Thread.yield();
        } else {
          account.transact(random.nextInt(250));
          Thread.sleep(random.nextInt(250));
        }
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }
}
