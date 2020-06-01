package skyvssea.database;

import java.sql.*;

public class DatabaseSetup {

    public static void buildDatabase() {
        dropTable(SVSDatabase.GAME_TABLE);
        dropTable(SVSDatabase.OBSTACLE_TABLE);
        dropTable(SVSDatabase.SPECIAL_EFFECT_MANAGER_TABLE);
        dropTable(SVSDatabase.PIECE_TABLE);
        dropTable(SVSDatabase.SPECIAL_EFFECT_TABLE);
        dropTable(SVSDatabase.PLAYER_TABLE);

        createPlayerTable();
        createSpecialEffectTable();
        createPieceTable();
        createSpecialEffectManagerTable();
        createObstacleTable();
        createGameTable();
    }

    private static void dropTable(String tableName) {
        try {
            PreparedStatement stmt = SVSDatabase.getConnection().prepareStatement("DROP TABLE " + tableName);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createGameTable() {
        try {
            String table_name = SVSDatabase.GAME_TABLE;

            // Determine if the table already exists or not.
            Connection con = SVSDatabase.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, table_name, null);

            if (!rs.next()) {
                String query = "CREATE TABLE " + table_name + " (" +
                        "GameID             INT NOT NULL PRIMARY KEY " +
                        "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "CurrentState       VARCHAR(20) NOT NULL, " +
                        "CurrentPlayer      INT NOT NULL, " +
                        "FOREIGN KEY (CurrentPlayer) REFERENCES " + SVSDatabase.PLAYER_TABLE + "(PlayerID), " +
                        "RegisteredPiece    INT, " +
                        "FOREIGN KEY (RegisteredPiece) REFERENCES " + SVSDatabase.PIECE_TABLE + "(PieceID), " +
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
                        "RegisteredTileX    INT NOT NULL, " +
                        "RegisteredTileY    INT NOT NULL)";
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
                        "PieceType          VARCHAR(20) NOT NULL, " +
                        "SpecialEffect      VARCHAR(20) NOT NULL)," +
                        "(SpecialEffectID) FOREIGN KEY REFERENCES " + SVSDatabase.SPECIAL_EFFECT_TABLE + "(SpecialEffectID), " +
                        "RegisteredTileX    INT NOT NULL, " +
                        "RegisteredTileY    INT NOT NULL, " +
                        "Player             INT NOT NULL, " +
                        "FOREIGN KEY (Player) REFERENCES " + SVSDatabase.PLAYER_TABLE + "(PlayerID))";
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
                        "PieceID            INT NOT NULL, " +
                        "(PieceID) FOREIGN KEY REFERENCES " + SVSDatabase.PIECE_TABLE + "(PieceID), " +
                        "SpecialEffect      VARCHAR(20) NOT NULL," +
                        "(SpecialEffectID) FOREIGN KEY REFERENCES " + SVSDatabase.SPECIAL_EFFECT_TABLE + "(SpecialEffectID), " +
                        "EffectiveDuration  INT)";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createSpecialEffectTable() {
        try {
            String table_name = SVSDatabase.SPECIAL_EFFECT_TABLE;

            // Determine if the table already exists or not.
            Connection con = SVSDatabase.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, table_name, null);

            if (!rs.next()) {
                String query = "CREATE TABLE " + table_name + " (" +
                        "SpecialEffectID    VARCHAR(20) NOT NULL PRIMARY KEY)";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPlayerTable() {
        try {
            String table_name = SVSDatabase.PLAYER_TABLE;

            // Determine if the table already exists or not.
            Connection con = SVSDatabase.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, table_name, null);

            if (!rs.next()) {
                String query = "CREATE TABLE " + table_name + " (" +
                        "PlayerID INT NOT NULL PRIMARY KEY " +
                        "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1))";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
