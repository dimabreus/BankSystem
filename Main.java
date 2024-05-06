import java.util.Scanner;
import java.util.UUID;

public class Main {
    private final Bank bank = new Bank();
    private final Scanner scanner = new Scanner(System.in);
    private long currentAccountId;
    private UUID currentSessionId;

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
        if (currentSessionId == null) {
            System.out.println("Вы не вошли в систему.");
            return;
        }

        System.out.println("Введите сумму:");
        int amount = readAmount();

        bank.deposit(currentSessionId, currentAccountId, amount);
    }

    private void withdraw() {
        if (currentSessionId == null) {
            System.out.println("Вы не вошли в систему.");
            return;
        }

        System.out.println("Введите сумму:");
        int amount = readAmount();

        bank.withdraw(currentSessionId, currentAccountId, amount);
    }


    private void showBalance() {
        if (currentSessionId == null) {
            System.out.println("Вы не вошли в систему.");
            return;
        }

        System.out.println("Ваш баланс: " + bank.findAccount(currentSessionId, currentAccountId).getBalance());
    }

    private void login() {
        System.out.println("Введите email:");
        String email = scanner.nextLine();

        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        UUID sessionId = bank.login(email, password);
        if (sessionId != null) {
            currentSessionId = sessionId;
            System.out.println("Успешный вход. Сессия активна.");
        } else {
            System.out.println("Ошибка входа. Проверьте учетные данные.");
        }
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
