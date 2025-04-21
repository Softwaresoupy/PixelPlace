/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author caoziyu
 */


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ArtGalleryGUI extends JFrame {
    private User currentUser;
    private JList<Art> artworkList;
    private DefaultListModel<Art> listModel;

    public ArtGalleryGUI(User user) {
        this.currentUser = user;
        setTitle("PixelGallery - " + user.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        toolBar.add(new JLabel("Welcome, " + user.getUsername()));
        toolBar.addSeparator();
        
        JButton uploadBtn = new JButton("Upload Art");
        JButton createGalleryBtn = new JButton("Create Gallery");
        toolBar.add(uploadBtn);
        toolBar.add(createGalleryBtn);
        
        mainPanel.add(toolBar, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        listModel = new DefaultListModel<>();
        artworkList = new JList<>(listModel);
        artworkList.setCellRenderer(new ArtworkListRenderer());
        JScrollPane listScrollPane = new JScrollPane(artworkList);
        listScrollPane.setPreferredSize(new Dimension(250, 400));
        
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.add(new JLabel("Artwork Details", JLabel.CENTER), BorderLayout.NORTH);
        
        splitPane.setLeftComponent(listScrollPane);
        splitPane.setRightComponent(detailPanel);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel("Ready"));
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        add(mainPanel);
        
        loadUserArtworks();
    }

    private void loadUserArtworks() {
        listModel.clear();
        for (Art artwork : currentUser.getUploadedArtworks()) {
            listModel.addElement(artwork);
        }
    }


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                User user = new User("Username", "Password", "email");
                new ArtGalleryGUI(user).setVisible(true);
            }
        });
    }

    private static class ArtworkListRenderer extends JLabel implements ListCellRenderer<Art> {
        public ArtworkListRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Art> list, Art artwork,
                int index, boolean isSelected, boolean cellHasFocus) {
            
            setText(artwork.getArtName());
            setIcon(new ImageIcon(artwork.getArtLocation()));
            // figure out way to have a thumbnail
            
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            
            return this;
        }
    }
}