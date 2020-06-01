package skyvssea.model;

import java.util.ArrayList;
import java.util.List;

public class ObstacleManager {

    private static final double OBSTACLE_APPEARANCE_PROBABLITY = 0.1;

    public List<Tile> setObstacleOnBoard(Board board) {
        List<Tile> obstacleTiles = new ArrayList<>();
        int numCol = board.getCol();
        int numRow = board.getRow();

        for (int x = 0; x < numCol; x++) {
            for (int y = 0; y < numRow; y++) {
                Tile tile = board.getTile(x, y);
                if (!tile.hasPiece() && Math.random() < OBSTACLE_APPEARANCE_PROBABLITY) {
                    tile.setGameObject(new Obstacle());
                    obstacleTiles.add(tile);
                }
            }
        }
        return obstacleTiles;
    }

}
