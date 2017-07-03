package SilverRail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.LinkedHashMap;

/**
 * @author Simone Maniero
 * 
 */
public class SilverRail {

	/**
	 * Retrieve the distance between 2 Stations
	 * 
	 * @param s1 Station number 1
	 * @param s2 Station number 2
	 * @return the distance between the Station s1 and Station s2
	 */
	public static float getDistanceBetweenStations(Station s1, Station s2){
		// Apply the distance formula sqrt((x2 - x1)^2 + (y2 - y1)^2) to
		// retrieve the distance between the Station s1 and Station s2
		float distance = (float)Math.sqrt( Math.pow(s2.getPosX() - s1.getPosX(), 2) + Math.pow(s2.getPosY() - s1.getPosY(), 2) );
		return distance;
	}
	
	/**
	 * The following algorithm retrieves the shortest path between 2 different stations
	 * using a similar approach of the A Start algorithm.
	 * @param startStation The Station as a start location
	 * @param endStation The Station as the target location
	 * @return The shortest path between the Station start and Station end. If there is no connections between
	 * the start station and the end station, the algorithm returns an empty path.
	 */
	public Stack<Station> getShortestPathBetweenStations(Station startStation, Station endStation){
		// This Stack will contain a list of stations of the shortest path from Start to End
		Stack<Station> shortestPath = new Stack<>();
		
		// This map is to keep track of the path before to reach to the shortest path,
		// with the key being the child station and the value being the father station
		Map<Station, Station> pathMap = new LinkedHashMap<Station, Station>(); 
		
		// This map contains all the stations to be examined and the sum between the
		// starting station to the current station, and the hypothetical distance between
		// the current station to the target station.
		Map<Station, Float> openStations = new LinkedHashMap<Station, Float>();
		
		// This List contains all the station already evaluated
		List<Station> closedStations = new ArrayList<>();
		
		// Add the start station into the open station list and begin with the iteration
		float distanceStartEnd = getDistanceBetweenStations(startStation, endStation);
		// We do not need to calculate the formula here as the distance from start is 0
		openStations.put(startStation, distanceStartEnd);
		
		// iterate until the end has been reached or until the openStations is empty
		while(!openStations.isEmpty()){
			
			// remove the station with the lowest AStar formula from the openStations,
			// retrieve and add the connections from it to the openStations list and add 
			// the current station to the closedStations to mark it as evaluated.
			Entry<Station, Float> minEntryStation = Collections.min(openStations.entrySet(),
					(s1, s2) -> s1.getValue().compareTo(s2.getValue()));
			Station currentStation = minEntryStation.getKey();
			
			// check if the station is the end station
			if(currentStation.equals(endStation)){
				// we reached the end as the end station has the lowest formula
				// retrieve the shortest path using the pathMap
				shortestPath.add(endStation);
				while(shortestPath.peek() != startStation){
					Station father = pathMap.get(shortestPath.peek());
					shortestPath.add(father);
				}
				return shortestPath;
			}
			
			// retrieve the AStar formula for the current station
			float currentStationFormula = openStations.get(currentStation);
			
			// remove from the current station formula the distance from the current
		    // station to the end, to get the distance from start to the current station
		    float distanceFromStart = currentStationFormula - getDistanceBetweenStations(currentStation, endStation);
			
		    // retrieve connections
			Map<Station, Float> connections = currentStation.getConnections();
			
			//retrieve walking distance map
			Map<Station, Float> walkingDistance = currentStation.getWalkingDistance();
			
			// add them to the openStations list
			for(Map.Entry<Station, Float> station : connections.entrySet()) {
			    Station childStation = station.getKey();
			    // check if the child station is not in the closedStations list
			    if(!closedStations.contains(childStation)){
				    float distanceCost = station.getValue();
				    // get the extra cost (walking distance)
				    Float extraCost = walkingDistance.get(childStation);
				    // if the value is null set the walking distance to 0
				    if(extraCost == null){
				    	extraCost = 0f;
				    }
				    
				    float distanceToEnd = getDistanceBetweenStations(childStation, endStation);
				    float childFormula = distanceFromStart + distanceToEnd + distanceCost + extraCost;
				    
				    // add the childStation if the station is not in the openStations list
				    // or if the childStation has a smaller childFormula of the previous added
				    if(!openStations.containsKey(childStation) || openStations.get(childStation) >= childFormula){
				    	openStations.put(childStation, childFormula);
				    	//update the pathMap
				    	pathMap.put(childStation, currentStation);
				    }
			    }
			}
			
			// remove the current station from openStations list and add it to the closedStations list
			openStations.remove(currentStation);
			closedStations.add(currentStation);
		}
		
		return shortestPath;
	}
}
