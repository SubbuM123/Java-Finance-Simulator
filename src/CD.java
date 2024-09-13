public class CD extends Account {
    protected int maturity;

    public CD(double setBalance, String setName, double setApy, int setMaturity) {
        super(setBalance, setName, setApy);
        if (maturity > 0) {
            throw new IllegalArgumentException("Maturity  must be positive");
        } 
    
        maturity = setMaturity;
    }

    @Override
    public void Withdraw(double amount) {
        balance = balance - amount;
        //System.out.println(this.GetName() + " CD balance: $" + this.GetBalance());
    }

    public boolean Matured() {
        if (maturity > 0) {
            return false;
        }
        return true;
    }
    public void setMaturity(int newMaturity) {
        maturity = newMaturity;
    }
    public int getMaturity() {
        return maturity;
    }
    @Override
    public void Simulation() {
        // CD other = new CD(this.balance, this.name, this.apy, this.maturity)
        // for (int i = other.maturity; i > 0; i--) {
        //     other.setMaturity(i - 1);
        //     double rate = 1 + 0.01 * other.apy;
        //     double exp = Math.pow(rate, 0.08333333333);
        //     this.SetBalance(other.balance * exp);
        // }
        // return other.balance;

        maturity--;
        double rate = 1 + 0.01 * apy;
        double exp = Math.pow(rate, 0.08333333333);
        balance = balance * exp;
    }
}
