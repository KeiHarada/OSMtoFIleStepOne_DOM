package graph;

public class SMEdge {

	private SMNode from;
	private SMNode to;
	private double distance;

	public SMEdge(SMNode from, SMNode to){
		this.from = from;
		this.to = to;
		this.distance = dist(from, to);
	}

	/* Calculates distance from node to node using longitude and latitude. */
	public static double dist(SMNode from, SMNode to) {
		double theta = from.getLongitude() - to.getLongitude();
		double dist = Math.sin(deg2rad(from.getLatitude()))*Math.sin(deg2rad(to.getLatitude()))+Math.cos(deg2rad(from.getLatitude()))*Math.cos(deg2rad(to.getLatitude()))*Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return (dist * 1000.0);
	}

	/* Converts Decimal degrees to Radians */
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	/* Converts Radians to Decimal degrees */
	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
	
	public SMNode getFrom(){
		return from;
	}
	
	public SMNode getTo(){
		return to;
	}
	
	public double getDist(){
		return distance;
	}

}
