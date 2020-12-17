import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class AStarPathingStrategy implements PathingStrategy
{

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors){

        List<Point> path = new LinkedList<>();
        HashMap<Point, PointData> openList = new HashMap<>();
        HashMap<Point, PointData> closedList = new HashMap<>();
        PriorityQueue<PointData> filterOpenList = new PriorityQueue<PointData>(Comparator.comparing(n -> n.getF()));

        PointData startNode = new PointData( null,  start,0, calcH(end, start), 0 + calcH(end, start));
        openList.put(start, startNode);
        filterOpenList.add(startNode);

        while(!filterOpenList.isEmpty()) {
            PointData currentNode = filterOpenList.poll();
            if(currentNode == null) {
                return path;
            }

            if (!withinReach.test(currentNode.getCurrent(), end)) {
                List<Point> neighbors = potentialNeighbors.apply(currentNode.getCurrent())
                        .filter(p -> !closedList.containsKey(p))
                        .filter(canPassThrough)
                        .collect(Collectors.toList());

                for (Point neighbor : neighbors) {
                    PointData adjacent = new PointData(currentNode, neighbor,currentNode.getG() + 1, calcH(neighbor, end), (currentNode.getG() + 1) + calcH(neighbor, end));
                    if (!openList.containsKey(neighbor)) {
                        openList.put(neighbor, adjacent);
                        filterOpenList.add(adjacent);
                    }
                    else {
                        PointData temp = currentNode;
                        if (openList.get(neighbor).compareLowerG(temp)) {
                            openList.replace(neighbor, adjacent);
                            filterOpenList.add(adjacent);
                            filterOpenList.remove(openList.get(neighbor));

                        }
                    }
                    openList.remove(currentNode.getCurrent());
                    closedList.put(currentNode.getCurrent(), currentNode);
                }
            }
            else
                return findGoal(path, currentNode);
        }
        return path;


    }

    public List<Point> findGoal(List<Point> computedPath, PointData goal){
        computedPath.add(goal.getCurrent());
        if(goal.getPrevious() != null){
            return findGoal(computedPath, goal.getPrevious());
        }
        computedPath.remove(goal.getCurrent());
        Collections.reverse(computedPath);
        return computedPath;

    }


    int CalcG(Point start, Point current){
        return Math.abs((current.x - start.x) + (current.y - start.y));
    }

    int calcH(Point current, Point end){
        return Math.abs((end.x - current.x)) + Math.abs((end.y - current.y));
    }
}


/* attempt1
{
        //which data structure?  //maybe key/value

        //HashMap<Point, List<Point> > openList = new HashMap<Point, List<Point>>(); // (point, neighboring points)?
        //HashMap<Point, List<Point> > closedList = new HashMap<Point, List<Point>>();
        ArrayList<Point> openList = new ArrayList<Point>();
        ArrayList<Point> closedList = new ArrayList<Point>();
        ArrayList<Point> neighbors = new ArrayList<Point>();
        ArrayList<Double> fCompare = new ArrayList<Double>();
        List<Point> path = new LinkedList<>();
        //1.Choose/know starting and ending points of the path
        // 2.Add start node to the open list and mark it as the current node
        openList.add(start);
        Point currentPoint = start;
        // 3.Analyze all valid adjacent nodes that are not on the closed list
        Point rightN = new Point(currentPoint.x + 1, currentPoint.y); // get each point up right left down
        Point downN = new Point(currentPoint.x, currentPoint.y + 1);
        Point leftN = new Point(currentPoint.x - 1, currentPoint.y);
        Point upN = new Point(currentPoint.x, currentPoint.y - 1);

        neighbors.add(rightN);
        neighbors.add(downN);
        neighbors.add(upN);
        neighbors.add(leftN);
        System.out.println(potentialNeighbors);
        for(Point point: neighbors){
            //System.out.println(point);
            //check if theres obstacle or not in closed
            if(closedList.contains(point) == false && canPassThrough.test(point) == true &&  openList.contains(point) == false) {
                // a.Add to Open List if not already in it
                openList.add(point);
                //System.out.println(point);
                //distance formula
                //b.Determine distance from start node (g value)
                double gCurrent = heuristicDistance(currentPoint,start);
                double g = heuristicDistance(point,start);
                double hCurrent = heuristicDistance(currentPoint,end);
                double h = heuristicDistance(point,end);
                //i.g = Distance of current node from start + distance from current node to adjacent node
                //c.If the calculated g value is better than a previously calculated g value, replace the old g value with the new one and:
                //      i.If necessary, estimate distance of adjacent node to the end point (h)
                //      1.This is the called the heuristic.  It can be Euclidian distance, Manhattan distance, etc.
                //      ii.Add g and h to get an f value
                System.out.print("G: " + "center: "+ currentPoint + " gdistance: " +  gCurrent + " ");
                System.out.println("point: "+ point + " gdistance: " +  g + " ");
                System.out.print("H: " + "center: "+ currentPoint + " hdistance: " +  hCurrent + " ");
                System.out.println("point: "+ point + " hdistance: " +  h);
                double f = g + h;
                fCompare.add(f);
                System.out.println("F: " + f);
                //      iii.Mark the adjacent node’s prior vertex as the current node

            }

        }
        //      4.Move the current node to the closed list
        closedList.add(currentPoint);

        for(Double point: fCompare){

        }



            //      5.Choose a node from the open list with the smallest f value and make it the current node
            //      6.Go to step 3
            //      7.Repeat until a path to the end is found.

        return path;
    }

    public double heuristicDistance(Point p1, Point p2){
        double x1  = p1.x;
        double y1 =  p1.y;
        double x2  = p2.x;
        double y2 =  p2.y;
        double distance = (x2-x1) + (y2-y1);
        return distance;

    }
 */
