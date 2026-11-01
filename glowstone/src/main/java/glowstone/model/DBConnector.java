package glowstone.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    private static Connection conn = null;
    private static String connectionUrl = "jdbc:mariadb://localhost:3306/";
    private static String connectionUser = "root";
    private static String connectionPassword = "example";
    private static final String DATABASESQL = "GlowstoneV2.sql";
    private static String databaseString;

    private static void connect(String db) {
        try {
            if (!DriverManager.getDrivers().hasMoreElements()) {
                System.out.println("you done fucked up now");
            }
            conn = DriverManager.getConnection(connectionUrl + db, connectionUser, connectionPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Connection getInstance(String db) throws SQLException {
        if (conn == null) {
            if (!db.contains("test")) {
                databaseString = db;
                createDB();
            }
            connect(db);
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }

    private static boolean checkDBExists(Connection conn) throws SQLException {
        boolean dbExists = false;

        // Source - https://stackoverflow.com/a/12414762
        // Posted by mguymon, modified by community. See post 'Timeline' for change history
        // Retrieved 2026-10-31, License - CC BY-SA 4.0
        ResultSet dbSet = conn.getMetaData().getCatalogs();
        // Iterate each catalog in the ResultSet
        while (dbSet.next()) {
            // Get the database name, which is at position 1
            if (dbSet.getString(1).contains(databaseString) && !dbSet.getString(1).contains("test")) {
                System.out.println("the DB is real");
                dbExists = true;
            }
        }
        dbSet.close();

        return dbExists;
    }

    private static void createDB() throws SQLException {
        Connection rootConnection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
        if (!checkDBExists(rootConnection)) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(DATABASESQL));
                Statement rootStatement = rootConnection.createStatement();
                StringBuilder builder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    // Skip empty lines and single-line comments.
                    if (line.isEmpty() || line.startsWith("--"))
                        continue;

                    builder.append(line);
                    if (line.endsWith(";"))
                    try {
                        // Execute the SQL command
                        rootStatement.execute(builder.toString());
                        // Print a success message along with
                        // the first 15 characters of the
                        // executed command.
                        System.out.println(
                            " Command successfully executed : "
                            + builder.substring(
                                0,
                                Math.min(builder.length(), 15))
                            + "...");
                        builder.setLength(0);
                    }
                    catch (SQLException e) {
                        // If an SQLException occurs during
                        // execution, print an error message and
                        // stop further execution.
                        System.err.println(e.getMessage() + "\n");
                    }
                }
                reader.close();

                //rootStatement.execute(builder.toString());

                boolean dbCreated = checkDBExists(rootConnection);
                if (dbCreated) {
                    System.out.println("new database created");
                } else {
                    System.out.println("db creation failed");
                }
                
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
    }
    
}
