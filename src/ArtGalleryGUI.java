import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ArtGalleryGUI extends JFrame implements ActionListener, ItemListener {
    private User currentUser;
    private JList<Art> artList;
    private DefaultListModel<Art> listModel;

    private JButton uploadBtn, paintBtn, createGalleryBtn, exitBtn, reloadBtn;
    public static Client client;
    private JPanel detailPanel;
    private ResourceBundle bundle;
    private JComboBox cbLang;

    public ArtGalleryGUI(User user, Client client, ResourceBundle bundle) {
        this.client = client;
        this.currentUser = user;
        this.bundle = bundle;
        cbLang = new JComboBox<>();
        cbLang.addItemListener(this);
        cbLang.addItem("English");
        cbLang.addItem("中文");
        artGalleryShow();

    }

    public void artGalleryShow(){
        getContentPane().removeAll(); // ← This clears previous components
        this.bundle = client.getLangBundle();
        System.out.println("YAYYY");
        setTitle("PixelPlace");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        String welcome = bundle.getString("gallery.welcome");
        toolBar.add(new JLabel(MessageFormat.format(welcome, currentUser.getUsername())));
        toolBar.addSeparator();

        uploadBtn = new JButton(bundle.getString("gallery.upload"));
        uploadBtn.addActionListener(this);
        paintBtn = new JButton(bundle.getString("gallery.paint"));
        paintBtn.addActionListener(this);
        createGalleryBtn = new JButton(bundle.getString("gallery.createGal"));
        createGalleryBtn.addActionListener(this);
        reloadBtn = new JButton(bundle.getString("gallery.reload"));
        reloadBtn.addActionListener(this);
        exitBtn = new JButton(bundle.getString("gallery.exit"));
        exitBtn.addActionListener(this);

        toolBar.add(uploadBtn);
        toolBar.add(paintBtn);
        toolBar.add(createGalleryBtn);
        toolBar.add(reloadBtn);
        toolBar.add(exitBtn, BorderLayout.EAST);
        cbLang.addItemListener(this);
        toolBar.add(cbLang);
        toolBar.add(exitBtn);
        mainPanel.add(toolBar, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        listModel = new DefaultListModel<>();
        artList = new JList<>(listModel);
        artList.setCellRenderer(new ArtListRenderer());
        JScrollPane listScrollPane = new JScrollPane(artList);

        listScrollPane.setPreferredSize(new Dimension(250, 400));

        detailPanel = new JPanel(new BorderLayout());
        detailPanel.add(new JLabel(bundle.getString("gallery.details"), JLabel.CENTER), BorderLayout.NORTH);

        splitPane.setLeftComponent(listScrollPane);
        splitPane.setRightComponent(detailPanel);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel(bundle.getString("gallery.status")));
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        artList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Art selectedArt = artList.getSelectedValue();
                if (selectedArt != null) {
                    showArtDetails(selectedArt);
                }
            }
        });

        add(mainPanel);
        loadUserArt();
        repaint();                    // ← Redraws the frame
        revalidate();
    }

    private void loadUserArt() {
        listModel.clear();
        //System.out.println("LOADING ART LIST");
        File f = new File("artFiles/artDatabaseFile.txt");
        try{
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String artObjectString = br.readLine();
            //System.out.println("read line");
            while (artObjectString != null) {
                Art newArt = new Art();
                String[] splitArtDesc = artObjectString.split("%");
                newArt.setName(splitArtDesc[0]);
                newArt.setArtTime(splitArtDesc[1]);
                newArt.setArtUser(splitArtDesc[2]);
                newArt.setArtLocation(splitArtDesc[3]);
                newArt.setArtDescription(splitArtDesc[5]);
                newArt.setArtFile(splitArtDesc[4]);
                listModel.addElement(newArt);
                //System.out.println("new art created: " + newArt.toString());
                artObjectString = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() != ItemEvent.SELECTED) return;

        cbLang.removeItemListener(this);
        if (cbLang.getSelectedItem() == "English"){
            client.changeLang("English");
            artGalleryShow();
        } else if (cbLang.getSelectedItem() == "中文"){
            client.changeLang("中文");
            artGalleryShow();
        }
    }

    private void showArtDetails(Art art) {
        detailPanel.removeAll();

        JLabel titleLabel = new JLabel(art.getArtName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        String label = bundle.getString("gallery.artist");
        label = MessageFormat.format(label, currentUser.getUsername());
        JLabel artistLabel = new JLabel(label + art.getArtLocation(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial",Font.PLAIN, 14));

        ImageIcon fullImage = new ImageIcon(art.getArtFile());
        Image img = fullImage.getImage();
        Image scaledImg = img.getScaledInstance(300, 400, Image.SCALE_SMOOTH); // 👈 resize to 50x50
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JTextArea descriptionArea = new JTextArea(art.getArtDescription());
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);

        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);

        detailPanel.setLayout(new BorderLayout());
        detailPanel.add(titleLabel, BorderLayout.NORTH);
        detailPanel.add(imageLabel, BorderLayout.CENTER);
        detailPanel.add(artistLabel, BorderLayout.SOUTH);
        detailPanel.add(descriptionScroll, BorderLayout.EAST);

        detailPanel.revalidate();
        detailPanel.repaint();
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadBtn)
        {
            UploadGUI uGUI = new UploadGUI(client, currentUser, bundle);
            uGUI.show();
        }
        if (e.getSource() == paintBtn)
        {
            CanvasGUI cGui = new CanvasGUI(client, currentUser, bundle);
            cGui.show();
        }
        if (e.getSource() == exitBtn)
        {
            client.sendMessage("EXIT");
            dispose();
        }
        if (e.getSource() == reloadBtn){
                dispose(); // close current window
                new ArtGalleryGUI(currentUser, client, bundle).setVisible(true); // open a fresh new one
        }
    }

    private static class ArtListRenderer extends JLabel implements ListCellRenderer<Art> {
        public ArtListRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Art> list, Art art,
                int index, boolean isSelected, boolean cellHasFocus) {

            setText(art.getArtName());
            //setIcon(new ImageIcon(art.getArtFile()));

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

