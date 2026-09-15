package glowstone.controller;
import glowstone.model.Category;
import glowstone.model.Note;
import glowstone.model.Workspace;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Locale;


public class appControlls {
    languageToggle langToggle = new languageToggle(Locale.ENGLISH);
    MenuItem m_1;
    MenuItem m_2;
    @FXML
    private StackPane scene_stackpane;
    @FXML
    private BorderPane main_area;
    @FXML
    private Pane settings_overlay;
    ToggleGroup tg;
    @FXML
    private RadioButton radio_en;
    @FXML
    private RadioButton radio_fi;
    @FXML
    private RadioButton radio_ru;
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
    private Label lang_title;
    @FXML
    private Label colourScheme_title;
    @FXML
    private Button exit_btn;

    private Workspace currentActiveWorkspace;

    @FXML
    public void initialize() {
        tg = new ToggleGroup();
        radio_en.setToggleGroup(tg);
        radio_fi.setToggleGroup(tg);
        radio_ru.setToggleGroup(tg);
        main_area.prefWidthProperty().bind(scene_stackpane.widthProperty());
        main_area.prefHeightProperty().bind(scene_stackpane.heightProperty());
        settings_overlay.setVisible(false);
        settings_btn.setOnAction(event -> {
            settings_overlay.setVisible(!settings_overlay.isVisible());
        });
        settings_overlay.setOnMouseClicked(event -> {
            if (event.getTarget() == main_area){
                System.out.println("AAUYGWEUA");
                settings_overlay.setVisible(false);
            }
        });
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton rb = (RadioButton)tg.getSelectedToggle();
                if(rb == radio_en){
                    updateTranslations();
                    langToggle.setLocale(Locale.ENGLISH);
                    renderWorkspace();
                    System.out.println("YIPII");
                } else if (rb == radio_fi){
                    updateTranslations();
                    langToggle.setLocale(languageToggle.FINNISH);
                    renderWorkspace();
                    System.out.println("YIPII");
                } else if (rb == radio_ru){
                    updateTranslations();
                    langToggle.setLocale(languageToggle.RUSSIAN);
                    renderWorkspace();
                    System.out.println("YIPII");
                } else {
                    System.out.println("uhhhhhhhhhhhhhhhh no lang?");
                }
            }
        });

        //For some random ass reason the menubutton comes with "Action 1" and "Action 2" options by default.... needs to be cleared.
        add_btn.getItems().clear();
        Add_tab.setOnAction(event -> { addNewTab();});
        m_1 = new MenuItem(langToggle.getString("addTabBtn"));
        m_2 = new MenuItem(langToggle.getString("addCategoryBtn"));
        m_1.setOnAction(event -> { addNewTab();});
        m_2.setOnAction(event -> { addNewCategory();});
        add_btn.getItems().addAll(m_1, m_2);
        loadWorkingArea();
    }

    public void updateTranslations(){
        Platform.runLater(() ->{
            //#YanDecCore
            if(m_1!=null) m_1.setText(langToggle.getString("addTabBtn"));
            if(m_2!=null) m_2.setText(langToggle.getString("addCategoryBtn"));
            if(add_btn!=null) add_btn.setText(langToggle.getString("addMenu"));
            if(Add_tab!=null) Add_tab.setText(langToggle.getString("addTabBtn"));
            if(lang_title!=null) lang_title.setText(langToggle.getString("langTitle"));
            if(colourScheme_title!=null) colourScheme_title.setText(langToggle.getString("cso_title"));
            if(exit_btn!=null) exit_btn.setText(langToggle.getString("exitBtn"));
        });
    }

    public void loadWorkingArea(){
        noteSpace_view.getChildren().clear();
        tab_column.getChildren().clear();

        //replace later with code to get stuff from the DB
        addNewTab();
    }
    public void openTab(Workspace workspace){
        //clear workspace
        //fetch data for the specific tab based on some sort of ID
        System.out.println("On this tab!");
        currentActiveWorkspace = workspace;
        currentTabName_title.setText(currentActiveWorkspace.getName());
        renderWorkspace();
    }

    private void renderWorkspace(){
        noteSpace_view.getChildren().clear();
        if (currentActiveWorkspace == null) return;

        for(Category category : currentActiveWorkspace.getCategories()) {
            noteSpace_view.getChildren().add(buildCategoryNode(category));
        }
    }

    public void addNewTab(){
        System.out.println("ADDING NEW WORKSPACE TAB!!!");
        Workspace workspace = new Workspace(langToggle.getString("newTab"));
        Button tab_btn = new Button(workspace.name);
        tab_btn.getStyleClass().add("column_btn");
        tab_btn.setMaxWidth(Double.MAX_VALUE);
        tab_btn.setOnAction(event -> {openTab(workspace);});
        MenuItem o_1 = new MenuItem(langToggle.getString("editBtn"));
        MenuItem o_2 = new MenuItem(langToggle.getString("deleteBtn"));
        MenuButton m = new MenuButton("✎", null, o_1, o_2);
        o_1.setOnAction(event -> { editTab(workspace, tab_btn, m);});
        o_2.setOnAction(event -> { tab_column.getChildren().remove(tab_btn);});
        tab_btn.setGraphic(m);
        tab_column.getChildren().add(tab_btn);
        tab_column.setFillWidth(true);
        openTab(workspace);
    }
    private void editTab(Workspace workspace, Button tabbtn, MenuButton m){
        HBox container = new HBox(5);
        TextField nameField = new TextField(workspace.getName());
        Button confirm = new Button("✓");
        Button cancel = new Button("X");
        confirm.getStyleClass().add("editing_btns");
        cancel.getStyleClass().add("editing_btns");
        container.setFillHeight(true);
        container.setMaxWidth(Double.MAX_VALUE);
        container.setAlignment(Pos.CENTER_LEFT);
        container.getChildren().addAll(nameField, confirm, cancel);

        HBox.setHgrow(nameField, Priority.ALWAYS);
        nameField.setMinWidth(100);
        nameField.setMaxWidth(Double.MAX_VALUE);

        confirm.setOnAction(event -> {
            String newName = nameField.getText().trim();
            if(!newName.isEmpty()){
                workspace.setName(newName);
                tabbtn.setText(newName);
                if (currentActiveWorkspace == workspace){
                    currentTabName_title.setText(newName);
                }
                tabbtn.setGraphic(m);
            }
            renderWorkspace();
        });
        cancel.setOnAction(event -> {
            tabbtn.setText(workspace.getName());
            tabbtn.setGraphic(m);
        });


        tabbtn.setText("");
        tabbtn.setGraphic(container);
        tabbtn.setMaxWidth(Double.MAX_VALUE);
        tabbtn.setMinWidth(0);
        tabbtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        //can you see the fucking struggle I have to get the elements to fit? Jokes on me its useless :DDDDDD
    }

    public void addNewCategory(){
        if (currentActiveWorkspace == null) return;

        Category category = new Category(langToggle.getString("newCategory"));
        currentActiveWorkspace.createCategory(category);
        renderWorkspace();
    }
    private void addNoteToCategory(Category category){
        System.out.println("Happening");
        Note note = new Note("New Note");
        note.setContent("");
        category.addNotes(note);
        renderWorkspace();
    }
    private VBox buildCategoryNode(Category category){
        VBox categoryColumn = new VBox();
        categoryColumn.getStyleClass().add("category_view");
        HBox categoryTitle = new HBox();
        categoryTitle.getStyleClass().add("category_title");
        Label categoryName = new Label(category.getName());
        Button addNote = new Button("+");
        Button categoryEdit = new Button("✎");
        Button categoryDelete = new Button(":(");
        addNote.setOnAction(event -> {addNoteToCategory(category);});
        categoryEdit.setOnAction(event -> { editCategory(category, categoryTitle);});
        categoryDelete.setOnAction(event -> {
            currentActiveWorkspace.removeCategory(category);
            renderWorkspace();
        });
        categoryEdit.getStyleClass().add("editing_btns");

        categoryTitle.getChildren().addAll(categoryName, addNote, categoryEdit, categoryDelete);

        VBox notesContainer = new VBox();
        for(Note note : category.getNotes()){
            notesContainer.getChildren().add(buildNoteNode(category, note));
        }
        categoryColumn.getChildren().addAll(categoryTitle, notesContainer);
        return categoryColumn;
    }
    public void editCategory(Category category, HBox categoryTitle){
        TextField nameField = new TextField(category.getName());
        nameField.setPrefWidth(160);
        Button confirm = new Button("✓");
        Button cancel = new Button("x");
        confirm.setOnAction(event -> {
            String newName = nameField.getText().trim();
            if(!newName.isEmpty()) {
                category.setName(newName);
            }
            renderWorkspace();
        });
        cancel.setOnAction(event -> {
            renderWorkspace();
        });
        categoryTitle.getChildren().clear();
        categoryTitle.getStyleClass().add("category_title");
        categoryTitle.getChildren().addAll(nameField, confirm, cancel);
    }

    private void editNotes(Note note, VBox noteInstance){
        TextField titleField = new TextField(note.getTitle());
        TextArea contentField = new TextArea(note.getContent());
        contentField.setWrapText(true);

        Button confirm = new Button("✓");
        Button cancel = new Button("X");

        confirm.setOnAction(event -> {
            note.setTitle(titleField.getText().trim());
            note.setContent(contentField.getText());
            renderWorkspace();
        });
        cancel.setOnAction(event -> {
            renderWorkspace();
        });

        noteInstance.getChildren().clear();
        noteInstance.getStyleClass().add("note_card");
        VBox editor = new VBox();
        editor.getChildren().addAll(titleField, contentField, confirm, cancel);
        noteInstance.getChildren().add(editor);
    }
    private VBox buildNoteNode(Category category, Note note){
        VBox noteInstance = new VBox();
        noteInstance.getStyleClass().add("note_card");

        HBox noteTitle = new HBox();
        Label noteName = new Label(note.getTitle());
        Button editNote = new Button("✎");
        Button deleteNote = new Button(langToggle.getString("deleteBtn"));

        editNote.setOnAction(event -> editNotes(note, noteInstance));
        deleteNote.setOnAction(event -> {
            category.removeNotes(note);
            renderWorkspace();
        });

        VBox noteContents = new VBox();
        noteContents.setFillWidth(true);
        Label theStuff = new Label(note.getContent());
        theStuff.setWrapText(true);
        noteInstance.setMaxWidth(Double.MAX_VALUE);
        theStuff.maxWidthProperty().bind(noteInstance.widthProperty().subtract(10));
        noteContents.getChildren().add(theStuff);
        noteTitle.getChildren().addAll(noteName, editNote, deleteNote);
        noteInstance.getChildren().addAll(noteTitle, noteContents);
        return noteInstance;
    }
}
