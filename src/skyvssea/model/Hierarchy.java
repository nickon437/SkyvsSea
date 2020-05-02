package skyvssea.model;

public enum Hierarchy {
	BABY,
    SMALL,
    MEDIUM,
    BIG;

    private Hierarchy upgrade;
    private Hierarchy downgrade;

    static {
        BABY.upgrade = SMALL;
        SMALL.upgrade = MEDIUM;
        MEDIUM.upgrade = BIG;
        BIG.upgrade = BIG;

        BABY.downgrade = BABY;
        SMALL.downgrade = BABY;
        MEDIUM.downgrade = SMALL;
        BIG.downgrade = MEDIUM;
    }

    public Hierarchy upgrade() { return upgrade; }
    public Hierarchy downgrade() { return downgrade; }
}
