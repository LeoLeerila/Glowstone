package glowstone.controller;
import glowstone.view.AppView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class appControlls {
    String[] st = { "New tab", "New category" };
    //Header
    @FXML
    private ChoiceBox<String> add_btn;
    @FXML
    private TextField search_field;
    @FXML
    private Button filter_btn;
    @FXML
    private Button search_btn;
    @FXML
    private Label currentTabName_title;
    @FXML
    private Button settings_btn;

    @FXML
    public void initialize() {
        //Populate choicebox with choices
        add_btn.setValue("Add...");
        add_btn.setItems(FXCollections.observableArrayList(st));
        add_btn.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                System.out.println("clicked " + newV);
            }
        });
    }


}
