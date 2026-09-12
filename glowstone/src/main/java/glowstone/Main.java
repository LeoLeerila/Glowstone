package glowstone;

import glowstone.model.DB;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            DB.startConnection();
            DB.getFromDB("note", 1);
            DB.endConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}