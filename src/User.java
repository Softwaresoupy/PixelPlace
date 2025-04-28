import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private List<Artwork> uploadedArtworks;
    private List<User> following;
    private String [] followingString;
    private String [] uploadedArtworksString;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.uploadedArtworks = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public List<Artwork> getUploadedArtworks() { return uploadedArtworks; }
    public void setUploadedArtworks(List<Artwork> uploadedArtworks) { this.uploadedArtworks = uploadedArtworks; }

    public void setUploadedArtworksString (String [] artNames) { uploadedArtworksString = artNames;}
    public String [] getUploadedArtworksString() { return uploadedArtworksString; }
    public List<User> getFollowing() { return following; }
    public void setFollowing(List<User> following) { this.following = following;}
    public void setFollowingString (String [] usersNames) { followingString = usersNames;}
    public String [] getFollowingString() { return followingString;}
    public void uploadArtwork(Artwork artwork) {
        uploadedArtworks.add(artwork);
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

    public String toString() {
        String userString = username + "%" + password + "%" + email + "%";
        return userString;
    }
}