// attempt2
//        List<Point> path = new LinkedList<>();
//
//        List<PointData> openList = new ArrayList<>();
//        List<PointData> closedList = new ArrayList<>();
//        PointData startNode = new PointData(0, calculateH(end, start), calculateH(end, start) + 0, null,  start);
//        openList.add(startNode);
//        PointData currentNode = startNode;
//
//        while(openList.size() > 0){
//            if (currentNode.getCurrent() == end){
//                return path;
//            }
//            else {
//                //step 3
//                List<Point> neighbors = potentialNeighbors.apply(currentNode.getCurrent())
//                        .filter(canPassThrough).collect(Collectors.toList());
//                //step3 a
//                for (Point neighbor : neighbors){
//                    //put neighbor point into a Node
//                    int g = calculateG(start, neighbor);
//                    int h = calculateH(end, neighbor);
//                    PointData temp = new PointData(g, h, g+h, currentNode, neighbor);
//                    if (!openList.contains(neighbor)){
//                        openList.add(temp);
//                    }
//                    if (temp.getG() < currentNode.getG()) //3c
//                        currentNode.setG(temp.getG());
//
//                }
//                System.out.println(openList);
//                closedList.add(currentNode);
//                openList.remove(currentNode);//idk
//                PointData minFPoint = null;
//                for(PointData pd: openList){
//                    if(minFPoint == null)
//                        minFPoint = pd;
//                    if(pd.getF() < minFPoint.getF())
//                        minFPoint = pd;
//                }
//                //PointData lowestF = openList.stream().min(PointData::getF).get();
//                currentNode = minFPoint;  //got the min f value
//
//                //break; //remove once line 49 works
//
//            }
//        }
//        return path;
//    }
//
//    public int calculateH(Point end, Point current){
//        return Math.abs((end.y - current.y) + (end.x - current.x));
//    }
//    public int calculateG(Point start, Point current){
//        return Math.abs((current.y - start.y) + (current.x - start.x));
//    }

//    List<Point> path = new LinkedList<>();
//    LinkedHashMap<Point, PointData> openList = new LinkedHashMap<>();
//    List<PointData> openListFiltered = new ArrayList<>();
//    List<PointData> closedList = new ArrayList<>();
//
//    PointData startPointValue = new PointData(null,  start, 0, findH(end, start), findH(end, start) + 0);
//    openList.put(start,startPointValue); // add  to both open lists
//    openListFiltered.add(startPointValue);
//    PointData currentNode = startPointValue;
//
//    while(openList.size() != 0 && openListFiltered.size() != 0){
//
//        if(!(withinReach.test(currentNode.getCurrent(), end))) {
//            List<Point> neighboringPoints = potentialNeighbors.apply(currentNode.getCurrent())
//            .filter(p -> !p.equals(start) && !p.equals(end)).filter(canPassThrough).collect(Collectors.toList()); //filter out non valid neighbor points and update list
//            for(Point neighbor : neighboringPoints){ // loop through updated list
//                //PointData updatedPoint = new PointData(currentNode,neighbor, currentNode.getG() + 1, findH(neighbor, end), findH(neighbor,end));
//                if(openList.containsKey(neighbor)) {
//                    PointData updatedPoint = new PointData(currentNode,neighbor, currentNode.getG() + 1, findH(neighbor, end), findH(neighbor,end));
//                    if(updatedPoint.getG() < openList.get(neighbor).getG()) { // see if new g is smaller than old g if so swap
//                        openList.get(neighbor).setG(updatedPoint.getG());
//                        openListFiltered.add(updatedPoint);
//                        openList.replace(neighbor,updatedPoint);
//                    }
//                }
//                else if(!(openList.containsKey(neighbor))) {
//                    PointData updatedPoint = new PointData(currentNode,neighbor, currentNode.getG() + 1, findH(neighbor, end), currentNode.getG() + 1 + findH(neighbor,end));
//                    openList.put(neighbor,updatedPoint);
//                    openListFiltered.add(updatedPoint);
//                }
//            }
//        openList.remove(currentNode.getCurrent()); //cross off current and  move to close
//        openListFiltered.remove(currentNode);
//        closedList.add(currentNode);
//        }
//        if((withinReach.test(currentNode.getCurrent(), end)))
////            openList.remove(currentNode.getCurrent()); //cross off current and  move to close
////            openListFiltered.remove(currentNode);
////            closedList.add(currentNode);
//            return getPath(path, currentNode);
//
//    //find smallest f value in list of points
//    Comparator<PointData>  compareF = Comparator.comparing(PointData::getF);
//    Collections.sort(openListFiltered, compareF);
//    openListFiltered.sort(compareF);
//    currentNode = openListFiltered.get(0);
//    //System.out.println(currentNode);
//    }
//
//    openList.remove(currentNode.getCurrent()); //cross off current and  move to close
//    openListFiltered.remove(currentNode);
//    closedList.add(currentNode);
//
//    //System.out.println(path);
//    return path;