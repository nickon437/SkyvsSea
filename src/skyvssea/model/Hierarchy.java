package skyvssea.model;

import com.google.java.contract.Ensures;

public enum Hierarchy {
	BABY(0),
    SMALL(1),
    MEDIUM(2),
    BIG(3);
	
	public final int magnitude;
	
	private Hierarchy(int magnitude) {
		this.magnitude = magnitude;
	}
	
	public static Hierarchy valueOfMagnitude(int magnitude) {
	    for (Hierarchy lvl : values()) {
	        if (lvl.magnitude == magnitude) {
	            return lvl;
	        }
	    }
	    return null;
	}
	
	@Ensures("values().length - 1 == result.magnitude")
	public static Hierarchy maxLevel() {
		return Hierarchy.BIG;
	}

	@Ensures("result.magnitude == 0")
	public static Hierarchy minLevel() {
		return Hierarchy.BABY;
	}
}
