package skyvssea.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class PieceFactory {
	protected static PieceFactory pieceFactory;

	public abstract Map<Hierarchy, ArrayList<Piece>> createInitialLineUp();
}
