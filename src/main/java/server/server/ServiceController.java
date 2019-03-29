package server.server;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    * http://dataService.com/getData?accountCode=clienteA&targetDevice=XBox&pluginVersion=3.3.1 
    * 
    */
	@RequestMapping(
			value ="/getData", 
			params= {"accountCode","targetDevice","pluginVersion"},
			produces = { MediaType.APPLICATION_XML_VALUE},
			headers = "Accept=application/xml",
			method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<q> getEmployeeGood(@RequestParam("accountCode") String accountCode, @RequestParam("targetDevice") String targetDevice, @RequestParam("pluginVersion") String pluginVersion ) {
		System.out.println("LOKO: " + 	accountCode);
	    System.out.println("LOKO: " + targetDevice); 
	    System.out.println("LOKO: " + pluginVersion); 
	    
	    Configuracio config= (XMLHandler.check_configuration(accountCode,targetDevice,pluginVersion));
	    return new ResponseEntity<q>(new q(config.getClusters().firstKey(),String.valueOf(config.getPingTime()),config.getCode()), HttpStatus.OK);	
	}
}
