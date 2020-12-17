public class Activity extends Action {

    public Activity(Entity entity, WorldModel world, ImageStore imageStore) {
        super(entity, world, imageStore);
    }

    public static Activity createActivityAction(Entity entity, WorldModel world,
                                       ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore);
    }

    @Override
    public void executeAction(Action action, EventScheduler scheduler) {
        executeActivityAction(scheduler);
    }

//    public void executeActivityAction(EventScheduler scheduler) //goes to act -abstract
//    {
//        switch (getEntity().getKind())
//        {
//            case Skeleton_FULL:
//                if(this.getEntity() instanceof Skeleton && ((Skeleton)this.getEntity()).getisFull())
//                    Skeleton.executeSkeletonFullActivity((Skeleton) getEntity(), getWorld(),
//                            getImageStore(), scheduler);
//                break;
//
//            case Skeleton_NOT_FULL:
//                if(this.getEntity() instanceof Skeleton && !((Skeleton)this.getEntity()).getisFull())
//                    Skeleton.executeSkeletonNotFullActivity((Skeleton) getEntity(), getWorld(),
//                            getImageStore(), scheduler);
//                break;
//
//            case Zombie:
//                Zombie.executeZombieActivity(getEntity(), getWorld(), getImageStore(),
//                        scheduler);
//                break;
//
//            case CRAB:
//                Crab.executeCrabActivity(getEntity(), getWorld(),
//                        getImageStore(), scheduler);
//                break;
//
//            case QUAKE:
//                Quake.executeQuakeActivity(getEntity(), getWorld(), getImageStore(),
//                        scheduler);
//                break;
//
//            case Village:
//                Village.executeVillageActivity(getEntity(), getWorld(), getImageStore(),
//                        scheduler);
//                break;
//
//            case Nether:
//                Nether.executeNetherActivity(getEntity(), getWorld(), getImageStore(),
//                        scheduler);
//                break;
//
//            default:
//                throw new UnsupportedOperationException(
//                        String.format("executeActivityAction not supported for %s",
//                                getEntity().getKind()));
//        }
//    }

    public void executeActivityAction(EventScheduler scheduler)
    {
        if(this.getEntity() != null) {
            if (this.getEntity() instanceof Skeleton && ((Skeleton) this.getEntity()).getisFull())
                Skeleton.executeSkeletonFullActivity((Skeleton) getEntity(), getWorld(), getImageStore(), scheduler);

            if (this.getEntity() instanceof Skeleton && !((Skeleton) this.getEntity()).getisFull())
                Skeleton.executeSkeletonNotFullActivity((Skeleton) getEntity(), getWorld(), getImageStore(), scheduler);

            if (this.getEntity() instanceof Zombie)
                Zombie.executeZombieActivity(getEntity(), getWorld(), getImageStore(), scheduler);

//            if (this.getEntity() instanceof Crab)
//                Crab.executeCrabActivity(getEntity(), getWorld(), getImageStore(), scheduler);

            if (this.getEntity() instanceof Quake)
                Quake.executeQuakeActivity(getEntity(), getWorld(), getImageStore(), scheduler);

            if (this.getEntity() instanceof Village)
                Village.executeVillageActivity(getEntity(), getWorld(), getImageStore(), scheduler);

            if (this.getEntity() instanceof Nether)
                Nether.executeNetherActivity(getEntity(), getWorld(), getImageStore(), scheduler);

        }
        else {
            throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", getEntity()));
        }
    }
}
