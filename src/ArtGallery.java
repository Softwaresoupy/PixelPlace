// Test main

import javax.swing.*;

public class ArtGallery {
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showLoginGUI();
        });
    }
     */

    public void showLoginGUI() {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setLoginListener(new LoginGUI.LoginListener() {
            @Override
            public void onLogin(String username, String password) {
                // if login is correct then
                JOptionPane.showMessageDialog(loginGUI, "Login succeed: " + username);
                loginGUI.dispose();
                showArtGalleryGUI();

                //else
                JOptionPane.showMessageDialog(loginGUI, "Username and password combination not found!");
            }
            
            @Override
            public void onRegister() {
                loginGUI.registerGUI();
                //JOptionPane.showMessageDialog(loginGUI, "To log in...");
            }
        });
        loginGUI.setVisible(true);
    }

    public void showArtGalleryGUI() {
        User testUser = new User("Test", "123", "test@pixelgallery.com");
        
        testUser.uploadArtwork(new Artwork("Sun", testUser));
        testUser.uploadArtwork(new Artwork("Moon", testUser));
        
        ArtGalleryGUI galleryGUI = new ArtGalleryGUI(testUser);
        galleryGUI.setVisible(true);
        
        galleryGUI.setLocationRelativeTo(null);
    }
}
