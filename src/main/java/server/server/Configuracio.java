package server.server;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
 
/* Programmer: Jose Angel Morena
 * This class encapsulates the information of the configuration to be delivered to the client.
 * There is a Map to store the clusters because there may be more than one to handle the petition. The structure is a map because
 * the name of the cluster is associated to its weight. 
 */
public class Configuracio {

	private Map<String, Integer> clusters = new HashMap<String,Integer>();
	private int pingTime;
	private String code;
	
	public Configuracio() {
		this.code= UUID.randomUUID().toString();; //generate unique code
	}

	public Map<String, Integer> getClusters() {
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
