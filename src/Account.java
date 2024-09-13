public class Account {
    protected double balance;
    protected String name;
    protected double apy;

    public Account(double setBalance, String setName, double setApy) {
        if (setBalance < 0 || setApy < 0) {
            throw new IllegalArgumentException("Balance and APY must be positive");
        }
        balance = setBalance;
        name = setName;
        apy = setApy;
    }

    public double GetBalance() {
        return balance;
    }

    public void SetBalance(double bal) {
        balance = bal;
    }

    public String GetName() {
        return name;
    }

    public void Withdraw(double amount) {
        balance = balance - amount;
        //System.out.println(name + " account balance: $" + balance);
    }

    public void Deposit(double amount) {
        balance += amount;
        //System.out.println(name + " account balance: $" + balance);
    }
    public void Simulation() {
        // for (int i = 0; i <)
        double rate = 1 + 0.01 * apy;
        double exp = Math.pow(rate, 0.08333333333);
        balance = balance * exp;
    }
}
