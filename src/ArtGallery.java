// Test main

import javax.swing.*;

public class ArtGallery {
    Client client;
    LoginGUI loginGUI;

    public ArtGallery(Client client){
        this.client = client;
    }

    public void showLoginGUI(Client client) {
        loginGUI = new LoginGUI(client);
        loginGUI.setLoginListener(new LoginGUI.LoginListener() {
            @Override
            public void onLogin(String username, String password) {
                client.sendMessage("LOGIN"+username+","+password);
            }
            
            @Override
            public void onRegister(String username, String password) {
                client.sendMessage("REGISTER"+username+","+password);
            }
        });
        loginGUI.setVisible(true);
    }

    public void correctLogin(String userInfo){
        JOptionPane.showMessageDialog(loginGUI, "Login successful!");
        loginGUI.dispose();
        String[] splitArtDesc = userInfo.split("%");
        String username = splitArtDesc[0];
        String password = splitArtDesc[1];
        String email = splitArtDesc[2];
        User newUser = new User(username, password, email);
        showArtGalleryGUI(newUser, client);
    }

    public void incorrectLogin(){
        JOptionPane.showMessageDialog(loginGUI, "Username and password combination not found!");
    }

    public void incorrectRegister(){
        JOptionPane.showMessageDialog(loginGUI, "Username already taken!");
    }

    public void correctRegister(){
        JOptionPane.showMessageDialog(loginGUI, "Register successful!");
    }

    public void showArtGalleryGUI(User user, Client client) {

        ArtGalleryGUI galleryGUI = new ArtGalleryGUI(user, client);
        System.out.println(client + "In showArtGalleryGUI GUI");
        galleryGUI.setVisible(true);
        galleryGUI.setLocationRelativeTo(null);
    }
}
