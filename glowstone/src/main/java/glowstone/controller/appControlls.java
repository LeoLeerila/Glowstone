package glowstone.controller;
import glowstone.view.AppView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        Add_tab.setOnAction(event -> { addNewTab();});
        m_1.setOnAction(event -> { addNewTab();});
        m_2.setOnAction(event -> { addNewCategory();});
        add_btn.getItems().addAll(m_1, m_2);
        loadWorkingArea();
    }

    public void loadWorkingArea(){
        noteSpace_view.getChildren().clear();
        tab_column.getChildren().clear();

        //REPLACE THIS CODE LATER!!!!!!!!!!!!! This is intended to load the users existing data!!!!
        //^wise it may be to instead loop the below for all user data!
        Button tab_btn = new Button("Default Workspace"); //This especially needs a way to be tied to an id of a workspace!
        tab_btn.getStyleClass().add("column_btn");
        tab_btn.setOnAction(event -> {openTab();});
        MenuItem o_1 = new MenuItem("Edit");
        MenuItem o_2 = new MenuItem("Delete");
        MenuButton m = new MenuButton("✎", null, o_1, o_2);
        o_1.setOnAction(event -> { editTab();});
        o_2.setOnAction(event -> { tab_column.getChildren().remove(tab_btn);});
        tab_btn.setGraphic(m);
        tab_column.getChildren().add(tab_btn);

        VBox categoryColumn = new VBox();
        categoryColumn.getStyleClass().add("category_view");
        HBox categoryTitle = new HBox();
        categoryTitle.getStyleClass().add("category_title");
        Label categoryName = new Label("Default Category");
        Button categoryEdit = new Button("✎");
        categoryEdit.setOnAction(event -> { editCategory();});
        categoryEdit.getStyleClass().add("editing_btns");
        categoryTitle.getChildren().addAll(categoryName, categoryEdit);
        //eventhandler and method call

        VBox noteInstance = new VBox();
        noteInstance.getStyleClass().add("note_card");
        HBox noteTitle = new HBox();
        Label noteName = new Label("Default note title");
        VBox noteContents = new VBox();
        noteContents.setFillWidth(true);
        Label theStuff = new Label("yapadabadubu yapapapapapa lalalalala aaaaaaaaaa -Larry");
        theStuff.setWrapText(true);
        noteInstance.setMaxWidth(Double.MAX_VALUE);
        theStuff.maxWidthProperty().bind(noteInstance.widthProperty().subtract(10));
        noteContents.getChildren().add(theStuff);
        noteTitle.getChildren().add(noteName);
        noteInstance.getChildren().addAll(noteTitle, noteContents);

        categoryColumn.getChildren().addAll(categoryTitle, noteInstance);
        noteSpace_view.getChildren().add(categoryColumn);
    }
    public void openTab(){
        //clear workspace
        //fetch data for the specific tab based on some sort of ID
        System.out.println("On this tab!");
    }

    public void addNewTab(){
        System.out.println("ADDING NEW WORKSPACE TAB!!!");
        Button tab_btn = new Button("Another one");
        tab_btn.getStyleClass().add("column_btn");
        tab_btn.setOnAction(event -> {openTab();});
        MenuItem o_1 = new MenuItem("Edit");
        MenuItem o_2 = new MenuItem("Delete");
        MenuButton m = new MenuButton("✎", null, o_1, o_2);
        o_1.setOnAction(event -> { editTab();});
        o_2.setOnAction(event -> { tab_column.getChildren().remove(tab_btn);});
        tab_btn.setGraphic(m);
        tab_column.getChildren().add(tab_btn);
    }
    public void editTab(){
        System.out.println("EDITINGGGG");
    }
    public void deleteTab(){
        System.out.println("DELETING");
    }
    public void addNewCategory(){
        VBox categoryColumn = new VBox();
        categoryColumn.getStyleClass().add("category_view");
        HBox categoryTitle = new HBox();
        categoryTitle.getStyleClass().add("category_title");
        Label categoryName = new Label("Default Category");
        Button categoryEdit = new Button("✎");
        categoryEdit.setOnAction(event -> { editCategory();});
        categoryEdit.getStyleClass().add("editing_btns");
        categoryTitle.getChildren().addAll(categoryName, categoryEdit);
        categoryColumn.getChildren().add(categoryTitle);
        noteSpace_view.getChildren().add(categoryColumn);
    }
    public void editCategory(){
        System.out.println("EDITING BUT WITH CATEGORYYY");
    }
}
