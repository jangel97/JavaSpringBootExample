package server.server;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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

import javax.xml.bind.annotation.XmlRootElement;


@RestController
public class ServiceController { 
   /*
    *http://localhost/getData?accountCode=clienteA&targetDevice=XBox&pluginVersion=3.3.1
    * 
    * PREGUNTAR SI ALGORITMO DE RULETA O MODULOS?
    * POST QUE MODIFICA EL XML
    *  This application has no explicit mapping for /error, so you are seeing this as a fallback.
    */
	@RequestMapping(
			value ="/getData", 
			params= {"accountCode","targetDevice","pluginVersion"},
			produces = { MediaType.APPLICATION_XML_VALUE},
			headers = "Accept=application/xml",
			method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<q> platformPetition(@RequestParam("accountCode") String accountCode, @RequestParam("targetDevice") String targetDevice, @RequestParam("pluginVersion") String pluginVersion ) {
	   
		Configuracio config= (XMLHandler.check_configuration(accountCode,targetDevice,pluginVersion));
		//Escollir cluster
	    TreeMap<Integer, String> pool= new TreeMap<Integer, String>();
	    Integer totalWeight=0;
	    for ( Entry<String, Integer> entry : config.getClusters().entrySet() ) {
	        totalWeight += entry.getValue();
	        pool.put(totalWeight, entry.getKey());
	    }	    
	    int rnd = new Random().nextInt(totalWeight);
	    String prediccion=pool.ceilingEntry(rnd).getValue();;
	    return new ResponseEntity<q>(new q(prediccion,String.valueOf(config.getPingTime()),config.getCode()), HttpStatus.OK);
	}
}
