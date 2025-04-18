/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artgallery;

/**
 *
 * @author caoziyu
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String password; // 实际项目中应加密存储
    private String email;
    private List<Artwork> uploadedArtworks;
    private List<Gallery> createdGalleries;
    private List<User> following;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.uploadedArtworks = new ArrayList<>();
        this.createdGalleries = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public List<Artwork> getUploadedArtworks() { return uploadedArtworks; }
    public List<Gallery> getCreatedGalleries() { return createdGalleries; }
    public List<User> getFollowing() { return following; }

    // 业务方法
    public void uploadArtwork(Artwork artwork) {
        uploadedArtworks.add(artwork);
    }

    public void createGallery(Gallery gallery) {
        createdGalleries.add(gallery);
    }

    public void followArtist(User artist) {
        if (!following.contains(artist)) {
            following.add(artist);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return username.equals(other.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
