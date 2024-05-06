import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import java.util.HashMap;

import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    private HashMap<Long, Account> accounts = new HashMap<>();
    private HashMap<UUID, User> sessions = new HashMap<>();
    private static final AtomicLong nextId = new AtomicLong(1); // Начинаем ID с 1

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    public long addAccount(String name, String email, String password, double initialBalance) {
        if (name.length() < 8) {
            System.out.println("The name is shorter than 8 characters");
            return -1;
        }
        if (password.length() < 8) {
            System.out.println("The password is shorter than 8 characters");
            return -1;
        }

        User owner = new User(name, email, password);
        long id = nextId.getAndIncrement(); // Получаем следующий уникальный ID
        Account account = new Account(id, initialBalance, owner);
        accounts.put(id, account);
        System.out.println("Account created with ID: " + id);
        return account.getId();
    }

    public Account findAccount(UUID sessionId, long id) {
        if (!sessions.containsKey(sessionId)) {
            System.out.println("Invalid session");
            return null;
        }

        User user = sessions.get(sessionId);
        Account account = accounts.get(id);

        if (account != null && account.getOwner().equals(user)) {
            return account;
        } else {
            System.out.println("Account not fount or acces denied");
            return null;
        }
    }

    public UUID login(String email, String password) {
        for (Account account : accounts.values()) {
            if (account.getOwner().getEmail().equals(email) && account.getOwner().getHashedPassword().equals(hashPassword(password))) {
                UUID sessionId = UUID.randomUUID();
                sessions.put(sessionId, account.getOwner());
                return sessionId;
            }
        }

        System.out.println("Login failed: Incorrect email or password");
        return null;
    }

    public void logout(UUID sessionId) {
        if (sessions.containsKey(sessionId)) {
            sessions.remove(sessionId);
        } else {
            System.out.println("Session not found");
        }
    }

    public void deposit(UUID sessionId, long id, double amount) {
        if (sessions.containsKey(sessionId) && accounts.get(id).getOwner().equals(sessions.get(sessionId))) {
            Account account = findAccount(sessionId, id);
            if (account != null) {
                account.deposit(amount);
            } else {
                System.out.println("Account not found");
            }
        }
    }

    public void withdraw(UUID sessionId, long id, double amount) {
        if (sessions.containsKey(sessionId) && accounts.get(id).getOwner().equals(sessions.get(sessionId))) {
            Account account = findAccount(sessionId, id);
            if (account != null) {
                account.withdraw(amount);
            } else {
                System.out.println("Account not found");
            }
        }
    }
}
