package glowstone.main;
import glowstone.view.AppView;

import glowstone.model.DB;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        AppView.launch(AppView.class);
        try {
            DB.startConnection();
            DB.getFromDB("NOTE", 1);
            DB.endConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}