// Test main

import javax.swing.*;
import java.util.ResourceBundle;

public class ArtGallery {
    Client client;
    LoginGUI loginGUI;

    ResourceBundle bundle;

    public ArtGallery(Client client, ResourceBundle bundle){
        this.client = client;
        this.bundle = bundle;
    }

    public void showLoginGUI(Client client, ResourceBundle bundle) {
        loginGUI = new LoginGUI(client, bundle);
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
        JOptionPane.showMessageDialog(loginGUI, bundle.getString("gallery.success"));
        loginGUI.dispose();
        String[] splitArtDesc = userInfo.split("%");
        String username = splitArtDesc[0];
        String password = splitArtDesc[1];
        String email = splitArtDesc[2];
        User newUser = new User(username, password, email);
        showArtGalleryGUI(newUser, client);
    }

    public void incorrectLogin(){
        JOptionPane.showMessageDialog(loginGUI, bundle.getString("gallery.fail"));
    }

    public void incorrectRegister(){
        JOptionPane.showMessageDialog(loginGUI, bundle.getString("register.incorrect"));
    }

    public void correctRegister(){
        JOptionPane.showMessageDialog(loginGUI, bundle.getString("register.correct"));
    }

    public void changeLangUI(ResourceBundle bundle){
        this.bundle = bundle;

    }

    public void showArtGalleryGUI(User user, Client client) {

        ArtGalleryGUI galleryGUI = new ArtGalleryGUI(user, client, bundle);
        System.out.println(client + "In showArtGalleryGUI GUI");
        galleryGUI.setVisible(true);
        galleryGUI.setLocationRelativeTo(null);
    }
}
