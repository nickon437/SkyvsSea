package skyvssea.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.java.contract.Ensures;

public class SharkFactory extends PieceFactory {

	private static SharkFactory sharkFactory;

	private SharkFactory() {}

//	@Override
//	public Map<Hierarchy, ArrayList<Piece>> createInitialLineUp() {
//		Map<Hierarchy, ArrayList<Piece>> lineup = new HashMap<Hierarchy, ArrayList<Piece>>();
//		for (Hierarchy level : Hierarchy.values()) { 
//			lineup.put(level, new ArrayList<Piece>());			
//		}
//		
//		//TODO: can we make this DRY using a loop?
//		lineup.get(Hierarchy.BIG).add(new BigShark());
//		lineup.get(Hierarchy.MEDIUM).add(new MediumShark());
//		lineup.get(Hierarchy.SMALL).add(new SmallShark());
//		lineup.get(Hierarchy.BABY).add(new BabyShark());
//		
//		return lineup;
//	}

	public static PieceFactory getInstance() {
		if (sharkFactory == null) {
			sharkFactory = new SharkFactory();
		}
		return sharkFactory;
	}

	@Override
	protected BigCharacter createBigCharacter() {
		return new BigShark();
	}

	@Override
	protected MediumCharacter createMediumCharacter() {
		return new MediumShark();
	}

	@Override
	protected SmallCharacter createSmallCharacter() {
		return new SmallShark();
	}

	@Override
	protected BabyCharacter createBabyCharacter() {
		return new BabyShark();
	}
}
