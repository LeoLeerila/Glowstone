package glowstone.controller;

import javafx.application.Platform;
import javafx.scene.Parent;

public class SwitchTheme {
    private final String lightThemeFile = getClass().getResource("/styles/styles_light.css").toExternalForm();
    private final String blueThemeFile = getClass().getResource("/styles/styles_lightblue.css").toExternalForm();
    private final String darkThemeFile = getClass().getResource("/styles/styles_dark.css").toExternalForm();
    private final String pinkThemeFile = getClass().getResource("/styles/styles_pink.css").toExternalForm();
    private final String eyestrainThemeFile = getClass().getResource("/styles/styles_eyestrain.css").toExternalForm();

    public SwitchTheme() {}

    public void applyTheme(Parent root, String theme) {
        if (theme.equals("light")) {
            System.out.println("Switching to light theme!");
            root.getStylesheets().clear();
            root.getStylesheets().add(lightThemeFile);
            root.applyCss();
        } else if (theme.equals("blue")){
            System.out.println("Switching to blue theme!");
            root.getStylesheets().clear();
            root.getStylesheets().add(blueThemeFile);
            root.applyCss();
        } else if (theme.equals("dark")) {
            System.out.println("Switching to dark theme!");
            root.getStylesheets().clear();
            root.getStylesheets().add(darkThemeFile);
            root.applyCss();
        } else if (theme.equals("pink")){
            System.out.println("Switching to pink theme!");
            root.getStylesheets().clear();
            root.getStylesheets().add(pinkThemeFile);
            root.applyCss();
        } else if (theme.equals("eyestrain")){
            System.out.println("Switching to eyestrain theme... why...");
            root.getStylesheets().clear();
            root.getStylesheets().add(eyestrainThemeFile);
            root.applyCss();
        }
        else {
            System.out.println("This bum ass developer made a typo in main controller");
        }
    }
}
