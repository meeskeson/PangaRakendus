package ee.raunokivi.PangaRakendus.PangaController;

import ee.raunokivi.PangaRakendus.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostMapping("create")
    public String createAccount(@RequestBody Account newAccount) {
        String sql = "INSERT INTO account (number, name) VALUES (:a1, :a2)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", newAccount.getNumber());
        paramMap.put("a2", newAccount.getName());
        jdbcTemplate.update(sql, paramMap);
        return "You have successfully created an account!";
    }

    @GetMapping("list")
    public List<Account> getList() {
        String sql = "SELECT * FROM account";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.query(sql, paramMap, new AccountRowMapper());
    }

    public static class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet resultSet, int i) throws SQLException {
            Account result = new Account();
            result.setNumber(resultSet.getString("number"));
            result.setName(resultSet.getString("name"));
            result.setBalance(resultSet.getInt("balance"));
            result.setIs_locked(resultSet.getBoolean("is_locked"));
            return result;
        }
    }

    @GetMapping("balance/{id}")
    public int getBalance(@PathVariable("id") String x) {
        String sql = "SELECT balance FROM account WHERE number = :a1";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a1", x);
        return jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    @PutMapping("deposit/{id}/{amount}")
    public String depositMoney(@PathVariable("id") String x, @PathVariable("amount") int y) {
        if (!isItLocked(x)) {
            String sql = "UPDATE account SET balance = :a1 WHERE number = :a2";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("a1", getBalance(x) + y);
            paramMap.put("a2", x);
            jdbcTemplate.update(sql, paramMap);
            return "You have deposited " + y + " amount of money.";
        } else return "Your account is locked.";
    }

    @PutMapping("withdraw/{id}/{amount}")
    public String withdrawMoney(@PathVariable("id") String x, @PathVariable("amount") int y) {
        if (!isItLocked(x)) {
            int oldBalance = getBalance(x);
            if (oldBalance >= y) {
                String sqlNew = "UPDATE account SET balance = :a1 WHERE number = :a2";
                Map<String, Object> paramMapNew = new HashMap<>();
                paramMapNew.put("a2", x);
                paramMapNew.put("a1", oldBalance - y);
                jdbcTemplate.update(sqlNew, paramMapNew);
                return "Withdrawal has been completed.";
            } else return "You have insufficient funds.";
        } else return "Your account is locked.";
    }

    @PutMapping("transfer/{idfrom}/{idto}/{amount}")
    public String transferMoney(@PathVariable("idfrom") String id1, @PathVariable("idto") String id2, @PathVariable("amount") int amount) {
        if (!isItLocked(id1) || !isItLocked(id2)) {
            String sql1 = "UPDATE account SET balance = :a1 WHERE number = :a2";
            String sql2 = "UPDATE account SET balance = :a3 WHERE number = :a4";
            Map<String, Object> paramMap1 = new HashMap<>();
            paramMap1.put("a2", id1);
            paramMap1.put("a1", getBalance(id1) - amount);
            Map<String, Object> paramMap2 = new HashMap<>();
            paramMap2.put("a4", id2);
            paramMap2.put("a3", getBalance(id2) + amount);
            jdbcTemplate.update(sql1, paramMap1);
            jdbcTemplate.update(sql2, paramMap2);
            return "It is DONE.";
        } else return "Your account is locked.";
    }

    @GetMapping("getnames")
    private List<String> getAllNames() {
        String sql = "SELECT name FROM account";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.queryForList(sql, paramMap, String.class);
    }

    @GetMapping("getaccount/{id}")
    public Account getOneAccount(@PathVariable("id") String id) {
        String sql = "SELECT * FROM account WHERE number = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.queryForObject(sql, paramMap, new AccountRowMapper());
    }


    @GetMapping("islocked/{id}")
    public boolean isItLocked(@PathVariable("id") String id) {
        String sql = "SELECT is_locked FROM account WHERE number = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.queryForObject(sql, paramMap, Boolean.class);
    }

    @PutMapping("lock/{id}")
    public String lockAccount(@PathVariable("id") String id) {
        String sql = "UPDATE account SET is_locked = :a1 WHERE number = :a2";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a2", id);
        paramMap.put("a1", true);
        jdbcTemplate.update(sql, paramMap);
        return "Your account has been locked.";
    }

    @PutMapping("unlock/{id}")
    public String unlockAccount(@PathVariable("id") String id) {
        String sql = "UPDATE account SET is_locked = :a1 WHERE number = :a2";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a2", id);
        paramMap.put("a1", false);
        jdbcTemplate.update(sql, paramMap);
        return "Your account has been unlocked.";
    }
}
