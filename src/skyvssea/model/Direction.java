package skyvssea.model;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST,
    JUMP_OVER;

	public static List<Direction> getEightDirections() {
		List<Direction> eightDirections = new ArrayList<>();
		for (Direction direction : Direction.values()) {
			if (direction == Direction.JUMP_OVER) {
				continue;
			}
			eightDirections.add(direction);
		}
		
		return eightDirections;
	}
}
