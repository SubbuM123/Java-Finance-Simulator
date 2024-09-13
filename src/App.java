public class App {
    // public static void main(String[] args) throws Exception {
    //     System.out.println("Hello, World!");
    // }
    public static void main(String[] args) {
        System.out.println("Hi");
        // Account d = new Account(200, "Test", 5.5);
        // d.Deposit(10000);
        // d.Withdraw(1);
        //System.out.println(d.name + d.balance);
        Bank b = new Bank(3);
        b.NewCD(500, "First", 5, 18);
        b.Deposit(500, "Savings");
        //b.TransferFundsAcc(300, "Savings", "Checking");
        //b.TransferFundsCD(500, "First", "Checking");
        System.out.println(b.Total() + " initial");

        b.Simulate(20);
        b.Simulate(10);
        b.Simulate(20);
    }
}
