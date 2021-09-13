package ee.raunokivi.PangaRakendus.PangaController;

import ee.raunokivi.PangaRakendus.*;
import ee.raunokivi.PangaRakendus.PankService.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class Controller {

    @Autowired
    private BankService bankService;

    @PostMapping("createclient")
    public Integer createClient(@RequestBody Client newClient) {
        return bankService.createClient(newClient);
    }

    @PostMapping("createaccount")
    public String createAccount(@RequestBody Account newAccount) {
        return bankService.createAccount(newAccount);
    }

    @GetMapping("list")
    public List<All> getList() {
        return bankService.getList();
    }

    @GetMapping("getdata")
    public List<Everything> getData() {
        return bankService.getData();
    }

    @GetMapping("gethistory/{accountNr}")
    public List<History> getHistory(@PathVariable("accountNr") String accountNr) {
        return bankService.getHistory(accountNr);
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
    public String withdrawMoney2(@PathVariable("id") String x, @PathVariable("amount") int y) {
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
    public List<Balance> getOneAccount(@PathVariable("id") int id) {
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
