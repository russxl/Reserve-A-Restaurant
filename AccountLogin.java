import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AccountLogin extends JFrame {
    private JFrame frame, main;
    private JPanel panel, mainpnl, btnpnl;
    private JLabel userlbl, passlbl, userlbl2, passlbl2,
            usertypelbl, loginCountlbl, loginCount;
    private JTextField usertxt, passtxt, usertxt2, passtxt2;
    private JButton loginbtn, clearbtn, addbtn, editbtn, delbtn, prevbtn, nextbtn, backbtn;
    private JComboBox<String> typeList;
    private Account acc;
    private AccountController ac;

    public AccountLogin() {
        createAccountData();
        frame = new JFrame();
        panel = new JPanel(new GridLayout(3, 3));

        userlbl = new JLabel("Username");
        passlbl = new JLabel("Password");

        usertxt = new JTextField(20);
        passtxt = new JTextField(20);

        loginbtn = new JButton("Login");
        loginbtn.addActionListener(new LoginAction());
        clearbtn = new JButton("Clear");
        clearbtn.addActionListener(new ClearAction());

        panel.add(userlbl);
        panel.add(usertxt);
        panel.add(passlbl);
        panel.add(passtxt);
        panel.add(loginbtn);
        panel.add(clearbtn);

        frame.add(panel);
    }

    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            String i_user;
            String i_pass;
            i_user = usertxt.getText();
            i_pass = passtxt.getText();
            if (i_user.equals(ac.getAccount(i_user).getUsername())
                    && i_pass.equals(ac.getAccount(i_user).getPassword())) {
                usertxt.setText("");
                passtxt.setText("");
                int i = ac.getAccount(i_user).getCount();
                ac.getAccount(i_user).setCount(i + 1);
                main = new JFrame();
                main.setVisible(true);
                main.setSize(600, 200);
                main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                main.setTitle("Users");

                userlbl2 = new JLabel("Username");
                passlbl2 = new JLabel("Password");

                usertxt2 = new JTextField(20);
                usertxt2.setText(i_user);
                passtxt2 = new JTextField(20);
                passtxt2.setText(i_pass);

                usertypelbl = new JLabel("User type");
                mainpnl = new JPanel(new GridLayout(4, 2));
                String[] userTypeList = { "Administrator", "User" };
                typeList = new JComboBox<>(userTypeList);
                typeList.setSelectedIndex(selectedIndex(i_user));

                loginCountlbl = new JLabel("Login Count");
                loginCount = new JLabel(Integer.toString(ac.getAccount(i_user).getCount()));

                // buttons
                btnpnl = new JPanel(new GridLayout(0, 6));
                addbtn = new JButton("Add");
                addbtn.addActionListener(new AddAction());
                editbtn = new JButton("Edit");
                editbtn.addActionListener(new EditAction());
                delbtn = new JButton("Delete");
                delbtn.addActionListener(new DeleteAction());
                prevbtn = new JButton("Previous");
                prevbtn.addActionListener(new PreviousAction());
                nextbtn = new JButton("Next");
                nextbtn.addActionListener(new NextAction());
                backbtn = new JButton("Back");
                backbtn.addActionListener(new BackAction());

                btnpnl.add(addbtn);
                btnpnl.add(editbtn);
                btnpnl.add(delbtn);
                btnpnl.add(prevbtn);
                btnpnl.add(nextbtn);
                btnpnl.add(backbtn);

                mainpnl.add(userlbl2);
                mainpnl.add(usertxt2);
                mainpnl.add(passlbl2);
                mainpnl.add(passtxt2);
                mainpnl.add(usertypelbl);
                mainpnl.add(typeList);
                mainpnl.add(loginCountlbl);
                mainpnl.add(loginCount);
                main.add(mainpnl);
                main.add(btnpnl, BorderLayout.SOUTH);
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect Password or Username");
            }
        }
    }

    public void setupGUI() {
        frame.setVisible(true);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login");
    }

    private void createAccountData() {
        acc = new Account();
        ac = new AccountController(acc);
        ac.addAccount("username", "password", true, 0);
    }

    private int selectedIndex(String username) {
        Account temp = ac.getAccount(username);
        if (temp.getIsAdmin()) {
            return 0;
        } else {
            return 1;
        }
    }

    private boolean isAdminHelper() {
        if (typeList.getSelectedIndex() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Action Listeners
    private class ClearAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            usertxt.setText("");
            passtxt.setText("");
        }
    }

    private class AddAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usertxt2.getText();
            String password = passtxt2.getText();
            boolean isAdmin;
            if (ac.getIndex(username) == -1) {
                if (typeList.getSelectedItem().equals("Administrator")) {
                    isAdmin = true;
                } else {
                    isAdmin = false;
                }
                ac.addAccount(username, password, isAdmin, 0);
                JOptionPane.showMessageDialog(main, "User Account Successfully Added!");

            } else {
                JOptionPane.showMessageDialog(main, "Username is already taken.");
            }
        }
    }

    private class EditAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String i_user = usertxt2.getText();
            String password = passtxt2.getText();
            boolean isAdmin;
            if (typeList.getSelectedItem().equals("Administrator")) {
                isAdmin = true;
            } else {
                isAdmin = false;
            }
            ArrayList<Account> temp = ac.getArr();
            int i = 0;
            while (i < temp.size()) {
                if (!i_user.equals(temp.get(i).getUsername()) || isAdmin != temp.get(i).getIsAdmin() ||
                        !password.equals(temp.get(i).getPassword())) {
                    temp.get(i).setUsername(i_user);
                    temp.get(i).setPassword(password);
                    temp.get(i).isAdmin(isAdmin);
                    JOptionPane.showMessageDialog(main, "User Account Successfully Edited!");
                    break;
                } else {
                    JOptionPane.showMessageDialog(main, "Did you really changed something?");
                    return;
                }
            }
        }
    }

    private class DeleteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usertxt2.getText();
            String password = passtxt2.getText();
            boolean isAdmin = isAdminHelper();
            ArrayList<Account> temp = ac.getArr();
            int index = -1;
            if (username.equals(ac.getAccount(username).getUsername())
                    && password.equals(ac.getAccount(username).getPassword())) {
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).getUsername().equals(username)) {
                        index = i;
                        break;
                    }
                }
            }
            try {
                temp.remove(index);
                JOptionPane.showMessageDialog(main, "User Account Successfully Deleted!");
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(main, "Account is already deleted.");
                return;
            }

        }
    }

    private class PreviousAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usertxt2.getText();
            String password = passtxt2.getText();
            ArrayList<Account> temp = ac.getArr();
            int index = ac.getIndex(username);
            try {
                usertxt2.setText(temp.get(index - 1).getUsername());
                passtxt2.setText(temp.get(index - 1).getPassword());
                typeList.setSelectedIndex(selectedIndex(temp.get(index - 1).getUsername()));
                loginCount.setText(Integer.toString(temp.get(index - 1).getCount()));
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(main, "This is the last account.");
                return;
            }
        }
    }

    private class NextAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usertxt2.getText();
            String password = passtxt2.getText();
            ArrayList<Account> temp = ac.getArr();
            int index = ac.getIndex(username);
            try {
                usertxt2.setText(temp.get(index + 1).getUsername());
                passtxt2.setText(temp.get(index + 1).getPassword());
                typeList.setSelectedIndex(selectedIndex(temp.get(index + 1).getUsername()));
                loginCount.setText(Integer.toString(temp.get(index + 1).getCount()));
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(main, "This is the last account.");
                return;
            }
        }
    }

    private class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            main.dispose();
        }
    }
}