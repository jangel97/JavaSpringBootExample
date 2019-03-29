package server.server;

import java.io.File;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ServiceController { 
   
	
	private String configFile="Configuration.xml";
	
	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}
	
	/*
	 * url example:
	 * 
	 */
	@RequestMapping(value = "/changeXML",params= {"configFile"},method = RequestMethod.GET)
	public ResponseEntity<?> updateConfigFile(@RequestParam("configFile") String file) { 
		
		File f = new File(file);
		System.out.println(f);
		if(f.exists() && !f.isDirectory()) { //file is updated if and only if it exisits 
			System.out.println("FICHERO SI QUE EXISTEEEEE");
			this.configFile=file;	
			return new ResponseEntity<String>("XML configuration file has been updated to: "+file, HttpStatus.OK);
	    }
		System.out.println("PETITION");
		//otherwise path is not going to be updated    
	    return new ResponseEntity<String>("Unexisting XML", HttpStatus.OK);
	   } 
	
	
	/*
    * url example
    * http://localhost/getData?accountCode=clienteA&targetDevice=XBox&pluginVersion=3.3.1 
    *  This application has no explicit mapping for /error, so you are seeing this as a fallback.
    */
	@RequestMapping(
			value ="/getData", 
			params= {"accountCode","targetDevice","pluginVersion"},
			produces = { MediaType.APPLICATION_XML_VALUE},
			headers = "Accept=application/xml",
			method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> platformPetition(@RequestParam("accountCode") String accountCode, @RequestParam("targetDevice") String targetDevice, @RequestParam("pluginVersion") String pluginVersion ) {
	   
		Configuracio config= XMLHandler.check_configuration(accountCode,targetDevice,pluginVersion,this.configFile); //extract data considering the xml configuration
		
		if (config==null) return new ResponseEntity<String>(new String("Provided information is not matching xml configuration"),
                HttpStatus.NOT_FOUND);
		
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
	
	
}
