package glowstone.controller;
import glowstone.view.AppView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class appControlls {
    MenuItem m_1 = new MenuItem("Add new workspace tab");
    MenuItem m_2 = new MenuItem("Add a new category");
    @FXML
    private MenuButton add_btn;
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
    private VBox tab_column;
    @FXML
    private Button Add_tab;
    @FXML
    private HBox noteSpace_view;

    @FXML
    public void initialize() {
        //For some random ass reason the menubutton comes with "Action 1" and "Action 2" options by default.... needs to be cleared.
        add_btn.getItems().clear();
        add_btn.getItems().addAll(m_1, m_2);
        // https://www.geeksforgeeks.org/java/javafx-menubutton/ <- do that shit later
        loadWorkingArea();
    }

    public void loadWorkingArea(){
        noteSpace_view.getChildren().clear();
        tab_column.getChildren().clear();

        //REPLACE THIS CODE LATER!!!!!!!!!!!!! This is intended to load the users existing data!!!!
        //^wise it may be to instead loop the below for all user data!
        Button tab_btn = new Button("Default Workspace");
        tab_btn.getStyleClass().add("column_btn");
        MenuItem o_1 = new MenuItem("Edit");
        MenuItem o_2 = new MenuItem("Delete");
        MenuButton m = new MenuButton("✎", null, o_1, o_2);
        //add eventHandlers to Edit and Delete (call a method)
        tab_btn.setGraphic(m);
        tab_column.getChildren().add(tab_btn);

        VBox categoryColumn = new VBox();
        categoryColumn.getStyleClass().add("category_view");
        HBox categoryTitle = new HBox();
        categoryTitle.getStyleClass().add("category_title");
        Label categoryName = new Label("Default Category");
        Button categoryEdit = new Button("✎");
        categoryEdit.getStyleClass().add("editing_btns");
        categoryTitle.getChildren().addAll(categoryName, categoryEdit);
        //eventhandler and method call

        VBox noteInstance = new VBox();
        HBox noteTitle = new HBox();
        Label noteName = new Label("Default note title");
        VBox noteContents = new VBox();
        Label theStuff = new Label("yapadabadubu yapapapapapa lalalalala aaaaaaaaaa -Larry");
        noteContents.getChildren().add(theStuff);
        noteTitle.getChildren().add(noteName);
        noteInstance.getChildren().addAll(noteTitle, noteContents);

        categoryColumn.getChildren().addAll(categoryTitle, noteInstance);
        noteSpace_view.getChildren().add(categoryColumn);
    }
}
