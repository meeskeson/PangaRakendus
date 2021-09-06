package ee.raunokivi.PangaRakendus.PankService;

import ee.raunokivi.PangaRakendus.Account;
import ee.raunokivi.PangaRakendus.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Service
public class BankService {

    @Autowired
    private AccountRepository accountRepository;

    public static class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet resultSet, int i) throws SQLException {
            Account result = new Account();
            result.setBalance(resultSet.getInt("balance"));
            result.setNumber(resultSet.getString("number"));
            result.setName(resultSet.getString("name"));
            result.setIs_locked(resultSet.getBoolean("is_locked"));
            return result;
        }
    }

    public String createAccount(Account newAccount) {
        accountRepository.createAccount(newAccount);
        return "You have successfully created an account!";
    }

    public List<Account> getList() {
        return accountRepository.getList();
    }

    public int getBalance(String accountNr) {
        return accountRepository.getBalance(accountNr);
    }

    public String depositMoney(String accountNr, int amount) {
        if (!isItLocked(accountNr)) {
            int newBalance = getBalance(accountNr) + amount;
            accountRepository.updateAccount(accountNr, newBalance);
            return "You have deposited " + amount + " amount of money.";
        } else return "Your account is locked.";
    }

    public String withdrawMoney(String accountNr, int amount) {
        if (!isItLocked(accountNr)) {
            int oldBalance = getBalance(accountNr);
            if (oldBalance >= amount) {
                int newBalance = oldBalance - amount;
                accountRepository.updateAccount(accountNr, newBalance);
                return "Withdrawal has been completed.";
            } else return "You have insufficient funds.";
        } else return "Your account is locked.";
    }

    public String transferMoney(String accountFrom, String accountTo, int amount) {
        if (!isItLocked(accountFrom) || !isItLocked(accountTo)) {
            int nr1Balance = getBalance(accountFrom) - amount;
            int nr2Balance = getBalance(accountTo) + amount;
            accountRepository.updateAccount(accountFrom, nr1Balance);
            accountRepository.updateAccount(accountTo, nr2Balance);
            return "It is DONE.";
        } else return "Your account is locked.";
    }

    public List<String> getAllNames() {
        return accountRepository.getAllNames();
    }

    public Account getOneAccount(String accountNr) {
        return accountRepository.getOneAccount(accountNr);
    }

    public boolean isItLocked(String accountNr) {
        return accountRepository.isItLocked(accountNr);
    }

    public String lockAccount(String accountNr) {
        accountRepository.lockAccount(accountNr);
        return "Your account has been locked.";
    }

    public String unlockAccount(String accountNr) {
        accountRepository.unlockAccount(accountNr);
        return "Your account has been unlocked.";
    }
}
