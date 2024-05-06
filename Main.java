import java.util.Scanner;

public class Main {
    private final Bank bank = new Bank();
    private final Scanner scanner = new Scanner(System.in);
    private long currentAccountId;

    public void start() {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1 - Открыть новый счет");
            System.out.println("2 - Внести средства");
            System.out.println("3 - Снять средства");
            System.out.println("4 - Проверить баланс");
            System.out.println("0 - Выход");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    showBalance();
                    break;
                case 0:
                    System.out.println("Выход из программы");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите один из предложенных вариантов.");
            }
        }
    }

    private void createAccount() {
        currentAccountId = bank.addAccount(0);
    }

    private int readAmount() {
        while (!scanner.hasNextInt()) {
            scanner.next(); // Потребляем некорректный ввод
            System.out.println("Пожалуйста, введите число:");
        }
        return scanner.nextInt();
    }

    private void deposit() {
        System.out.println("Введите сумму:");
        int amount = readAmount();
        bank.deposit(currentAccountId, amount);
    }

    private void withdraw() {
        System.out.println("Введите сумму:");
        int amount = readAmount();
        bank.withdraw(currentAccountId, amount);
    }


    private void showBalance() {
        System.out.println("Ваш баланс: " + bank.findAccount(currentAccountId).getBalance());
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
