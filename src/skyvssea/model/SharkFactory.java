package skyvssea.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class SharkFactory extends PieceFactory {
	
	@Override
	public Map<Hierarchy, ArrayList<Piece>> createInitialLineUp() {
		Map<Hierarchy, ArrayList<Piece>> lineup = new HashMap<Hierarchy, ArrayList<Piece>>();
		for (Hierarchy level : Hierarchy.values()) { 
			lineup.put(level, new ArrayList<Piece>());			
		}
		
		//TODO: can we make this DRY using a loop?
		lineup.get(Hierarchy.BIG).add(new BigShark());
		lineup.get(Hierarchy.MEDIUM).add(new MediumShark());
		lineup.get(Hierarchy.SMALL).add(new SmallShark());
		lineup.get(Hierarchy.BABY).add(new BabyShark());
		
		return lineup;
	}

	
	public static PieceFactory getInstance() {
		if (pieceFactory == null) {
			pieceFactory = new SharkFactory();
		}
		return pieceFactory;
	}

}
