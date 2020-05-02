package skyvssea.model;

public enum Hierarchy {
	BABY(0),
    SMALL(1),
    MEDIUM(2),
    BIG(3),
    STEROID(4);
	
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
}
