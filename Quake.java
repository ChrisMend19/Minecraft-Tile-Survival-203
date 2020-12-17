import processing.core.PImage;

import java.util.List;

public class Quake extends Entity{

    static final String QUAKE_KEY = "quake";
    static final String QUAKE_ID = "quake"; //changed to static
    static final int QUAKE_ACTION_PERIOD = 1100;
    static final int QUAKE_ANIMATION_PERIOD = 100;

    public Quake(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                getActionPeriod());
        scheduler.scheduleEvent(this,
                Animation.createAnimationAction(this,QUAKE_ANIMATION_REPEAT_COUNT),
                getAnimationPeriod());
    }

    public static Quake createQuake(Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ID, position, images,
                QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public static void executeQuakeActivity(Entity entity, WorldModel world,
                                            ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(entity);
        world.removeEntity(entity);
    }
}
