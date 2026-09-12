package glowstone.controller;
import glowstone.model.Category;
import glowstone.model.Note;
import glowstone.model.Workspace;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

    private Workspace currentActiveWorkspace;

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
        Workspace workspace = new Workspace("New Tab");
        Button tab_btn = new Button(workspace.name);
        tab_btn.getStyleClass().add("column_btn");
        tab_btn.setMaxWidth(Double.MAX_VALUE);
        tab_btn.setOnAction(event -> {openTab(workspace);});
        MenuItem o_1 = new MenuItem("Edit");
        MenuItem o_2 = new MenuItem("Delete");
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

        Category category = new Category("New category");
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
        Button confirm = new Button("Yas");
        Button cancel = new Button("nvm");
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
        Button deleteNote = new Button("Delete");

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
