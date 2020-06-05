package skyvssea.database;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import skyvssea.controller.Controller;
import skyvssea.model.*;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;
import skyvssea.view.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoadHandler {

    private int col;
    private int row;
    private String currentGameStateString;
    private int registeredPieceID;
    private int registeredTileX;
    private int registeredTileY;
    private int currentPlayerID;

    private List<Tile> tilesWithPiece = new ArrayList<>();
    private List<Tile> tilesWithObstacle = new ArrayList<>();
    private List<Pair<Integer, AbstractPiece>> pieceIDPairs = new ArrayList<>();

    public void loadGame(Stage stage) {
        stage.close();

        Controller controller = new Controller();

        loadCoreGameData();

        // Views
        BoardPane boardPane = new BoardPane(controller, col, row);
        ActionPane actionPane = new ActionPane(controller);
        MainControlPane primaryPane = new MainControlPane(boardPane, actionPane);
        InfoPane infoPane = new InfoPane();
        MainView root = new MainView(primaryPane, infoPane);

        // Models - Managers
        Board board = new Board(col, row);
        PieceManager pieceManager = loadPieceManager(board);
        PlayerManager playerManager = new PlayerManager(pieceManager.getEaglePieces(), pieceManager.getSharkPieces());
        Game game = new Game();
        loadObstacleManager(board);
        loadSpecialEffectManager();

        board.setRegisteredTile(board.getTile(registeredTileX, registeredTileY));
        game.setCurrentGameState(GameState.valueOf(currentGameStateString));
        // Nick - May be startNewTurn? if READYTOMOVE
        playerManager.setCurrentPlayer(currentPlayerID);

        // Link GameObject with their Avatar
        controller.setTiles(boardPane, board);
        controller.setPieces(boardPane, board, tilesWithPiece, pieceManager, playerManager);
        controller.setObstacles(boardPane, board, tilesWithObstacle);

        controller.loadController(stage, boardPane, actionPane, infoPane, board, pieceManager, playerManager, game);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Sky vs. Sea");
        stage.setResizable(true);
        stage.show();

        controller.updateUI();
    }

    private PieceManager loadPieceManager(Board board) {
        List<AbstractPiece> eaglePieces = new ArrayList<>();
        List<AbstractPiece> sharkPieces = new ArrayList<>();

        AbstractPiece registeredPiece = null;

        try {
            String query = "SELECT * FROM " + SVSDatabase.PIECE_TABLE;
            PreparedStatement stmt = SVSDatabase.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Hierarchy hierarchy;
                AbstractPieceFactory abstractPieceFactory;
                AbstractPiece piece = null;

                String pieceName = rs.getString("PieceName");
                if (pieceName.contains("Big")) {
                    hierarchy = Hierarchy.BIG;
                } else if (pieceName.contains("Medium")) {
                    hierarchy = Hierarchy.MEDIUM;
                } else if (pieceName.contains("Small")) {
                    hierarchy = Hierarchy.SMALL;
                } else {
                    hierarchy = Hierarchy.BABY;
                }

                // Instantiate piece
                if (pieceName.contains("Eagle")) {
                    piece = EagleFactory.getInstance().createPiece(hierarchy);
                    eaglePieces.add(piece);
                } else {
                    piece = SharkFactory.getInstance().createPiece(hierarchy);
                    sharkPieces.add(piece);
                }

                piece.setSpecialEffectCounter(rs.getInt("SpecialEffectCounter"));
                piece.setPassiveEffectActivated(rs.getBoolean("IsPassiveEffectActivated"));

                // Create a link between PieceID of database and the piece for ease of identification
                Pair<Integer, AbstractPiece> pieceIDPair = new Pair<>(rs.getInt("PieceID"), piece);
                pieceIDPairs.add(pieceIDPair);

                // Set piece on tile
                Tile tile = board.getTile(rs.getInt("TileX"), rs.getInt("TileY"));
                tile.setGameObject(piece);
                tilesWithPiece.add(tile);

                // Identify registeredPiece
                if (registeredPieceID == pieceIDPair.getKey()) {
                    registeredPiece = piece;
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PieceManager pieceManager = new PieceManager(eaglePieces, sharkPieces);
        pieceManager.setRegisteredPiece(registeredPiece);
        return pieceManager;
    }

    private void loadObstacleManager(Board board) {
        try {
            String query = "SELECT * FROM " + SVSDatabase.OBSTACLE_TABLE;
            PreparedStatement stmt = SVSDatabase.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tile tile = board.getTile(rs.getInt("TileX"), rs.getInt("TileY"));
                Obstacle obstacle = new Obstacle();
                tile.setGameObject(obstacle);
                tilesWithObstacle.add(tile);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSpecialEffectManager() {
        try {
            String query = "SELECT * FROM " + SVSDatabase.SPECIAL_EFFECT_MANAGER_TABLE;
            PreparedStatement stmt = SVSDatabase.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Piece
                int pieceID = rs.getInt("PieceID");
                AbstractPiece piece = getPiece(pieceID);

                int casterID = rs.getInt("CasterID");
                AbstractPiece caster = getPiece(casterID);

                // SpecialEffect
                String specialEffectString = rs.getString("SpecialEffect");
                SpecialEffectCode specialEffectCode = SpecialEffectCode.fromString(specialEffectString);
                SpecialEffectObject specialEffect = SpecialEffectFactory.getInstance().createSpecialEffect(specialEffectCode, caster);

                piece.getSpecialEffectManager().add(specialEffect);
                specialEffect.setEffectiveDuration(rs.getInt("EffectiveDuration"));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCoreGameData() {
        try {
            String query = "SELECT * FROM " + SVSDatabase.CORE_GAME_TABLE;
            PreparedStatement stmt = SVSDatabase.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                currentGameStateString = rs.getString("CurrentState");
                col = rs.getInt("Col");
                row = rs.getInt("Row");
                registeredPieceID = rs.getInt("RegisteredPiece");
                registeredTileX = rs.getInt("RegisteredTileX");
                registeredTileY = rs.getInt("RegisteredTileY");
                currentPlayerID =  rs.getInt("CurrentPlayer");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private AbstractPiece getPiece(int pieceID) {
        for (Pair<Integer, AbstractPiece> pair : pieceIDPairs) {
            if (pair.getKey().intValue() == pieceID) {
                return pair.getValue();
            }
        }
        return null;
    }
}
