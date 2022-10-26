package org.example;

import java.util.HashMap;
import java.util.Map;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    private HashMap<Location, Waypoint> openWaypoints = new HashMap<>();
    private HashMap<Location, Waypoint> closedWaypoints = new HashMap<>();


    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (openWaypoints.isEmpty()) return null; // для пустой возвращаем null
        Waypoint wp = openWaypoints.values().stream().findFirst().get(); // получаем первое значение
        float minTotalValue = wp.getTotalCost();
        for (Map.Entry<Location, Waypoint> currentWp: openWaypoints.entrySet()){
            // проходимся по всем точкам и ищем минимальную
            if(Math.min(minTotalValue, currentWp.getValue().getTotalCost()) != minTotalValue) {
                minTotalValue = currentWp.getValue().getTotalCost();
                wp = currentWp.getValue();
            }
        }
        return wp;
    }

//    public Waypoint getMinOpenWaypoint() {
//        if (openWaypoints.isEmpty()) return null;
//        Waypoint minTotalCostWaypoint = openWaypoints.values().stream().findFirst().get();
//        float minTotalCost = minTotalCostWaypoint.getTotalCost();
//        for (Map.Entry<Location, Waypoint> i : openWaypoints.entrySet()) {
//            if(i.getValue().getTotalCost() < minTotalCost) {
//                minTotalCost = i.getValue().getTotalCost();
//                minTotalCostWaypoint = i.getValue();
//            }
//        }
//        return minTotalCostWaypoint;
//    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        if (!openWaypoints.containsValue(newWP) ||
                openWaypoints.get(newWP.getLocation()).getPreviousCost() > newWP.getPreviousCost()) {
            openWaypoints.put(newWP.getLocation(), newWP);
            return true;
        }
        return false;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return openWaypoints.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        closedWaypoints.put(loc, openWaypoints.get(loc));
        openWaypoints.remove(loc);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closedWaypoints.containsKey(loc);
    }
}

