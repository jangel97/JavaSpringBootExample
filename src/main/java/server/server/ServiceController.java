package server.server;

import java.io.File;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * Rest controller class.
 * It also controls errors.
 */
@RestController
public class ServiceController implements ErrorController { 
   
	/*
	 * filename of configuration file
	 */
	private String configFile="Configuration.xml";
	
	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}
	
	/* The aim of this method is to change the xml configuration filename in the service run time, through a rest call.
	 * url example:
	 * http://localhost/changeXML?configFile=Configuration.xml
	 * http://localhost/changeXML?configFile=Configuration2.xml
	 */
	@RequestMapping(value = "/changeXML",params= {"configFile"},method = RequestMethod.GET)
	public ResponseEntity<?> updateConfigFile(@RequestParam("configFile") String file) { 
		
		File f = new File(file);
		System.out.println(f);
		if(f.exists() && !f.isDirectory()) { //file is updated if and only if it exisits 
			this.configFile=file;	
			return new ResponseEntity<String>("XML configuration file has been updated to: "+file, HttpStatus.OK);
	    }
		//otherwise path is not going to be updated    
	    return new ResponseEntity<String>("Unexisting XML", HttpStatus.OK);
	   } 
	
	
	/*
	*
	* The aim of this method is to return the xml as was stated in the documentation but also balancing the load of each cluster.
	* In order to balance the load for each cluster, I decided to implement a roulette wheel algorithm.
	* Firstly, the url params are checked by the XMHandler. In case the params are correct, an object is returned with the information of the response encapsulated and the list of clusters.
	* Afterwards, the cluster is chosen by the algorithm.
	* Eventually the xml is returned.
	*    
    * url example:
    * http://localhost/getData?accountCode=clienteA&targetDevice=XBox&pluginVersion=3.3.1 
    */
	@RequestMapping(
			value ="/getData", 
			params= {"accountCode","targetDevice","pluginVersion"},
			produces = { MediaType.APPLICATION_XML_VALUE},
			headers = "Accept=application/xml",
			method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> platformPetition(@RequestParam("accountCode") String accountCode, @RequestParam("targetDevice") String targetDevice, @RequestParam("pluginVersion") String pluginVersion ) {
	   
		Configuracio config= XMLHandler.check_configuration(accountCode,targetDevice,pluginVersion,this.configFile); //extract data considering the xml configuration
		
		if (config==null) return new ResponseEntity<String>("",HttpStatus.OK);
		
		String cluster=chooseCluster(config);
	    return new ResponseEntity<q>(new q(cluster,String.valueOf(config.getPingTime()),config.getCode()), HttpStatus.OK);
	}

	
	/*
	 * Choose cluster method
	 * Roulette wheel selection algorithm 
	 */
	public String chooseCluster(Configuracio config) {
	    TreeMap<Integer, String> pool= new TreeMap<Integer, String>();
	    Integer totalWeight=0;
	    for ( Entry<String, Integer> entry : config.getClusters().entrySet() ) {
	        totalWeight += entry.getValue();
	        pool.put(totalWeight, entry.getKey());
	    }	    
	    
	    int rnd = new Random().nextInt(totalWeight);
	    
	    return pool.ceilingEntry(rnd).getValue();
	}

	/*
	 * error handler
	 */
	@RequestMapping("/error")
    public String handleError() {
        return "Hey there, try to check the URL!";
    }
	
	public String getErrorPath() {
		 return "/error";
	}
	
	
}
