package glowstone.main;
import glowstone.model.DBConnector;
import glowstone.view.AppView;

public class Main {
    public static void main(String[] args) {
        try {
            DBConnector.getInstance("glowstone");
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        AppView.launch(AppView.class);
    }
}