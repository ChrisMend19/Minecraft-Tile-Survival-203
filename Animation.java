public class Animation extends Action{

    private int repeatCount;

    public Animation(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore);
        this.repeatCount = repeatCount;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    @Override
    public void executeAction(Action action, EventScheduler scheduler) {
        executeAnimationAction(scheduler);
    }

    public static Animation createAnimationAction(Entity entity, int repeatCount)
    {
        return new Animation(entity, null, null, repeatCount);
    }

    public void executeAnimationAction(
            EventScheduler scheduler) //animation
    {
        this.getEntity().nextImage();

        if (this.getRepeatCount() != 1)
        {
            scheduler.scheduleEvent(this.getEntity(), createAnimationAction(this.getEntity(),
                    Math.max(repeatCount - 1, 0)),
                    this.getEntity().getAnimationPeriod());
        }
    }
}
