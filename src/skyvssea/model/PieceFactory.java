package skyvssea.model;

import java.util.ArrayList;
import java.util.Map;

public abstract class PieceFactory {
	public abstract Map<Hierarchy, ArrayList<Piece>> createInitialLineUp();
}
