import processing.core.PImage;

import java.util.List;

public class Steve extends Entity {

    private int diamondCount;
    private int health;
    private int portalCount;

    public Steve(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int diamondCount, int health, int portalCount) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.position = position;
        this.diamondCount = diamondCount;
        this.health = health;
        this.portalCount = portalCount;

    }
    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }

    public static Steve createSteve(String id, Point position,
                                          List<PImage> images)
    {
        return new Steve(id, position, images, //adding parse to display entity image
                0, 0, 0, 20, 3);
    }

    @Override
    public void setPosition(Point p) {
        position.x = p.x;
        position.y = p.y;
    }

    @Override
    public Point getPosition() {
        return super.getPosition();
    }

    public int getDiamondCount() {
        return diamondCount;
    }

    public void setDiamondCount(int diamondCount) {
        this.diamondCount = diamondCount;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPortalCount() {
        return portalCount;
    }

    public void setPortalCount(int portalCount) {
        this.portalCount = portalCount;
    }
}
