import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArtGalleryGUI extends JFrame implements ActionListener {
    private User currentUser;
    private JList<Artwork> artworkList;
    private DefaultListModel<Artwork> listModel;

    private JButton uploadBtn, paintBtn, createGalleryBtn;
    public static Client client;

    public ArtGalleryGUI(User user, Client client) {
        this.client = client;
        this.currentUser = user;
        setTitle("PixelGallery - " + user.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        toolBar.add(new JLabel("Welcome, " + user.getUsername()));
        toolBar.addSeparator();

        uploadBtn = new JButton("Upload Art");
        uploadBtn.addActionListener(this);
        paintBtn = new JButton("Paint");
        paintBtn.addActionListener(this);
        createGalleryBtn = new JButton("Create Gallery");

        toolBar.add(uploadBtn);
        toolBar.add(paintBtn);
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
        for (Artwork artwork : currentUser.getUploadedArtworks()) {
            listModel.addElement(artwork);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadBtn)
        {
            UploadGUI uGUI = new UploadGUI(client, currentUser);
            uGUI.show();
        }
        if (e.getSource() == paintBtn)
        {
            CanvasGUI cGui = new CanvasGUI();
            cGui.show();
        }

    }

    private static class ArtworkListRenderer extends JLabel implements ListCellRenderer<Artwork> {
        public ArtworkListRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Artwork> list, Artwork artwork,
                int index, boolean isSelected, boolean cellHasFocus) {

            setText(artwork.getTitle());
            setIcon(new ImageIcon(artwork.getThumbnail()));

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

