package skyvssea.database;

import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.controller.Controller;
import skyvssea.model.*;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;
import skyvssea.view.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadHandler {

    private int col;
    private int row;
    private String currentGameStateString;
    private int registeredPieceID;
    private int registeredTileX;
    private int registeredTileY;
    private int currentPlayerID;
    private int player1NumUndos;
    private int player2NumUndos;

    private List<Tile> tilesWithPiece = new ArrayList<>();
    private List<Tile> tilesWithObstacle = new ArrayList<>();
    private Map<Integer, AbstractPiece> pieceMap = new HashMap<>();

    public void loadGame(Stage stage) {
        if (!loadCoreGameData()) return;

        stage.close();
        Controller controller = new Controller();

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
        playerManager.setCurrentPlayer(currentPlayerID);
        playerManager.getPlayer(0).setNumUndos(player1NumUndos);
        playerManager.getPlayer(1).setNumUndos(player2NumUndos);

        // Link GameObject with their Avatar
        controller.setTiles(boardPane, board);
        controller.setPieces(boardPane, board, tilesWithPiece, pieceManager, playerManager);
        controller.setObstacles(boardPane, board, tilesWithObstacle);

        controller.loadController(stage, root, actionPane, infoPane, board, pieceManager, playerManager, game);

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
                pieceMap.put(rs.getInt("PieceID"), piece);

                // Set piece on tile
                Tile tile = board.getTile(rs.getInt("TileX"), rs.getInt("TileY"));
                tile.setGameObject(piece);
                tilesWithPiece.add(tile);

                // Identify registeredPiece
                if (registeredPieceID == rs.getInt("PieceID")) {
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
                AbstractPiece piece = pieceMap.get(pieceID);

                int casterID = rs.getInt("CasterID");
                AbstractPiece caster = pieceMap.get(casterID);

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

    private boolean loadCoreGameData() {
        boolean hasSave = false;
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
                player1NumUndos = rs.getInt("Player1NumUndo");
                player2NumUndos = rs.getInt("Player2NumUndo");
                hasSave = true;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasSave;
    }
}
