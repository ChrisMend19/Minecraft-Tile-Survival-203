import processing.core.PImage;

import java.util.List;

public class Diamond extends Entity {

    private int diamondCount;

    public Diamond(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int diamondCount) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.position = position;
        this.diamondCount = diamondCount;

    }
    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }

    public static Diamond createDiamond(String id, Point position,
                                    List<PImage> images)
    {
        return new Diamond(id, position, images, //adding parse to display entity image
                0, 0,10);
    }

    public int getDiamondCount() {
        return diamondCount;
    }

    public void setDiamondCount(int diamondCount) {
        this.diamondCount = diamondCount;
    }

    //    @Override
//    public Point getPosition() {
//        return super.getPosition();
//    }
}
