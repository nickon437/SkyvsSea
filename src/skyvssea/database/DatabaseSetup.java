package skyvssea.database;

import java.sql.*;

public class DatabaseSetup {

    public static void rebuildDatabase() {
        dropTable(SVSDatabase.CORE_GAME_TABLE);
        dropTable(SVSDatabase.OBSTACLE_TABLE);
        dropTable(SVSDatabase.SPECIAL_EFFECT_MANAGER_TABLE);
        dropTable(SVSDatabase.PIECE_TABLE);

        setupDatabase();
    }

    public static void setupDatabase() {
        createPieceTable();
        createSpecialEffectManagerTable();
        createObstacleTable();
        createCoreGameTable();
    }

    private static void dropTable(String tableName) {
        try {
            PreparedStatement stmt = SVSDatabase.getConnection().prepareStatement("DROP TABLE " + tableName);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createCoreGameTable() {
        try {
            String table_name = SVSDatabase.CORE_GAME_TABLE;

            // Determine if the table already exists or not.
            Connection con = SVSDatabase.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, table_name, null);

            if (!rs.next()) {
                String query = "CREATE TABLE " + table_name + " (" +
                        "GameID             INT NOT NULL PRIMARY KEY " +
                        "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "CurrentState       VARCHAR(50) NOT NULL, " +
                        "CurrentPlayer      INT NOT NULL, " +
                        "RegisteredPiece    INT, " +
                        "RegisteredTileX    INT, " +
                        "RegisteredTileY    INT, " +
                        "Col                INT NOT NULL, " +
                        "Row                INT NOT NULL)";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createObstacleTable() {
        try {
            String table_name = SVSDatabase.OBSTACLE_TABLE;

            // Determine if the table already exists or not.
            Connection con = SVSDatabase.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, table_name, null);

            if (!rs.next()) {
                String query = "CREATE TABLE " + table_name + " (" +
                        "ObstacleID         INT NOT NULL PRIMARY KEY " +
                        "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "TileX    INT NOT NULL, " +
                        "TileY    INT NOT NULL)";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPieceTable() {
        try {
            String table_name = SVSDatabase.PIECE_TABLE;

            // Determine if the table already exists or not.
            Connection con = SVSDatabase.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, table_name, null);

            if (!rs.next()) {
                String query = "CREATE TABLE " + table_name + " (" +
                        "PieceID            INT NOT NULL PRIMARY KEY " +
                        "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "PieceName          VARCHAR(20) NOT NULL, " +
                        "TileX    INT NOT NULL, " +
                        "TileY    INT NOT NULL, " +
                        "SpecialEffectCounter INT NOT NULL)";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createSpecialEffectManagerTable() {
        try {
            String table_name = SVSDatabase.SPECIAL_EFFECT_MANAGER_TABLE;

            // Determine if the table already exists or not.
            Connection con = SVSDatabase.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, table_name, null);

            if (!rs.next()) {
                String query = "CREATE TABLE " + table_name + " (" +
                        "PieceID            INT NOT NULL REFERENCES " + SVSDatabase.PIECE_TABLE + "(PieceID), " +
                        "SpecialEffect      VARCHAR(20) NOT NULL, " +
                        "EffectiveDuration  INT NOT NULL)";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
