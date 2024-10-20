import java.util.ArrayList;

public class AccountController {
    private Account acc;
    private ArrayList<Account> arr;

    public AccountController(Account acc) {
        this.acc = acc;
        arr = new ArrayList<>();
    }

    public void addAccount(String username, String password, boolean isAdmin, int count) {
        Account temp = new Account();
        temp.setUsername(username);
        temp.setPassword(password);
        temp.isAdmin(isAdmin);
        temp.setCount(count);

        arr.add(temp);
    }

    public Account getAccount(String username) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getUsername().equals(username)) {
                return arr.get(i);
            }
        }
        return arr.get(0);
    }

    public ArrayList<Account> getArr() {
        return arr;
    }

    public void setArr(ArrayList<Account> arr) {
        this.arr = arr;
    }

    public int getIndex(String username) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }
}