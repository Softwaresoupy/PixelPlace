// TEST artwork class

import java.awt.*;
import java.util.*;
import javax.swing.*;


public class Artwork {
    private String title;
    private User artist;
    private Image thumbnail;

    public Artwork(String title, User artist) {
        this.title = title;
        this.artist = artist;
        this.thumbnail = getDefaultThumbnail();
    }

    private Image getDefaultThumbnail() {
        return new ImageIcon("placeholder.jpg").getImage();
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }
}
