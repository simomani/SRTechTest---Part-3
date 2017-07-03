package SilverRail;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Simone Maniero
 * 
 */
public class Station
{
	private String name;
	private double posX;
	private double posY;
	
	// map of connections among Stations and the specific distance between them
	Map<Station, Float> connections = new HashMap<>();
	// map that keeps track of the walking distance between platforms
	// if the walking distance is missing or equal to 0 from train to train
	// it means the train is the same and there is no need to change it
	Map<Station, Float> walkingDistance = new HashMap<>();
	
	/**
	 * Station Constructor receiving the coordinates x and y in an hypothetical map
	 * @param x Position x of the Station
	 * @param y Position y of the Station
	 */
	public Station(String n, double x, double y) {
		name = n;
		posX = x;
		posY = y;
	}

	/**
	 * @return the name of the Station
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name Set the name of the Station
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return position X of the Station
	 */
	public double getPosX() {
		return posX;
	}

	/**
	 * @param posX Set the position X of the Station
	 */
	public void setPosX(double posX) {
		this.posX = posX;
	}

	/**
	 * @return position Y of the Station
	 */
	public double getPosY() {
		return posY;
	}

	/**
	 * @param posY Set the position Y of the Station
	 */
	public void setPosY(double posY) {
		this.posY = posY;
	}
	
	/**
	 * @return map of connections of the Station
	 */
	public Map<Station, Float> getConnections() {
		return connections;
	}

	/**
	 * @param connections Set the connections of stations for the invoked Station
	 */
	public void setConnections(Map<Station, Float> connections) {
		this.connections = connections;
	}
	
	/**
	 * @return map of walking distances of the Station
	 */
	public Map<Station, Float> getWalkingDistance() {
		return walkingDistance;
	}

	/**
	 * @param walkingDistance Set the walking distance map for the invoked Station
	 */
	public void setWalkingDistance(Map<Station, Float> walkingDistance) {
		this.walkingDistance = walkingDistance;
	}

	/**
	 * @param newStation The new Station to be added into the map of connections.
	 * @param distance The distance between the current Station and the new Station. The distance
	 * must be positive or equal to 0.
	 */
	public void addConnection(Station newStation, Float distance){
		// check if the station is null and not already in the map
		if(newStation != null && !connections.containsKey(newStation)){
			if(distance < 0f)
			{
				distance = 0f;
			}
			connections.put(newStation, distance);
		}
	}
	
	/**
	 * @param station The Station (platform for the Station) to be added into the map of walking distances.
	 * @param distance The distance between the current platform and the new platform. The distance must be positive or equal to 0
	 */
	public void addWalkingDistances(Station station, Float distance){
		// check if the station is null and if the distance is greater than 0
		if(station != null && distance > 0f){
			walkingDistance.put(station, distance);
		}
	}
}
