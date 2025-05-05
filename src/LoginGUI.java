/**
 *
 * @author caoziyu
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;

public class LoginGUI extends JFrame implements ItemListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repasswordField;
    private JComboBox cbLang;
    private LoginListener loginListener;

    private LoginListener registerListener;
    public Client client;

    public ResourceBundle bundle;

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() != ItemEvent.SELECTED) return;

        cbLang.removeItemListener(this);

        if (cbLang.getSelectedItem() == "English") {
            client.changeLang("English");
            login();
        } else if (cbLang.getSelectedItem() == "中文") {
            client.changeLang("中文");
            login();
        }

    }


    public interface LoginListener {
        void onLogin(String username, String password);
        void onRegister(String username, String password);

    }

    public LoginGUI(Client client, ResourceBundle bundle){
        this.client = client;
        this.bundle = bundle;
        cbLang = new JComboBox<>();
        cbLang.addItem("English");
        cbLang.addItem("中文");
        cbLang.addItemListener(this);
        login();
    }
    public void login() {
        this.bundle = client.getLangBundle();
        getContentPane().removeAll(); // ← This clears previous components
        setTitle(bundle.getString("login.title"));
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel(bundle.getString("login.label"), JLabel.CENTER), gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel(bundle.getString("login.user")), gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel(bundle.getString("login.password")), gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginBtn = new JButton(bundle.getString("login.btnlogin"));
        loginBtn.addActionListener(this::performLogin);
        
        JButton registerBtn = new JButton(bundle.getString("login.btnreg"));
        registerBtn.addActionListener(e -> {
            if (loginListener != null) registerGUI();
        });

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        cbLang.addItemListener(this);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(cbLang, gbc);

        add(panel);
        repaint();                    // ← Redraws the frame
        revalidate();
    }

    public void registerGUI(){
        getContentPane().removeAll(); // ← This clears previous components
        repaint();                    // ← Redraws the frame
        revalidate();
        setTitle(bundle.getString("register.title"));
        setSize(350, 250);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(new JLabel(bundle.getString("register.label"), JLabel.CENTER), gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel(bundle.getString("register.username")), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel(bundle.getString("register.password")), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel(bundle.getString("register.reenter")), gbc);

        gbc.gridx = 1;
        repasswordField = new JPasswordField(15);
        panel.add(repasswordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton registerBtn = new JButton(bundle.getString("register.btn"));
        registerBtn.addActionListener(this::performRegister);

        JButton back = new JButton(bundle.getString("register.back"));
        back.addActionListener(e -> { login();
        });

        buttonPanel.add(back);
        buttonPanel.add(registerBtn);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);
        repaint();                    // ← Redraws the frame
        revalidate();
    }

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public void setRegisterListener(LoginListener listener) {
        this.registerListener = listener;
    }

    private void performLogin(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, bundle.getString("register.error"));
            return;
        }

        if (loginListener != null) {
            loginListener.onLogin(username, password);
        }
    }

    private void performRegister(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String repassword = new String(repasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, bundle.getString("register.error"));
            return;
        }
        if (!password.equalsIgnoreCase(repassword)) {
            JOptionPane.showMessageDialog(this, bundle.getString("register.passer"));
            return;
        }
        if(password.contains(",") || username.contains(",") ){
            JOptionPane.showMessageDialog(this, bundle.getString("register.comma"));
            return;
        }
        if (loginListener != null) {
            loginListener.onRegister(username, password);
        }
    }

}
