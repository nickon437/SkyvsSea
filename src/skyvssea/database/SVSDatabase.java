package skyvssea.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class SVSDatabase {

    private static final String DB_NAME = "svsdb";
    public static final String GAME_TABLE = "GAME";
    public static final String OBSTACLE_TABLE = "OBSTACLE";
    public static final String PIECE_TABLE = "PIECE";
    public static final String SPECIAL_EFFECT_MANAGER_TABLE = "PIECE";
    public static final String SPECIAL_EFFECT_TABLE  = "PIECE";
    public static final String PLAYER_TABLE = "PLAYER";

    private static Connection con;

    private SVSDatabase() {}

    public static Connection getConnection() {
        if (con == null) {
            try {
                // Registering the HSQLDB JDBC driver
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
                con = DriverManager.getConnection("jdbc:hsqldb:file:database/" + DB_NAME, "SA", "");
            } catch (Exception ex) {
                ex.getStackTrace();
            }
        }
        return con;
    }
}
