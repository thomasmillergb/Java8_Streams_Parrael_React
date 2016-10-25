package common.person;

/**
 * @author Thomas
 *         Created by Thomas on 25/10/2016.
 */
public class Person {
    private String name;
    private float balance;

    public Person(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public float getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
