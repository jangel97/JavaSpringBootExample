package server.server;

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/* Programmer: Jose Angel Morena
 * Rest controller class.
 * It also controls errors.
 */
@RestController
public class ServiceController implements ErrorController { 
		
	private Random seed = new Random();//random generator
	
	/*This attribute is going to store all of the configuration. Its aim is to provide a preload because this way
	 * the access to the data is going to be much quicker in the rest call
	 * 
	 * I assumed per each technology there is a single version to avoid a one more hash entrance.
	 */
	private Map<String,Map<String,Configuration>> info;

	private String configFile="Configuration.xml";	//filename of configuration file
	
	//un cliente tiene varias plataformas
	//una plataforma UN UNICO PLUGIN, 
	//plataforma y plugin sera la clave para entrar en PING TIME Y TREEMAP DE SERVERS
	public ServiceController() {	
		info = XMLHandler.returnInfo(this.configFile); //extract data considering the xml configuration
	}
	
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
	 * 
	 * It is possible to modify the current xml configuration file and then by using the rest call reload that new configuration
	 */
	@RequestMapping(value = "/changeXML",params= {"configFile"},method = RequestMethod.GET)
	public ResponseEntity<?> updateConfigFile(@RequestParam("configFile") String file) { 
		
		File f = new File(file);
		if(f.exists() && !f.isDirectory()) { //file is updated if and only if it exisits 
			this.configFile=file;
			info = XMLHandler.returnInfo(this.configFile); //extract data considering the xml configuration
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
	*    
	*    The return, chooses a random cluster following a uniform distribution, the reason behind is to distribute more uniformly 
			 the chosen cluster according to the clusters of that technology
	*    
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
		try {
			Configuration config = info.get(accountCode).get(targetDevice+pluginVersion);
			return new ResponseEntity<q>(new q(config.getClusters().ceilingEntry(new Random().nextInt(config.getTotalWeight()))
					.getValue(),config.getPingTime(),new UUID(this.seed.nextLong(), this.seed.nextLong()).toString()), HttpStatus.OK);			
		}
		catch (Exception e) {
			return null;
        }
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
