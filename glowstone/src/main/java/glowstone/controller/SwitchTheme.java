package glowstone.controller;
import javafx.scene.Parent;
import java.util.Objects;

public class SwitchTheme {
    private final String lightThemeFile = Objects.requireNonNull(getClass().getResource("/styles/styles_light.css")).toExternalForm();
    private final String blueThemeFile = Objects.requireNonNull(getClass().getResource("/styles/styles_lightblue.css")).toExternalForm();
    private final String darkThemeFile = Objects.requireNonNull(getClass().getResource("/styles/styles_dark.css")).toExternalForm();
    private final String pinkThemeFile = Objects.requireNonNull(getClass().getResource("/styles/styles_pink.css")).toExternalForm();
    private final String eyestrainThemeFile = Objects.requireNonNull(getClass().getResource("/styles/styles_eyestrain.css")).toExternalForm();

    public SwitchTheme() {}

    public void applyTheme(Parent root, String theme) {
        root.getStylesheets().clear();
        switch (theme) {
            case "light" -> root.getStylesheets().add(lightThemeFile);
            case "blue" -> root.getStylesheets().add(blueThemeFile);
            case "dark" -> root.getStylesheets().add(darkThemeFile);
            case "pink" -> root.getStylesheets().add(pinkThemeFile);
            case "eyestrain" -> {
                System.out.println("Switching to eyestrain theme... why...");
                root.getStylesheets().add(eyestrainThemeFile);
            }
            default -> System.out.println("This bum ass developer made a typo in main controller");
        }
        root.applyCss();
    }
}
