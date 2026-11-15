package glowstone.controller;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageToggle {
    public static final Locale FINNISH = new Locale("fi", "FI");
    public static final Locale RUSSIAN = new Locale("ru", "RU");
    private ResourceBundle translations;

    public LanguageToggle(Locale initLocale){
        setLocale(initLocale);
    }
    public void setLocale(Locale newLocale){
        String bundleBasename = "Translations";
        this.translations = ResourceBundle.getBundle(bundleBasename, newLocale);
    }
    public String getString(String key){
        return translations.getString(key);
    }
}
