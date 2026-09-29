package glowstone.controller;
import glowstone.model.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import java.util.Locale;


public class appControlls {
    private final languageToggle langToggle = new languageToggle(Locale.ENGLISH);
    private final SwitchTheme themeSwitcher = new SwitchTheme();
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
    private HBox center_Hbox;
    @FXML
    private ScrollPane center_scrollbar;
    @FXML
    private ScrollPane left_scrollpane;
    @FXML
    private RadioButton radio_en;
    @FXML
    private RadioButton radio_fi;
    @FXML
    private RadioButton radio_ru;
    @FXML
    private Button light_mode;
    @FXML
    private Button dark_mode;
    @FXML
    private Button blue_mode;
    @FXML
    private Button pink_mode;
    @FXML
    private Button eyestrain_mode;
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

    private boolean checkNote = false;

    @FXML
    public void initialize() throws SQLException {
        tg = new ToggleGroup();
        left_scrollpane.setFitToWidth(true);
        center_scrollbar.setFitToWidth(false);

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
                    langToggle.setLocale(Locale.ENGLISH);
                    updateTranslations();
                    renderWorkspace();
                } else if (rb == radio_fi){
                    langToggle.setLocale(languageToggle.FINNISH);
                    updateTranslations();
                    renderWorkspace();
                } else if (rb == radio_ru){
                    langToggle.setLocale(languageToggle.RUSSIAN);
                    updateTranslations();
                    renderWorkspace();
                } else {
                    System.out.println("uhhhhhhhhhhhhhhhh no lang?");
                }
            }
        });
        light_mode.setOnAction(event -> {themeSwitcher.applyTheme(scene_stackpane, "light");});
        blue_mode.setOnAction(event -> {themeSwitcher.applyTheme(scene_stackpane, "blue");});
        dark_mode.setOnAction(event -> {themeSwitcher.applyTheme(scene_stackpane, "dark");});
        pink_mode.setOnAction(event -> {themeSwitcher.applyTheme(scene_stackpane, "pink");});
        eyestrain_mode.setOnAction(event -> {themeSwitcher.applyTheme(scene_stackpane, "eyestrain");});

        //For some random ass reason the menubutton comes with "Action 1" and "Action 2" options by default.... needs to be cleared.
        add_btn.getItems().clear();
        m_1 = new MenuItem(langToggle.getString("addTabBtn"));
        m_2 = new MenuItem(langToggle.getString("addCategoryBtn"));
        Add_tab.setOnAction(event -> { loadTab(addNewTab());});
        m_1.setOnAction(event -> { loadTab(addNewTab());});
        m_2.setOnAction(event -> { addNewCategory();});
        add_btn.getItems().addAll(m_1, m_2);
        filter_btn.setOnAction(event -> {checkNote = !checkNote;
            System.out.println(checkNote);});
        search_btn.setOnAction(event -> {
            String text = search_field.getText().trim();
            if(text.startsWith("#")){
                text = text.replace("#","");
                if(checkNote){
                    loadFilteredNote(text, true);
                }else {
                    loadFilteredCategories(text, true);
                }
            }else{
                if(checkNote){
                    loadFilteredNote(text, true);
                }else {
                    loadFilteredCategories(text, false);
                }
            }

        });

        DB.startConnection();
        loadWorkingArea();
    }

    public void updateTranslations(){
        Platform.runLater(() ->{
            if(m_1!=null) m_1.setText(langToggle.getString("addTabBtn"));
            if(m_2!=null) m_2.setText(langToggle.getString("addCategoryBtn"));
            if(add_btn!=null) add_btn.setText(langToggle.getString("addMenu"));
            if(Add_tab!=null) Add_tab.setText(langToggle.getString("addTabBtn"));
            if(lang_title!=null) lang_title.setText(langToggle.getString("langTitle"));
            if(colourScheme_title!=null) colourScheme_title.setText(langToggle.getString("cso_title"));
            if(exit_btn!=null) exit_btn.setText(langToggle.getString("exitBtn"));
            for(Node n : tab_column.getChildren()){
                if(n instanceof Button){
                    Button b = (Button) n;
                    if(b.getGraphic() instanceof MenuButton){
                        MenuButton mb = (MenuButton) b.getGraphic();
                        for(MenuItem mi : mb.getItems()){
                            String id = mi.getId();
                            if("editTab".equals(id)) mi.setText(langToggle.getString("editBtn"));
                            else if("deleteTab".equals(id)) mi.setText(langToggle.getString("deleteBtn"));
                        }
                    }
                }
            }
        });
    }

    public void loadWorkingArea() throws SQLException {
        noteSpace_view.getChildren().clear();
        tab_column.getChildren().clear();

        //replace later with code to get stuff from the DB   <- still needs to be done, but I don't wanna (yet) :p -O
        ResultSet tabs = DB.readWholeTableFromDB("NOTE_TAB");
        while(tabs.next()){
            Workspace tab = new Workspace(tabs.getString("name"));
            tab.setId(tabs.getInt("id"));
            ResultSet groups = DB.readGroupByTab(tab.getId());
            while(groups.next()){
                Category category = new Category(groups.getString("name"));
                category.setId(groups.getInt("id"));
                tab.createCategory(category);
                ResultSet notes = DB.readNoteByGroup(category.getId());
                while(notes.next()){
                    Note note = new Note(notes.getString("name"));
                    note.setId(notes.getInt("id"));
                    note.setContent(notes.getString("content"));
                    category.addNotes(note);
                    System.out.println("Loaded Note with: "+note.getId()+" and category: "+note.getParentId());
                }
                System.out.println("Loaded category with: "+category.getId()+" and tab: "+category.getParentId());
            }
            loadTab(tab);
            System.out.println("Loaded tab with: "+tab.getId());
        }
        //if(tab_column.getChildren().isEmpty()){loadTab(addNewTab());} //idk if needed

    }
    public void openTab(Workspace workspace){
        //clear workspace
        //fetch data for the specific tab based on some sort of ID
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
    private void loadFilteredCategories(String filter, boolean isTag) {
        currentActiveWorkspace.clearCategoryToShow();
        if(isTag){
            for (Category c : currentActiveWorkspace.getCategories()){
                for (CategoryTag tag : c.getTags()) {
                    if (tag.getName().contains(filter)) {
                        System.out.println(c.getName());
                        currentActiveWorkspace.addCategoryToShow(c);
                    }
                }
            }
        }else {
            for (Category c : currentActiveWorkspace.getCategories()){
                if(c.getName().contains(filter)){
                    System.out.println(c.getName());
                    currentActiveWorkspace.addCategoryToShow(c);}
            }
        }
        renderFilteredWorkspace();
    }
    private void loadFilteredNote(String filter, boolean isTag){
        currentActiveWorkspace.clearCategoryToShow();
        for (Category c : currentActiveWorkspace.getCategories()){
            if(isTag){
                for (Note n : c.getNotes()) {
                    for(NoteTag t : n.getTags()){
                        if(t.getName().contains(filter)){
                            currentActiveWorkspace.addCategoryToShow(c);
                            break;
                        }
                    }
                }
            }else {
                for (Note n : c.getNotes()) {
                    if (n.getTitle().contains(filter)) {
                        currentActiveWorkspace.addCategoryToShow(c);
                        break;
                    }
                }
            }
        }
        renderFilteredWorkspace();
    }

    private void renderFilteredWorkspace(){
        noteSpace_view.getChildren().clear();
        if (currentActiveWorkspace == null) return;

        for(Category category : currentActiveWorkspace.getCategoriesToShow()) {
            System.out.println(category.getName());
            noteSpace_view.getChildren().add(buildCategoryNode(category));
        }
    }

    public Workspace addNewTab(){
        System.out.println("ADDING NEW WORKSPACE TAB!!!");
        Workspace workspace = new Workspace(langToggle.getString("newTab"));
        int tabId = DB.insertTabToDB(workspace.getName(),0);//thumbnail stuff is missing
        workspace.setId(tabId);
        System.out.println("Tab with id: "+workspace.getId()+ ", should be: "+tabId);
        return workspace;
    }
    public void loadTab(Workspace workspace){
        Button tab_btn = new Button(workspace.name);
        tab_btn.getStyleClass().add("column_btn");
        tab_btn.setMaxWidth(Double.MAX_VALUE);
        tab_btn.setOnAction(event -> {openTab(workspace);});
        MenuItem o_1 = new MenuItem(langToggle.getString("editBtn"));
        MenuItem o_2 = new MenuItem(langToggle.getString("deleteBtn"));
        MenuButton m = new MenuButton("✎", null, o_1, o_2);
        o_1.setId("editTab");
        o_2.setId("deleteTab");
        o_1.setOnAction(event -> { editTab(workspace, tab_btn, m);});
        o_2.setOnAction(event -> { tab_column.getChildren().remove(tab_btn);DB.deleteTabFromDB(workspace.getId());});
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
                DB.updateTabInDB(workspace.getId(),workspace.getName(),0);//thumbnail stuff is missing
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
        int id = DB.insertGroupToDB(category.getName(), currentActiveWorkspace.getId(),0);
        category.setId(id);
        category.setParentId(currentActiveWorkspace.getId());
        System.out.println("Category Id with TabID: "+category.getId()+" / "+category.getParentId()+", C.Id should be: "+id);
        currentActiveWorkspace.createCategory(category);
        renderWorkspace();
    }
    private void addNoteToCategory(Category category){
        Note note = new Note(langToggle.getString("noteTitle"));
        note.setContent("");
        int id = DB.insertNoteToDB(note.content, note.title, category.getId(), 0); //still the thing with the thing which is the thing with... thumbnail..
        note.setId(id);
        category.addNotes(note);
        System.out.println("Added Note with: "+note.getId()+" with category: "+note.getParentId());
        renderWorkspace();
    }
    private VBox buildCategoryNode(Category category){
        VBox categoryColumn = new VBox();
        categoryColumn.getStyleClass().add("category_view");
        HBox categoryTitle = new HBox();
        categoryTitle.getStyleClass().add("category_title");
        Label categoryName = new Label(category.getName());
        Pane pane = new Pane();
        HBox.setHgrow(pane, Priority.ALWAYS);
        pane.getChildren().add(categoryName);
        Button addNote = new Button("+");
        Button categoryEdit = new Button("✎");
        Button categoryDelete = new Button(":(");
        addNote.setOnAction(event -> {addNoteToCategory(category);});
        categoryEdit.setOnAction(event -> { editCategory(category, categoryTitle);});
        categoryDelete.setOnAction(event -> {
            currentActiveWorkspace.removeCategory(category);
            DB.deleteGroupFromDB(category.getId());
            renderWorkspace();
        });

        categoryTitle.getChildren().addAll(pane, addNote, categoryEdit, categoryDelete);

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
                DB.updateGroupInDB(category.getId(), category.getName(), currentActiveWorkspace.getId(),0); // yay thumbnail :D
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
            DB.updateNoteInDB(note.getId(),note.getContent(),note.getTitle(),note.getParentId(),0); //thumbnail thingy
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
        noteTitle.getStyleClass().add("note_title");
        Label noteName = new Label(note.getTitle());
        Pane pane = new Pane();
        HBox.setHgrow(pane, Priority.ALWAYS);
        pane.getChildren().add(noteName);
        Button editNote = new Button("✎");
        Button deleteNote = new Button(langToggle.getString("deleteBtn"));

        editNote.setOnAction(event -> editNotes(note, noteInstance));
        deleteNote.setOnAction(event -> {
            category.removeNotes(note);
            DB.deleteNoteFromDB(note.getId());
            renderWorkspace();
        });

        VBox noteContents = new VBox();
        noteContents.setFillWidth(true);
        Label theStuff = new Label(note.getContent());
        theStuff.setWrapText(true);
        noteInstance.setMaxWidth(Double.MAX_VALUE);
        theStuff.maxWidthProperty().bind(noteInstance.widthProperty().subtract(10));
        noteContents.getChildren().add(theStuff);
        noteTitle.getChildren().addAll(pane, editNote, deleteNote);
        noteInstance.getChildren().addAll(noteTitle, noteContents);
        return noteInstance;
    }
}
