import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import processing.core.*;

/*
VirtualWorld is our main wrapper
It keeps track of data necessary to use Processing for drawing but also keeps track of the necessary
components to make our world run (eventScheduler), the data in our world (WorldModel) and our
current view (think virtual camera) into that world (WorldView)
 */

public final class VirtualWorld
        extends PApplet
{
   private static final int TIMER_ACTION_PERIOD = 100;

   private static final int VIEW_WIDTH = 1280;
   private static final int VIEW_HEIGHT = 960;
   private static final int TILE_WIDTH = 32;
   private static final int TILE_HEIGHT = 32;
   private static final int WORLD_WIDTH_SCALE = 1;
   private static final int WORLD_HEIGHT_SCALE = 1;

   private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

   private static String IMAGE_LIST_FILE_NAME = "imagelist";
   private static final String DEFAULT_IMAGE_NAME = "background_default";
   private static final int DEFAULT_IMAGE_COLOR = 0x808080;

   private static String LOAD_FILE_NAME = "world.sav";

   private static final String FAST_FLAG = "-fast";
   private static final String FASTER_FLAG = "-faster";
   private static final String FASTEST_FLAG = "-fastest";
   private static double FAST_SCALE = 0.4;
   private static final double FASTER_SCALE = 0.25;
   private static final double FASTEST_SCALE = 0.10;

   private static double timeScale = 1.0;

   PFont font;

   boolean bool;
   boolean endBool = false;
   boolean startBool = false;
   boolean modeBool = false;
   boolean extraBool = false;
   public static boolean netherBool;

   static final int PROPERTY_KEY = 0;
   static final String BGND_KEY = "background";

//   static final String Skeleton_KEY = "Skeleton";
//   static final String OBSTACLE_KEY = "obstacle";
//   static final String Zombie_KEY = "Zombie";
//   static final String Nether_KEY = "Nether";
//   static final String Village_KEY = "seaGrass";

   private ImageStore imageStore;
   private WorldModel world;
   private WorldView view;
   private EventScheduler scheduler;


   private long next_time;



   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */
   public void setup()
   {
      //loop();
      bool = false;
      this.imageStore = new ImageStore(
              createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
              createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
              TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
      font = createFont("Minecraftia.ttf", 100);
      textFont(font);
   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
      }

      view.drawViewport();
      textSize(30);
      fill(255, 255, 255, 175);

      text("Diamonds: " + world.getSteve().getDiamondCount() , 10, 100);
      text("Health: " + world.getSteve().getHealth(), 10, 50 );
      if(netherBool) {
         text("Teleports: " + world.getSteve().getPortalCount(), 10, 150);
      }
      if(world.getSteve().getHealth() == 0){

         endBool = true;
         background(loadImage("images/lavaBG.jpg"));
         textSize(30);
         textAlign(CENTER);
         text("You died!", VIEW_WIDTH/2, VIEW_HEIGHT/3+20);
         String diamonds = String.valueOf(world.getSteve().getDiamondCount());
         textSize(20);
         text("Score: ", VIEW_WIDTH/2,VIEW_HEIGHT/3 + 60);
         fill(242,222,119,180);
         text(diamonds, VIEW_WIDTH/2 + 55, VIEW_HEIGHT/3 + 60);
         fill(13,0,0,5);
         rect(VIEW_WIDTH/3,VIEW_HEIGHT/2 - 40, 426,30);
         rect(VIEW_WIDTH/3,VIEW_HEIGHT/2, 426,30);
         rect(VIEW_WIDTH/3,VIEW_HEIGHT/2 + 40, 426,30);
         fill(111,109,111);
         rect(VIEW_WIDTH/3 + 1,VIEW_HEIGHT/2 - 39, 424.5f,28);
         rect(VIEW_WIDTH/3 + 1,VIEW_HEIGHT/2 + 1, 424.5f,28);
         rect(VIEW_WIDTH/3 + 1,VIEW_HEIGHT/2 + 41, 424.5f,28);
         fill(255);
         textSize(17);
         text("easy", VIEW_WIDTH/2, VIEW_HEIGHT/2 - 20);
         text("medium", VIEW_WIDTH/2, VIEW_HEIGHT/2 + 20);
         text("hard", VIEW_WIDTH/2, VIEW_HEIGHT/2 + 60);
         textAlign(BASELINE);

      }

      if (world.getSteve().getDiamondCount() == 10) {
         startBool = true;
         //endBool =  false;
         background(loadImage("images/dirtBackground.png"));
         textSize(80);
         textAlign(CENTER);
         text("You Win!", VIEW_WIDTH / 2, 2 * VIEW_HEIGHT / 5);

         rectMode(CENTER);
         fill(111, 109, 111);
         stroke(0);
         rect(VIEW_WIDTH / 2, VIEW_HEIGHT / 2, 350, 45);
         rect(VIEW_WIDTH / 2, VIEW_HEIGHT / 2 + 70, 350, 45);
         textSize(25);
         fill(255, 255, 255);
         stroke(0);

         text("Play Again", VIEW_WIDTH / 2, (VIEW_HEIGHT / 2) + 10);
         text("Change Mode", VIEW_WIDTH / 2, (VIEW_HEIGHT / 2) + 80);
         textAlign(BASELINE);
         rectMode(BASELINE);

      }

      if(modeBool){
         extraBool = true;
         //stopModeBool = true;
         //System.out.println(endBool);
         background(loadImage("images/modeBG.jpg"));
         textAlign(CENTER);
         //text("You Win!", VIEW_WIDTH / 2, 2 * VIEW_HEIGHT / 5);

         rectMode(CENTER);
         fill(111, 109, 111);
         stroke(0);
         rect(VIEW_WIDTH / 2, VIEW_HEIGHT / 2, 350, 45);
         rect(VIEW_WIDTH / 2, VIEW_HEIGHT / 2 + 70, 350, 45);
         rect(VIEW_WIDTH / 2, VIEW_HEIGHT / 2 + 140, 350, 45);
         textSize(25);
         fill(255, 255, 255);
         stroke(0);

         text("Easy", VIEW_WIDTH / 2, (VIEW_HEIGHT / 2) + 10);
         text("Medium", VIEW_WIDTH / 2, (VIEW_HEIGHT / 2) + 80);
         text("Hard", VIEW_WIDTH / 2, (VIEW_HEIGHT / 2) + 150);
         extraBool = false;
         textAlign(BASELINE);
         rectMode(BASELINE);
      }


//      if(world.getSteve().getHealth() == 0 && !bool){
//         bool = true;
//         if(LOAD_FILE_NAME == "underworld.sav")
//            IMAGE_LIST_FILE_NAME = "imagelist";
//            LOAD_FILE_NAME = "world.sav";
//         for(Entity entity: world.getEntity()){
//            world.removeEntity(entity);
//         }
         //setup();
         //LOAD_FILE_NAME = "end.sav";
         //loadWorld(world, LOAD_FILE_NAME, imageStore);
      //}
//      if(world.getSteve().getDiamondCount() == 10 && !bool){
//         if(LOAD_FILE_NAME == "underworld.sav")
//            IMAGE_LIST_FILE_NAME = "imagelist";
//            LOAD_FILE_NAME = "world.sav";
//         bool = true;
//         setup();
//      }

   }

   public void keyPressed()
   {
      if (key == CODED)
      {
         int dx = 0;
         int dy = 0;

         switch (keyCode)
         {
            case UP:
               dy = -1;
               break;
            case DOWN:
               dy = 1;
               break;
            case LEFT:
               dx = -1;
               break;
            case RIGHT:
               dx = 1;
               break;
         }

         //Steve steve = world.getSteve();
         //steve.setPosition(new Point(dx,dy));
         view.shiftView(dx, dy); //change this //world model put entity //parse=reads thro world.sav
      }
   }

   public ImageStore getImageStore() {
      return imageStore;
   }

   public void mousePressed()
   {
      System.out.println(mouseX  + " , " + mouseY);
      int realX = mouseX/32;
      int realY = mouseY/32;
      netherBool = false;


      if(!netherBool && !modeBool && LOAD_FILE_NAME  == "world.sav" && ((mouseX < 32 && mouseX > 0 && mouseY < 384 && mouseY > 352) || (mouseY < 32 && mouseY > 0 && mouseX > 640 && mouseX < 672)
              || (realX <= 40 && realX >= 39 && realY < 1 && realY >= 0) || (realX < 20 && realX >= 19 && realY < 15 && realY >= 14)
      || (realX <= 40 && realX >= 39 && realY < 15 && realY >= 14) || (realX < 1 && realX >= 0 && realY < 30 && realY >= 29)
      || (realX < 20 && realX >= 19 && realY < 30 && realY >= 29) || (realX <= 40 && realX >= 39 && realY < 30 && realY >= 29)))  {
         //startBool = true;
         LOAD_FILE_NAME = "underworld.sav";
         IMAGE_LIST_FILE_NAME = "imagelist2";
         netherBool = true;

         loadWorld(world, LOAD_FILE_NAME, imageStore);
         loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
         // cant directly change world.sav to nether.sav
         //maybe make a bool change and that triggers a  entity to spawn in crab
      }
      if(endBool = true && !startBool && !netherBool && (mouseX >= 426 && mouseX <= 851 && mouseY <= 471 && mouseY >= 442)) {
         if(LOAD_FILE_NAME == "underworld.sav"){
            LOAD_FILE_NAME = "world.sav";
            IMAGE_LIST_FILE_NAME = "imagelist";
         }
         System.out.println("e");
         FAST_SCALE = 0.4;
         timeScale = FAST_SCALE;
         setup();
      }
      if(endBool = true && !startBool && !netherBool && (mouseX >= 426 && mouseX <= 851 && mouseY <= 511 && mouseY >= 483)) {
         if(LOAD_FILE_NAME == "underworld.sav"){
            LOAD_FILE_NAME = "world.sav";
            IMAGE_LIST_FILE_NAME = "imagelist";
         }
         System.out.println("m");
         FAST_SCALE = 0.25;
         timeScale = FAST_SCALE;
         setup();
      }
      if(endBool = true && !startBool && !netherBool && (mouseX >= 426 && mouseX <= 851 && mouseY <= 552 && mouseY >= 523)) {
         if(LOAD_FILE_NAME == "underworld.sav"){
            LOAD_FILE_NAME = "world.sav";
            IMAGE_LIST_FILE_NAME = "imagelist";
         }
         System.out.println("h");
         FAST_SCALE = 0.1;
         timeScale = FAST_SCALE;
         setup();
      }

      if (startBool == true && !netherBool && !modeBool && (mousePressed && mouseX > (VIEW_WIDTH / 2) - 175 && mouseX < (VIEW_WIDTH / 2) + 175
              && mouseY < (VIEW_HEIGHT / 2) + 22 && mouseY > (VIEW_HEIGHT / 2) - 22)) {
         //System.out.println("adsf2");
         if(LOAD_FILE_NAME == "underworld.sav"){
            LOAD_FILE_NAME = "world.sav";
            IMAGE_LIST_FILE_NAME = "imagelist";
         }
         setup();
      }

      if (!modeBool && startBool && (mouseX >= 467 && mouseX <= 815 && mouseY <= 574 && mouseY >= 531)) {
         //startBool = false;
         //System.out.println("asdf");
         if(LOAD_FILE_NAME == "underworld.sav"){
            LOAD_FILE_NAME = "world.sav";
            IMAGE_LIST_FILE_NAME = "imagelist";
         }
         modeBool = true;
         startBool = false;
         endBool = false;
         extraBool = true;

         //setup();
      }
      if(!extraBool && modeBool && (mouseX >= 467 && mouseX <= 814 && mouseY <= 504 && mouseY >= 461)){
         //System.out.println("bruh");
         //textAlign(BASELINE); //fix allignement
         FAST_SCALE = 0.5;
         timeScale = FAST_SCALE;
         modeBool = false;
         extraBool = false;

         startBool = true;
         setup();
      }
      else if(!extraBool && modeBool && (mouseX >= 467 && mouseX <= 814 && mouseY <= 574 && mouseY >= 530)){
         //System.out.println("bruhhhhhh");
         //textAlign(BASELINE); //fix allignement
         FAST_SCALE = 0.3;
         timeScale = FAST_SCALE;
         modeBool = false;
         extraBool = false;

         startBool = true;
         setup();
      }
      else if(!extraBool && modeBool && (mouseX >= 467 && mouseX <= 814 && mouseY <= 643 && mouseY >= 601)){
         //System.out.println("bruhhhhhh");
         //textAlign(BASELINE); //fix allignement
         FAST_SCALE = 0.15;
         timeScale = FAST_SCALE;
         modeBool = false;
         extraBool = false;

         startBool = true;

         setup();
      }
//      if(modeBool && extraBool && (mouseX >= 467 && mouseX <= 814 && mouseY <= 574 && mouseY >= 530)){
//         //System.out.println("bruh");
//         FAST_SCALE = 0.25;
//         timeScale = FAST_SCALE;
//         modeBool = false;
//         startBool = true;
//         extraBool = false;
//         //textAlign(BASELINE);
//         setup();
//      }
//      if(modeBool && extraBool && (mouseX >= 467 && mouseX <= 814 && mouseY <= 644 && mouseY >= 600)){
//         //System.out.println("bruh");
//         FAST_SCALE = 0.1;
//         timeScale = FAST_SCALE;
//         modeBool = false;
//         startBool = true;
//         extraBool = false;
//         //textAlign(BASELINE);
//         setup();
//      }
//      if(!startBool && modeBool && (mouseX >= 467 && mouseX <= 814 && mouseY <= 575 && mouseY >= 530)){
//         System.out.println("bruh2");
//         FAST_SCALE = 0.25;
//         timeScale = FAST_SCALE;
//         startBool = true;
//         modeBool = false;
//         setup();
//      }

      //if(modeBool && (mouseX >= 467 && mouseX <= 814 && mouseY <= 504 && mouseY >= 461)){
        // System.out.println("bruh2");
//         FAST_SCALE = 0.4;
//         timeScale = FAST_SCALE;
//         startBool = true;
//         modeBool = false;
//         setup();
      //}
         //setup();


   }

   public static Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
              imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   public static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
                                  PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         imageStore.loadImages(in, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public static void loadWorld(WorldModel world, String filename,
                                ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         load(in,world, imageStore);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public static void scheduleActions(WorldModel world,
                                      EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.getEntities())
      {
         //Only start actions for entities that include action (not those with just animations)
         if (entity.getActionPeriod() > 0)
            entity.scheduleActions(scheduler, world, imageStore);
      }
   }

   public static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }

   public static void load(Scanner in, WorldModel world, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), world, imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }

   public static boolean processLine(String line, WorldModel world,
                                     ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return imageStore.parseBackground(properties, world);
            case ImageStore.Skeleton_KEY:
               return imageStore.parseSkeleton(properties, world);
            case ImageStore.OBSTACLE_KEY:
               return imageStore.parseObstacle(properties, world);
            case ImageStore.Zombie_KEY:
               return imageStore.parseZombie(properties, world);
            case ImageStore.NETHER_KEY:
               return imageStore.parseNether(properties, world);
            case ImageStore.Village_KEY:
               return imageStore.parseVillage(properties, world);
            case ImageStore.STEVE_KEY:
               return imageStore.parseSteve(properties,world);
            case ImageStore.DIAMOND_KEY:
               return imageStore.parseDiamond(properties,world);
            case ImageStore.GHAST_KEY:
               return imageStore.parseGhast(properties,world);
            case ImageStore.CREEPER_KEY:
               return imageStore.parseCreeper(properties, world);

         }
      }

      return false;
   }




   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }
}
