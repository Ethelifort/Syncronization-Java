import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

  private long balance;
  private Lock bLock = new ReentrantLock();
  private Condition InsuffientFunds = bLock.newCondition();

  public Account(long balance) {
    this.balance = balance;
    System.out.printf("%-30s%-30s%-100s%n", "Deposit Threads", "Withdrawal Threads", "Balance");
    System.out.printf("%-30s%-30s%-100s%n", "---------------", "------------------", "-------");
  }

  public void transact(long amount) throws InterruptedException {
    bLock.lock();
    try {
      if (amount > 0) {
        balance = balance + amount;
        System.out.printf(
            "%-30s%-30s%-100s%n",
            String.format("Thread %s deposits $%d", Thread.currentThread().getName(), amount),
            "",
            String.format("(+) Balance is $%d", balance));
        InsuffientFunds.signalAll();
      } else {
        amount = -amount;
        while (balance < amount) {
          System.out.printf(
              "%-30s%-30s%-100s%n",
              "",
              String.format("Thread %s withdraws $%d", Thread.currentThread().getName(), amount),
              "[******] WITHDRAWAL BLOCKED - INSUFFICIENT FUNDS");
          InsuffientFunds.await();
        }
        balance = balance - amount;
        System.out.printf(
            "%-30s%-30s%-100s%n",
            "",
            String.format("Thread %s withdraws $%d", Thread.currentThread().getName(), amount),
            String.format("(-) Balance is $%d", balance));
      }
    } finally {
      bLock.unlock();
    }
  }
}
