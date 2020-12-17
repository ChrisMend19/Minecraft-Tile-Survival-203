import processing.core.PImage;

import java.util.List;

public class Obstacle extends Entity {

    public Obstacle(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);

    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }

    public static Obstacle createObstacle(String id, Point position,
                                 List<PImage> images)
    {
        return new Obstacle(id, position, images,
                0, 0);
    }
}
