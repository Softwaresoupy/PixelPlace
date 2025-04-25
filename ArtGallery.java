// Test main

package artgallery;

import javax.swing.*;

public class ArtGallery {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showLoginGUI();
            
            // showArtGalleryGUI(); 
        });
    }

    private static void showLoginGUI() {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setLoginListener(new LoginGUI.LoginListener() {
            @Override
            public void onLogin(String username, String password) {
                JOptionPane.showMessageDialog(loginGUI, "Login succeed! " + username);
                loginGUI.dispose();
                showArtGalleryGUI();
            }
            
            @Override
            public void onRegister() {
                JOptionPane.showMessageDialog(loginGUI, "To log in...");
            }
        });
        loginGUI.setVisible(true);
    }

    private static void showArtGalleryGUI() {
        User testUser = new User("Test", "123", "test@pixelgallery.com");
        
        testUser.uploadArtwork(new Artwork("Sun", testUser));
        testUser.uploadArtwork(new Artwork("Moon", testUser));
        
        ArtGalleryGUI galleryGUI = new ArtGalleryGUI(testUser);
        galleryGUI.setVisible(true);
        
        galleryGUI.setLocationRelativeTo(null);
    }
}
