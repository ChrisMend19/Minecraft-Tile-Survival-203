import processing.core.PImage;

import java.util.List;

public class Nether extends Entity {


    public Nether(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Animation.createAnimationAction(this, Nether_ANIMATION_REPEAT_COUNT),
                getAnimationPeriod());
    }

    public static Nether createNether(String id, Point position,
                                        List<PImage> images)
    {
        return new Nether(id, position, images,
                0, 0);
    }

    public static void executeNetherActivity(Entity entity, WorldModel world,
                                               ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(entity);
        world.removeEntity(entity);
    }

}
