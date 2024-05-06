public class Account {
    private final long id;
    private double balance;
    private final User owner;

    public Account(long id, double balance, User owner) {
        this.id = id;
        this.balance = balance;
        this.owner = owner;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Deposit amount must be positive");
        }
    }

    public void withdraw(double amount) {
        if (amount < 0) {
            System.out.println("Withdraw amount must be positive");
            return;
        }
        if (balance - amount < 0) {
            System.out.println("Insufficient funds");
            return;
        }

        balance -= amount;
        System.out.println("withdrawn: " + amount);
    }

    public double getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }
}
