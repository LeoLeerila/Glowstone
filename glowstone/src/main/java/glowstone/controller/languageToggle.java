package glowstone.controller;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class languageToggle {
    private Locale locale;
    public static final Locale FINNISH = new Locale("fi", "FI");
    public static final Locale RUSSIAN = new Locale("ru", "RU");
    private ResourceBundle translations;
    private final String bundleBasename = "Translations";

    public languageToggle(Locale initLocale){
        setLocale(initLocale);
    }
    public void setLocale(Locale newLocale){
        this.locale = newLocale;
        this.translations = ResourceBundle.getBundle(bundleBasename, locale);
    }
    public void toggleLanguage(String lang){
        if(Objects.equals(lang, "en")){
            setLocale(Locale.ENGLISH);
        } else if (Objects.equals(lang, "fi")){
            setLocale(FINNISH);
        } else if(Objects.equals(lang, "ru")){
            setLocale(RUSSIAN);
            System.out.println("Switched to russian :3");
        } else {
            setLocale(Locale.ENGLISH);
        }
    }
    public String getString(String key){
        return translations.getString(key);
    }
}
