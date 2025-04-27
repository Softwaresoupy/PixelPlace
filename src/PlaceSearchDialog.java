import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PlaceSearchDialog extends JDialog {
    private JTextField searchField;
    private JList<GoogleMapsService.Place> resultsList;
    private DefaultListModel<GoogleMapsService.Place> listModel;
    private GoogleMapsService.Place selectedPlace;

    public PlaceSearchDialog(Frame parent) {
        super(parent, "Search Gallery Location", true);
        setSize(500, 400);
        
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.addActionListener(e -> performSearch());
        searchPanel.add(new JLabel("Search Galleries:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        listModel = new DefaultListModel<>();
        resultsList = new JList<>(listModel);
        resultsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedPlace = resultsList.getSelectedValue();
            }
        });

        JButton selectBtn = new JButton("Select");
        selectBtn.addActionListener(e -> dispose());
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> {
            selectedPlace = null;
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectBtn);
        buttonPanel.add(cancelBtn);

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultsList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) return;

        new SwingWorker<List<GoogleMapsService.Place>, Void>() {
            @Override
            protected List<GoogleMapsService.Place> doInBackground() throws Exception {
                return GoogleMapsService.searchPlaces(query, "art_gallery");
            }

            @Override
            protected void done() {
                try {
                    listModel.clear();
                    get().forEach(listModel::addElement);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        PlaceSearchDialog.this,
                        "Failed: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();
    }

    public GoogleMapsService.Place getSelectedPlace() {
        return selectedPlace;
    }
}
