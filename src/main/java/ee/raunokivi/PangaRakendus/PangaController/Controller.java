package ee.raunokivi.PangaRakendus.PangaController;

import ee.raunokivi.PangaRakendus.Account;
import ee.raunokivi.PangaRakendus.PankService.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class Controller {

    @Autowired
    private BankService bankService;

    @PostMapping("create")
    public void createAccount(@RequestBody Account newAccount) {
        bankService.createAccount(newAccount);
    }

    @GetMapping("list")
    public List<Account> getList() {
        return bankService.getList();
    }

    @GetMapping("balance/{id}")
    public int getBalance(@PathVariable("id") String x) {
        return bankService.getBalance(x);
    }

    @PutMapping("deposit/{id}/{amount}")
    public String depositMoney(@PathVariable("id") String x, @PathVariable("amount") int y) {
        return bankService.depositMoney(x, y);
    }

    @PutMapping("withdraw/{id}/{amount}")
    public String withdrawMoney(@PathVariable("id") String x, @PathVariable("amount") int y) {
        return bankService.withdrawMoney(x, y);
    }

    @PutMapping("transfer/{idfrom}/{idto}/{amount}")
    public String transferMoney(@PathVariable("idfrom") String id1, @PathVariable("idto") String id2, @PathVariable("amount") int amount) {
        return bankService.transferMoney(id1, id2, amount);
    }

    @GetMapping("getnames")
    public List<String> getAllNames() {
        return bankService.getAllNames();
    }

    @GetMapping("getaccount/{id}")
    public Account getOneAccount(@PathVariable("id") String id) {
        return bankService.getOneAccount(id);
    }

    @GetMapping("islocked/{id}")
    public boolean isItLocked(@PathVariable("id") String id) {
        return bankService.isItLocked(id);
    }

    @PutMapping("lock/{id}")
    public String lockAccount(@PathVariable("id") String id) {
        return bankService.lockAccount(id);
    }

    @PutMapping("unlock/{id}")
    public String unlockAccount(@PathVariable("id") String id) {
        return bankService.unlockAccount(id);
    }
}
