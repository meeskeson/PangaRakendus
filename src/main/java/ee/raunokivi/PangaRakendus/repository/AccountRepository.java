package ee.raunokivi.PangaRakendus.repository;

import ee.raunokivi.PangaRakendus.*;
import ee.raunokivi.PangaRakendus.PankService.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Integer createClient(Client newClient) {
        String sql = "INSERT INTO clients (firstname, lastname, address) VALUES (:a1, :a2, :a3)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", newClient.getFirstname());
        paramMap.put("a2", newClient.getLastname());
        paramMap.put("a3", newClient.getAddress());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
        return (Integer) keyHolder.getKeys().get("id");
    }

    public void createAccount(Account newAccount) {
        String sql = "INSERT INTO account (number, id) VALUES (:a1, :a2)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", newAccount.getNumber());
        paramMap.put("a2", newAccount.getId());
        jdbcTemplate.update(sql, paramMap);
    }

    public void createTransaction(History newHistory) {
        String sql = "INSERT INTO transactions (number , amount, number_to, time) VALUES (:a1, :a2, :a3, :a4)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", newHistory.getNumber());
        paramMap.put("a2", newHistory.getAmount());
        paramMap.put("a3", newHistory.getNumber_to());
        paramMap.put("a4", LocalDateTime.now());
        jdbcTemplate.update(sql, paramMap);
    }

    public List<AltHistory> getHistory(String accountNr) {
        String sql = "SELECT * FROM transactions WHERE number = :a1 ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", accountNr);
        return jdbcTemplate.query(sql, paramMap, new BankService.AltHistoryRowMapper());
    }

    public List<All> getList() {
        String sql = "SELECT * FROM account e FULL OUTER JOIN clients v ON e.id=v.id";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.query(sql, paramMap, new BankService.AllRowMapper());
    }

    public List<Everything> getData() {
        String sql = "SELECT * FROM account e FULL OUTER JOIN clients v ON e.id=v.id";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.query(sql, paramMap, new BankService.EverythingRowMapper());
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
        String sql = "SELECT name FROM clients";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.queryForList(sql, paramMap, String.class);
    }

    public List<Balance> getOneAccount(int id) {
        String sql = "SELECT * FROM account WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.query(sql, paramMap, new BankService.BalanceRowMapper());
    }

    public AccountPlus getAccountPlus(String accountNr) {
        String sql = "SELECT * FROM account WHERE number = :x";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("x", accountNr);
        return jdbcTemplate.queryForObject(sql, paramMap, new BankService.WithdrawRowMapper());
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

    public void deleteRow(int index) {
        String sql = "DELETE FROM transactions WHERE index = :a1";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", index);
        jdbcTemplate.update(sql, paramMap);
    }

    public List<AltHistory> sortRow(String accountNr) {
        String sql = "SELECT * FROM transactions WHERE number = :a1 ORDER BY amount desc";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", accountNr);
        return jdbcTemplate.query(sql, paramMap, new BankService.AltHistoryRowMapper());
    }

    public List<AltHistory> searchRow(String numberTo, String accountNr) {
        String sql = "SELECT * FROM transactions WHERE number_to ILIKE :a1 AND number = :a2";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", "%" + numberTo + "%");
        paramMap.put("a2", accountNr);
        return jdbcTemplate.query(sql, paramMap, new BankService.AltHistoryRowMapper());
    }
}
