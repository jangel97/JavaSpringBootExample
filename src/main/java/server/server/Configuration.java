package server.server;

import java.util.TreeMap;
 
/* Programmer: Jose Angel Morena
 * This class encapsulates the information of the configuration to be delivered to the client.
 * There is a Map to store the clusters because there may be more than one to handle the petition. The structure is a map because
 * the name of the cluster is associated to its weight. 
 */
public class Configuration {

	private Integer totalWeight;
	/*
	 * This treemap is here for a single reason. It is needed to decide which cluster is going to be used, given a configuration.
	 * It is going to be used in the algorithm of the roulette to distribute the load among clusters
	 */
	private TreeMap<Integer, String> clusters= new TreeMap<Integer, String>();
	private String pingTime;

	
	public Configuration() {
		//this.code= UUID.randomUUID().toString();; //generate unique code
	}

	public TreeMap<Integer, String> getClusters() {
		return clusters;
	}
/*
	public void addCluster(String cluster, int weight) {
		clusters.put(cluster, weight);
	}
	
	public void setClusters(TreeMap<String, Integer> clusters) {
		this.clusters = clusters;
	}
*/
	public String getPingTime() {
		return pingTime;
	}

	public void setPingTime(String pingTime) {
		this.pingTime = pingTime;
	}

	public String toString() {
		return  "; pingTime: " + this.pingTime + "; clusters: " + this.clusters;
		
	}

	public Integer getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Integer totalWeight) {
		this.totalWeight = totalWeight;
	}

	
}
