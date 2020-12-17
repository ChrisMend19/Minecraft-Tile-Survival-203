import processing.core.PImage;

import java.util.List;

public class Zombie extends Entity {

    static final String Zombie_ID_PREFIX = "Zombie -- ";
    static final int Zombie_CORRUPT_MIN = 20000;
    static final int Zombie_CORRUPT_MAX = 30000;
    static final String Zombie_KEY = "zombie";

    public Zombie(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                getActionPeriod());
    }

    public static Zombie createZombie(String id, Point position, int actionPeriod, //I changed parse Village because there a error
                                    List<PImage> images)
    {
        return new Zombie(id, position, images,
                actionPeriod, 0);
    }

    public static void executeZombieActivity(Entity entity, WorldModel world,
                                           ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = entity.getPosition();  // store current position before removing

        world.removeEntity(entity);
        scheduler.unscheduleAllEvents(entity);

//        Crab crab = Crab.createCrab(entity.getId() + Crab.CRAB_ID_SUFFIX,
//                pos, entity.getActionPeriod() / Crab.CRAB_PERIOD_SCALE,
//                Crab.CRAB_ANIMATION_MIN +
//                        Action.rand.nextInt(Crab.CRAB_ANIMATION_MAX - Crab.CRAB_ANIMATION_MIN),
//                imageStore.getImageList(Crab.CRAB_KEY));
//
//        world.addEntity(crab);
//        crab.scheduleActions(scheduler, world, imageStore);
    }
}
