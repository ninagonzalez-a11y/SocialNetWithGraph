package ph.edu.dlsu.lbycpds.socialnetwithgraph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {

    public TextField nameField;
    public Label statusLabel;
    public TextField statusChangeLabel;
    public TextField pictureLabel;
    public TextField friendLabel;
    public TextField unfriendLabel;
    public TextField quoteLabel;
    public ImageView pictureView;
    public ListView<String> listView;
    public Label displayNameLabel;
    public Label displayStatusLabel;
    public Label displayQuoteLabel;

    ObservableList<String> friendList;

    private final SocialGraph graph = new SocialGraph();
    private Profile current;

    @FXML
    private void initialize() {
        friendList = FXCollections.observableArrayList();
        listView.setItems(friendList);
    }

    // if file is missing or invalid, it goes to unknown.png
    private Image loadImage(String path) {
        try {
            Image img = new Image(path);
            if (!img.isError()) return img;
        } catch (IllegalArgumentException ignored) { }
        return new Image("Unknown.png");
    }

    // refreshes the display for one profile
    private void show(Profile p) {
        current = p;
        friendList.setAll(graph.getFriends(p.getName()));
        pictureView.setImage(loadImage(p.getImagePath()));
        displayNameLabel.setText(p.getName());
        displayStatusLabel.setText(p.getStatus());
        displayQuoteLabel.setText(p.getQuote());
    }

    private boolean requireCurrent() {
        if (current == null) {
            statusLabel.setText("Select a profile first");
            return false;
        }
        return true;
    }

    public void handleAdd(ActionEvent e) {
        String name = nameField.getText().trim();
        if (name.isEmpty()) return;
        if (graph.addProfile(name)) {
            show(graph.lookup(name));
            statusLabel.setText("Added " + name);
        } else {
            statusLabel.setText(name + " already exists");
        }
    }

    public void handleDelete(ActionEvent e) {
        String name = nameField.getText().trim();
        if (name.isEmpty()) return;
        if (graph.deleteProfile(name)) {
            if (current != null && current.getName().equals(name)) {
                current = null;
                friendList.clear();
                pictureView.setImage(loadImage("Unknown.png"));
            }
            statusLabel.setText("Deleted " + name);
        } else {
            statusLabel.setText("Profile not found");
        }
    }

    public void handleLookup(ActionEvent e) {
        Profile p = graph.lookup(nameField.getText().trim());
        if (p != null) {
            show(p);
            statusLabel.setText("Displaying " + p.getName());
        } else {
            statusLabel.setText("Profile not found");
        }
    }

    public void handleStatus(ActionEvent e) {
        if (!requireCurrent()) return;
        current.setStatus(statusChangeLabel.getText().trim());
        displayStatusLabel.setText(current.getStatus());
        statusLabel.setText("Status updated");
    }

    public void handlePicture(ActionEvent e) {
        if (!requireCurrent()) return;
        String path = pictureLabel.getText().trim();
        if (path.isEmpty()) return;
        try {
            Image img = new Image(path);
            if (img.isError()) throw new IllegalArgumentException();
            current.setImagePath(path);
            pictureView.setImage(img);
            statusLabel.setText("Picture updated");
        } catch (IllegalArgumentException ex) {
            statusLabel.setText("Image file not found");
        }
    }

    public void handleQuote(ActionEvent e) {
        if (!requireCurrent()) return;
        current.setQuote(quoteLabel.getText().trim());
        statusLabel.setText("Quote updated");
    }

    public void handleAddFriend(ActionEvent e) {
        if (!requireCurrent()) return;
        String f = friendLabel.getText().trim();
        if (graph.addFriend(current.getName(), f)) {
            friendList.setAll(graph.getFriends(current.getName()));
            statusLabel.setText(f + " added as friend");
        } else {
            statusLabel.setText("Can't add " + f + " (profile must exist)");
        }
    }

    public void handleUnfriend(ActionEvent e) {
        if (!requireCurrent()) return;
        String f = unfriendLabel.getText().trim();
        if (graph.unfriend(current.getName(), f)) {
            friendList.setAll(graph.getFriends(current.getName()));
            statusLabel.setText(f + " removed as friend");
        } else {
            statusLabel.setText(f + " is not a friend");
        }
    }
}