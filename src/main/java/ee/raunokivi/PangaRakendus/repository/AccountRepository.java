package ee.raunokivi.PangaRakendus.repository;

import ee.raunokivi.PangaRakendus.Account;
import ee.raunokivi.PangaRakendus.Client;
import ee.raunokivi.PangaRakendus.PankService.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void createClient(Client newClient) {
        String sql = "INSERT INTO clients (id, name) VALUES (:a1, :a2)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", newClient.getId());
        paramMap.put("a2", newClient.getName());
        jdbcTemplate.update(sql, paramMap);
    }

    public void createAccount(Account newAccount) {
        String sql = "INSERT INTO account (number, name) VALUES (:a1, :a2)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", newAccount.getNumber());
        paramMap.put("a2", newAccount.getName());
        jdbcTemplate.update(sql, paramMap);
    }

    public List<Account> getList() {
        String sql = "SELECT * FROM account";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.query(sql, paramMap, new BankService.AccountRowMapper());
    }

    public int getBalance(String accountNr) {
        String sql = "SELECT balance FROM account WHERE number = :a1";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", accountNr);
        return jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    public void updateAccount(String accountNr, int amount) {
        String sql = "UPDATE account SET balance = :a1 WHERE number = :a2";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", amount);
        paramMap.put("a2", accountNr);
        jdbcTemplate.update(sql, paramMap);
    }

    public List<String> getAllNames() {
        String sql = "SELECT name FROM account";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.queryForList(sql, paramMap, String.class);
    }

    public Account getOneAccount(String accountNr) {
        String sql = "SELECT * FROM account WHERE number = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", accountNr);
        return jdbcTemplate.queryForObject(sql, paramMap, new BankService.AccountRowMapper());
    }

    public boolean isItLocked(String accountNr) {
        String sql = "SELECT is_locked FROM account WHERE number = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", accountNr);
        return jdbcTemplate.queryForObject(sql, paramMap, Boolean.class);
    }

    public void lockAccount(String accountNr) {
        String sql = "UPDATE account SET is_locked = :a1 WHERE number = :a2";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a2", accountNr);
        paramMap.put("a1", true);
        jdbcTemplate.update(sql, paramMap);
    }

    public void unlockAccount(String accountNr) {
        String sql = "UPDATE account SET is_locked = :a1 WHERE number = :a2";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a2", accountNr);
        paramMap.put("a1", false);
        jdbcTemplate.update(sql, paramMap);
    }
}
