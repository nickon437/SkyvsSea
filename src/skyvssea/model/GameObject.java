package skyvssea.model;

public abstract class GameObject implements AvatarCore {
    private Avatar avatar;

    @Override
    public void addAvatar(Avatar avatar) { this.avatar = avatar; }

    @Override
    public Avatar getAvatar() { return avatar; }
}
