package graph;

public class SMNode {

	private long id;
	private double lat;
	private double lon;
	
	public SMNode(long id, double lat, double lon) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
	}

	public long getId(){
		return id;
	}
	
	public double getLatitude(){
		return lat;
	}
	
	public double getLongitude(){
		return lon;
	}
}
