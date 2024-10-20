public class Account {
    private String username;
    private String password;
    private boolean isAdmin;
    private int count;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void isAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public int getCount() {
        return count;
    }
}