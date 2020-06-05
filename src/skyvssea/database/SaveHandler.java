package skyvssea.database;

import skyvssea.model.*;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveHandler {

    private Map<AbstractPiece, Integer> pieceMap = new HashMap<>();

    public void saveGame(Board board, PieceManager pieceManager, PlayerManager playerManager, Game game) {
        DatabaseSetup.rebuildDatabase();

        int registeredPieceID = -1;
        AbstractPiece registeredPiece = pieceManager.getRegisteredPiece();

        for (Tile[] cols : board.getTiles()) {
            for (Tile tile : cols) {
                if (tile.hasPiece()) {
                    AbstractPiece piece = (AbstractPiece) tile.getGameObject();
                    int pieceID = savePiece(piece, tile.getX(), tile.getY());

                    if (registeredPiece != null && registeredPiece.equals(piece)) {
                        registeredPieceID = pieceID;
                    }
                } else if (tile.hasObstacle()) {
                    saveObstacle((Obstacle) tile.getGameObject(), tile.getX(), tile.getY());
                }
            }
        }

        for (Map.Entry<AbstractPiece, Integer> entry : pieceMap.entrySet()) {
            saveSpecialEffectManager(entry.getValue(), entry.getKey());
        }

        saveCoreData(game.getCurrentGameState(), playerManager.getPlayerIndex(playerManager.getCurrentPlayer()), registeredPieceID, board, playerManager);
    }

    private int savePiece(AbstractPiece piece, int tileX, int tileY) {
        int pieceID = -1;

        try {
            String query = "INSERT INTO " + SVSDatabase.PIECE_TABLE
                    + " (PieceName, TileX, TileY, IsPassiveEffectActivated, SpecialEffectCounter) "
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = SVSDatabase.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, piece.getName());
            stmt.setInt(2, tileX);
            stmt.setInt(3, tileY);
            stmt.setBoolean(4, piece.isPassiveEffectActivated());
            stmt.setInt(5, piece.getActiveEffectCounter());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            pieceID = rs.getInt(1);
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pieceMap.put(piece, pieceID);
        return pieceID;
    }

    private void saveObstacle(Obstacle obstacle, int tileX, int tileY) {
        try {
            String query = "INSERT INTO " + SVSDatabase.OBSTACLE_TABLE + " (TileX, TileY) "
                    + "VALUES (?, ?)";
            PreparedStatement stmt = SVSDatabase.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, tileX);
            stmt.setInt(2, tileY);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveSpecialEffectManager(int pieceID, AbstractPiece piece) {
        List<SpecialEffectObject> appliedSpecialEffects = piece.getSpecialEffectManager().getAppliedSpecialEffects();
        for (SpecialEffectObject specialEffect : appliedSpecialEffects) {
            saveAppliedSpecialEffect(pieceID, specialEffect);
        }
    }

    private void saveAppliedSpecialEffect(int pieceID, SpecialEffectObject specialEffect) {
        try {
            String query = "INSERT INTO " + SVSDatabase.SPECIAL_EFFECT_MANAGER_TABLE + " (PieceID, CasterID, SpecialEffect, EffectiveDuration) "
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = SVSDatabase.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, pieceID);
            stmt.setInt(2, pieceMap.get(specialEffect.getCaster()));
            stmt.setString(3, specialEffect.getName());
            stmt.setInt(4, specialEffect.getEffectiveDuration());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveCoreData(GameState currentGameState, int currentPlayerIndex, int registeredPieceID, Board board, PlayerManager playerManager) {
        Tile registeredTile = board.getRegisteredTile();
        int registeredTileX = registeredTile == null ? -1 : registeredTile.getX();
        int registeredTileY = registeredTile == null ? -1 : registeredTile.getY();

        try {
            String query = "INSERT INTO " + SVSDatabase.CORE_GAME_TABLE
                    + " (CurrentState, CurrentPlayer, RegisteredPiece, RegisteredTileX, RegisteredTileY, Col, Row, Player1NumUndo, Player2NumUndo) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = SVSDatabase.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, currentGameState.toString());
            stmt.setInt(2, currentPlayerIndex);
            stmt.setInt(3, registeredPieceID);
            stmt.setInt(4, registeredTileX);
            stmt.setInt(5, registeredTileY);
            stmt.setInt(6, board.getCol());
            stmt.setInt(7, board.getRow());
            stmt.setInt(8, playerManager.getPlayer(0).getNumUndos());
            stmt.setInt(9, playerManager.getPlayer(1).getNumUndos());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
