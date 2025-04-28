/*
    Author: Sophia Deng
    Lab: CNIT 325, 1:30pm Friday
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    UploadGUI class builds a basic image upload form with image preview,
    form fields for title, description, and location, and buttons for upload and submit.
 */

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class UploadGUI extends JFrame implements ActionListener {
    JLabel artImageLabel, mapImageLabel;
    JTextField titleField, descriptionField, locationField;
    JButton uploadButton, findLocationButton, submitButton;
    ImageIcon artImage, mapImage;
    public static Client client;
    public static User user;

    public UploadGUI(Client client, User user) {
        this.client = client;
        this.user = user;
        setTitle("Upload Artwork");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        uploadGUI();
    }

    public void uploadGUI() {
        // First split pane: vertical, for art image on top and map on bottom
        JSplitPane imageSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        imageSplitPane.setDividerLocation(400);

// Art Image Preview (top)
        artImageLabel = new JLabel("Image Preview", JLabel.CENTER);
        artImageLabel.setPreferredSize(new Dimension(400, 400));
        artImageLabel.setBorder(BorderFactory.createLineBorder(Color.magenta));
        imageSplitPane.setTopComponent(artImageLabel);

// Map Preview (bottom)
        mapImageLabel = new JLabel("Map Preview", JLabel.CENTER);
        mapImageLabel.setPreferredSize(new Dimension(200, 400));
        mapImageLabel.setBorder(BorderFactory.createLineBorder(Color.magenta));
        imageSplitPane.setBottomComponent(mapImageLabel);

// Now, main split pane: horizontal, left = images (above), right = form
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(450); // adjust depending on width you want

// Left side = the image split pane we just made
        mainSplitPane.setLeftComponent(imageSplitPane);

        // Right side - Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Image Upload:"), gbc);

        gbc.gridx = 1;
        uploadButton = new JButton("File upload");
        uploadButton.addActionListener(this);
        formPanel.add(uploadButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        formPanel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Location:"), gbc);

        gbc.gridx = 1;
        locationField = new JTextField(15);
        formPanel.add(locationField, gbc);

        gbc.gridx = 2;
        findLocationButton = new JButton("Find");
        findLocationButton.addActionListener(this);
        formPanel.add(findLocationButton, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        formPanel.add(submitButton, gbc);

        mainSplitPane.setRightComponent(formPanel);
        getContentPane().add(mainSplitPane, BorderLayout.CENTER);
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel("Ready"));
        getContentPane().add(statusPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            chooseImage();
        }
        if (e.getSource() == findLocationButton) {
            fetchLocationMap();
        }
        if (e.getSource() == submitButton) {
            submitArtwork();
        }
    }

    public void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            artImage = new ImageIcon(selectedFile.getAbsolutePath());
            Image img = artImage.getImage().getScaledInstance(artImageLabel.getWidth(), artImageLabel.getHeight(), Image.SCALE_SMOOTH);
            artImageLabel.setIcon(new ImageIcon(img));
            artImageLabel.revalidate();
            artImageLabel.repaint();
        }
    }

    public void fetchLocationMap() {
        String location = locationField.getText().trim();
        if (location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a location first.");
            return;
        }
        try {
            String urlStr = "https://maps.googleapis.com/maps/api/staticmap?center=" +
                    location.replace(" ", "%20") +
                    "&zoom=14&size=400x400&maptype=roadmap&key=AIzaSyA63VagYVd2NjHPeslB9Xq7wjV7GujPZHo";
            URL url = new URL(urlStr);
            mapImage = new ImageIcon(url);
            Image img = mapImage.getImage().getScaledInstance(mapImageLabel.getWidth(), mapImageLabel.getHeight(), Image.SCALE_SMOOTH);
            mapImageLabel.setIcon(new ImageIcon(img));
            mapImageLabel.revalidate();
            mapImageLabel.repaint();

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load map image.");
        }
    }

    public static BufferedImage iconToBufferedImage(ImageIcon icon) {
        Image img = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }


    public void submitArtwork() {
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        if (title.isEmpty() || artImage == null) {
            JOptionPane.showMessageDialog(this, "Please provide a title and select an image.");
            return;
        }
        String filePath = "artFiles/" + title + ".png";

        try {
            // Save the image to the file system
            File outputFile = new File(filePath);

            BufferedImage buffered = iconToBufferedImage(artImage);
            ImageIO.write(buffered, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save the artwork image.");
            return;
        }

        Art art = new Art (titleField.getText(), "", user.getUsername(), locationField.getText(), filePath, descriptionField.getText());
        client.sendMessage("ADDART" + art.toString());
        JOptionPane.showMessageDialog(this, "Artwork submitted: " + title);
        dispose();
    }
}
