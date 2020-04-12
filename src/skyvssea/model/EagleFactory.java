package skyvssea.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class EagleFactory extends PieceFactory {

	@Override
	public Map<Hierarchy, ArrayList<Piece>> createInitialLineUp() {
		Map<Hierarchy, ArrayList<Piece>> lineup = new HashMap<Hierarchy, ArrayList<Piece>>();
		for (Hierarchy level : Hierarchy.values()) { 
			lineup.put(level, new ArrayList<Piece>());			
		}
		
		//TODO: can we make this DRY using a loop?
		lineup.get(Hierarchy.BIG).add(new BigEagle());
		lineup.get(Hierarchy.MEDIUM).add(new MediumEagle());
		lineup.get(Hierarchy.SMALL).add(new SmallEagle());
		lineup.get(Hierarchy.BABY).add(new BabyEagle());
		
		return lineup;
	}
	
	public static PieceFactory getInstance() {
		if (pieceFactory == null) {
			pieceFactory = new EagleFactory();
		}
		return pieceFactory;
	}

}
