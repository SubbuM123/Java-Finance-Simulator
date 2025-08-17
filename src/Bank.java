import java.util.HashMap;

public class Bank {
    private HashMap<String, Account> accounts = new HashMap<String, Account>();
    private HashMap<String, CD> cds = new HashMap<String, CD>();
    private double savings_apy;
    private int cd_counter = 0;

    private HashMap<String, Account> savedAccounts = new HashMap<String, Account>();
    private HashMap<String, CD> savedCds = new HashMap<String, CD>();

    public Bank(double setAPY) {
        savings_apy = setAPY;

        accounts.put("Savings", new Account(0, "Savings", savings_apy));
        accounts.put("Checking", new Account(0, "Checking", 0));
    }
    public void Deposit(double amount, String acc) {
        Account s = accounts.get(acc);
        s.Deposit(amount);

        // if (saved) {
        //     Account ss = savedAccounts.get(acc);
        //     ss.Deposit(amount);
        // }
        
    }
    public void Withdraw(double amount, String acc) {
        Account c = accounts.get(acc);
        c.Withdraw(amount);

        // Account cc = savedAccounts.get(acc);
        // cc.Withdraw(amount);
    }
    public void NewCD(double setBalance, String setName, double setApy, int setMaturity) {
        if (cd_counter >= 5) {
            throw new IllegalArgumentException("Only a maximum of 5 CDs can be made");
        }
        CD cd = new CD(setBalance, setName, setApy, setMaturity);
        cds.put(setName, cd);
        cd_counter++;
        
        CD cd2 = new CD(setBalance, setName, setApy, setMaturity);
        savedCds.put(setName, cd2);
    }
    public void TransferFundsAcc(double amount, String from, String to) {
        if (amount > accounts.get(from).GetBalance()) {
            throw new IllegalArgumentException("Balance in " + from + " is not sufficient");
        }
        accounts.get(from).Withdraw(amount);
        accounts.get(to).Deposit(amount);

    //     savedAccounts.get(from).Withdraw(amount);
    //     savedAccounts.get(to).Deposit(amount);
    }
    public void TransferFundsCD(double amount, String from, String to) {
        if (amount > cds.get(from).GetBalance()) {
            throw new IllegalArgumentException("Balance in " + from + " is not sufficient");
        }
        CD cd = cds.get(from);
        cd.Withdraw(amount);

        // CD cd2 = savedCds.get(from);
        // cd2.Withdraw(amount);

        // accounts.get(to).Deposit(amount * (1 - (0.01 * cd.GetPenalty())));
    }
    public void EmptyCD(CD cd) {
        accounts.get("Savings").Deposit(cd.GetBalance());
        cds.remove(cd.GetName());
    }
    public double Total() {
        double t = 0;
        for (CD c : cds.values()) {
            t += c.GetBalance();
        }
        t += accounts.get("Savings").GetBalance() + accounts.get("Checking").GetBalance();
        return t;
    }
    public void SaveAccounts() {
        for (String s : accounts.keySet()) {
            Account a = accounts.get(s);
            Account b = new Account(a.balance, a.name, a.apy);
            savedAccounts.put(s, b);
        }
        for (String c : cds.keySet()) {
            CD a = cds.get(c);
            CD b = new CD(a.balance, a.name, a.apy, a.maturity);
            savedCds.put(c, b);
        }
    }
    public void ResetAccounts() {
        for (String s : savedAccounts.keySet()) {
            Account a = savedAccounts.get(s);
            Account b = new Account(a.balance, a.name, a.apy);
            accounts.put(s, b);
        }
        for (String c : savedCds.keySet()) {
            CD a = savedCds.get(c);
            CD b = new CD(a.balance, a.name, a.apy, a.maturity);
            cds.put(c, b);
        }
    }
    public double Simulate(int months) {
       SaveAccounts();
        for (int i = 0; i < months; ++i) {
            for (CD c : cds.values()) {
                if (!c.Matured()) {
                    c.Simulation();
                } else {
                    EmptyCD(c);
                }
            }
        accounts.get("Savings").Simulation();
       }
       double total = Total();
       System.out.println(total + " end");
       
       ResetAccounts();
       return total;
    }
    // public void SimulateSalary(int months, double salary) {
    //    iterate through each month and check and empty as needed
    // }

}
