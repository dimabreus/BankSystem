import java.util.Scanner;
import java.util.UUID;

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
            System.out.println("5 - Логин");
            System.out.println("0 - Выход");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> showBalance();
                case 5 -> login();
                case 0 -> {
                    System.out.println("Выход из программы");
                    return;
                }
                default -> System.out.println("Неверный выбор. Пожалуйста, выберите один из предложенных вариантов.");
            }
        }
    }

    private void createAccount() {
        System.out.println("Name:");
        String name = scanner.nextLine();

        System.out.println("Email:");
        String email = scanner.nextLine();

        System.out.println("Password:");
        String password = scanner.nextLine();
        currentAccountId = bank.addAccount(name, email, password, 0);
    }

    private int readAmount() {
        while (!scanner.hasNextInt()) {
            scanner.next(); // Потребляем некорректный ввод
            System.out.println("Пожалуйста, введите число:");
        }
        return Integer.parseInt(scanner.nextLine());
    }

    private void deposit() {
        System.out.println("Введите сумму:");
        int amount = readAmount();

        System.out.println("Введите UUID сессии:");
        String sessionIdString = scanner.nextLine();

        try {
            UUID sessionId = UUID.fromString(sessionIdString);
            bank.deposit(sessionId, currentAccountId, amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Введен некорректный UUID");
        }

    }

    private void withdraw() {
        System.out.println("Введите сумму:");
        int amount = readAmount();

        System.out.println("Введите UUID сессии:");
        String sessionIdString = scanner.nextLine();

        try {
            UUID sessionId = UUID.fromString(sessionIdString);
            bank.withdraw(sessionId, currentAccountId, amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Введен некорректный UUID");
        }

    }


    private void showBalance() {
        System.out.println("Введите UUID сессии:");
        String sessionIdString = scanner.nextLine();

        try {
            UUID sessionId = UUID.fromString(sessionIdString);
            System.out.println("Ваш баланс: " + bank.findAccount(sessionId, currentAccountId).getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Введен некорректный UUID");
        }

    }

    private void login() {
        System.out.println("Введите email:");
        String email = scanner.nextLine();

        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        UUID sessionId = bank.login(email, password);
        System.out.println("ID Сессии: " + sessionId);
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
