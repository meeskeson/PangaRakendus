package ee.raunokivi.PangaRakendus.PankService;

import ee.raunokivi.PangaRakendus.*;
import ee.raunokivi.PangaRakendus.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Service
public class BankService {

    @Autowired
    private AccountRepository accountRepository;

    public static class BalanceRowMapper implements RowMapper<Balance> {

        @Override
        public Balance mapRow(ResultSet resultSet, int i) throws SQLException {
            Balance result = new Balance();
            result.setBalance(resultSet.getInt("balance"));
            result.setNumber(resultSet.getString("number"));
            return result;
        }
    }

    public static class AllRowMapper implements RowMapper<All> {
        @Override
        public All mapRow(ResultSet resultSet, int i) throws SQLException {
            All result = new All();
            result.setAddress(resultSet.getString("address"));
            result.setBalance(resultSet.getInt("balance"));
            result.setNumber(resultSet.getString("number"));
            result.setFirstname(resultSet.getString("firstname"));
            result.setLastname(resultSet.getString("lastname"));
            return result;
        }
    }

    public static class EverythingRowMapper implements RowMapper<Everything> {

        @Override
        public Everything mapRow(ResultSet resultSet, int i) throws SQLException {
            Everything result = new Everything();
            result.setAddress(resultSet.getString("address"));
            result.setBalance(resultSet.getInt("balance"));
            result.setNumber(resultSet.getString("number"));
            result.setFirstname(resultSet.getString("firstname"));
            result.setLastname(resultSet.getString("lastname"));
            result.setId(resultSet.getInt("id"));
            result.setIs_locked(resultSet.getBoolean("is_locked"));
            return result;
        }
    }

    public static class HistoryRowMapper implements RowMapper<History> {

        @Override
        public History mapRow(ResultSet resultSet, int i) throws SQLException {
            History result = new History();
            result.setNumber(resultSet.getString("number"));
            result.setAmount(resultSet.getInt("amount"));
            result.setNumber_to(resultSet.getString("number_to"));
            result.setTime(resultSet.getString("time"));
            return result;
        }
    }

    public static class WithdrawRowMapper implements RowMapper<AccountPlus> {

        @Override
        public AccountPlus mapRow(ResultSet resultSet, int i) throws SQLException {
            AccountPlus result = new AccountPlus();
            result.setBalance(resultSet.getInt("balance"));
            result.setNumber(resultSet.getString("number"));
            result.setIs_locked(resultSet.getBoolean("is_locked"));
            return result;
        }
    }

    public List<History> getHistory(String accountNr) {
        return accountRepository.getHistory(accountNr);
    }

    public Integer createClient(Client newClient) {
        return accountRepository.createClient(newClient);
    }

    public String createAccount(Account newAccount) {
        accountRepository.createAccount(newAccount);
        return "You have successfully created an account!";
    }

    public List<All> getList() {
        return accountRepository.getList();
    }

    public List<Everything> getData() {
        return accountRepository.getData();
    }

    public int getBalance(String accountNr) {
        return accountRepository.getBalance(accountNr);
    }

    @Transactional
    public String depositMoney(String accountNr, int amount) {
        AccountPlus account = accountRepository.getAccountPlus(accountNr);
        History history = new History(accountNr, amount, null);
        if (!account.isIs_locked()) {
            int newBalance = account.getBalance() + amount;
            accountRepository.updateAccount(accountNr, newBalance);
            accountRepository.createTransaction(history);
            return "You have deposited " + amount + " amount of money.";
        } else return "Your account is locked.";
    }

    @Transactional
    public String withdrawMoney(String accountNr, int amount) {
        AccountPlus account = accountRepository.getAccountPlus(accountNr);
        History history = new History(accountNr,amount*-1, null);
        if (!account.isIs_locked()) {
            int oldBalance = account.getBalance();
            if (oldBalance >= amount) {
                int newBalance = oldBalance - amount;
                accountRepository.updateAccount(accountNr, newBalance);
                accountRepository.createTransaction(history);
                return "Withdrawal has been completed.";
            } else return "You have insufficient funds.";
        } else return "Your account is locked.";
    }

    @Transactional
    public String transferMoney(String accountFrom, String accountTo, int amount) {
        AccountPlus accFrom = accountRepository.getAccountPlus(accountFrom);
        AccountPlus accTo = accountRepository.getAccountPlus(accountTo);
        History history = new History(accountFrom,amount*-1, accountTo);
        History secondHistory = new History(accountTo, amount, accountFrom);
        if (!accFrom.isIs_locked() || !accTo.isIs_locked()) {
            int nr1Balance = accFrom.getBalance() - amount;
            int nr2Balance = accTo.getBalance() + amount;
            accountRepository.updateAccount(accountFrom, nr1Balance);
            accountRepository.updateAccount(accountTo, nr2Balance);
            accountRepository.createTransaction(history);
            accountRepository.createTransaction(secondHistory);
            return "It is DONE.";
        } else return "Your account is locked.";
    }

    public List<String> getAllNames() {
        return accountRepository.getAllNames();
    }

    public List<Balance> getOneAccount(int id) {
        return accountRepository.getOneAccount(id);
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
