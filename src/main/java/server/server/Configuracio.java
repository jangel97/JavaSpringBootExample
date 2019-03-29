package server.server;

import java.util.TreeMap;
import java.util.UUID;
 

public class Configuracio {

	private TreeMap<String, Integer> clusters = new TreeMap<String,Integer>();
	private int pingTime;
	private String code;
	
	public Configuracio() {
		this.code= UUID.randomUUID().toString();; //generate unique code
	}

	public TreeMap<String, Integer> getClusters() {
		return clusters;
	}

	public void addCluster(String cluster, int weight) {
		clusters.put(cluster, weight);
	}
	
	public void setClusters(TreeMap<String, Integer> clusters) {
		this.clusters = clusters;
	}

	public int getPingTime() {
		return pingTime;
	}

	public void setPingTime(int pingTime) {
		this.pingTime = pingTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String toString() {
		return "Code: " + this.code + "; pingTime: " + this.pingTime + "; clusters: " + this.clusters;
		
	}

	
}
