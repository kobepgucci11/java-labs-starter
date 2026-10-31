package edu.course.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    @Test
    void successfulWithdrawal() {
        BankAccount account = new BankAccount(100);
        account.withdraw(40);
        assertEquals(60, account.getBalance());
    }

    @Test
    void withdrawalThrowsExceptionIfInsufficientFunds() {
        BankAccount account = new BankAccount(50);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(100));
    }

    @Test
    void constructorThrowsForNegativeBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(-10));
    }
}