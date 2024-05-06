import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    private final HashMap<Long, Account> accounts = new HashMap<>();
    private static final AtomicLong nextId = new AtomicLong(1); // Начинаем ID с 1

    public long addAccount(double initialBalance) {
        long id = nextId.getAndIncrement(); // Получаем следующий уникальный ID
        Account account = new Account(id, initialBalance);
        accounts.put(id, account);
        System.out.println("Account created with ID: " + id);
        return account.getId();
    }

    public Account findAccount(long id) {
        return accounts.get(id);
    }

    public void deposit(long id, double amount) {
        Account account = findAccount(id);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found");
        }
    }

    public void withdraw(long id, double amount) {
        Account account = findAccount(id);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account not found");
        }
    }
}
