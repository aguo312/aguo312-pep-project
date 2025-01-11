package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {
        if (account.getUsername().length() > 0 &&
            account.getPassword().length() >= 4 &&
            accountDAO.getAccountByUsername(account.getUsername()) == null 
        )
            return accountDAO.insertAccount(account);
        return null;
    }

    public Account loginAccount(Account account) {
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null &&
            existingAccount.getUsername().equals(account.getUsername()) &&
            existingAccount.getPassword().equals(account.getPassword())
        )
            return existingAccount;
        return null;
    }
}
