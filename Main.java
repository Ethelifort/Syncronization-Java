import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

  private static final int STARTING_BALANCE = 0;
  private static final int NUM_DEPOSIT_THREADS = 5;
  private static final int NUM_WITHDRAWAL_THREADS = 9;

  public static void main(String[] args) {
    int counter = 0;

    Account account = new Account(STARTING_BALANCE);
    List<Thread> ThreadList = new ArrayList<>();

    for (int i = 0; i < NUM_DEPOSIT_THREADS; i++) {
      ThreadList.add(new TransactThread("D" + (++counter), account, false));
    }
    counter = 0;
    for (int i = 0; i < NUM_WITHDRAWAL_THREADS; i++) {
      ThreadList.add(new TransactThread("W" + (++counter), account, true));
    }
    Collections.shuffle(ThreadList);
    ThreadList.stream()
        .forEach(
            (thread) -> {
              thread.start();
            });
  }
}
