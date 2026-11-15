package glowstone.controller;
import javafx.scene.Parent;

public class SwitchTheme {
    private final String lightThemeFile = getClass().getResource("/styles/styles_light.css").toExternalForm();
    private final String blueThemeFile = getClass().getResource("/styles/styles_lightblue.css").toExternalForm();
    private final String darkThemeFile = getClass().getResource("/styles/styles_dark.css").toExternalForm();
    private final String pinkThemeFile = getClass().getResource("/styles/styles_pink.css").toExternalForm();
    private final String eyestrainThemeFile = getClass().getResource("/styles/styles_eyestrain.css").toExternalForm();

    public SwitchTheme() {}

    public void applyTheme(Parent root, String theme) {
        //Making this into a switch case could be a more elegant solution, but I am not elegant
        root.getStylesheets().clear();
        if (theme.equals("light")) {
            root.getStylesheets().add(lightThemeFile);
        } else if (theme.equals("blue")){
            root.getStylesheets().add(blueThemeFile);
        } else if (theme.equals("dark")) {
            root.getStylesheets().add(darkThemeFile);
        } else if (theme.equals("pink")){
            root.getStylesheets().add(pinkThemeFile);
        } else if (theme.equals("eyestrain")){
            System.out.println("Switching to eyestrain theme... why...");
            root.getStylesheets().add(eyestrainThemeFile);
        }
        else {
            System.out.println("This bum ass developer made a typo in main controller");
        }
        root.applyCss();
    }
}
