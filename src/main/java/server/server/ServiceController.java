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
    * PARA BALANCEAR LA CARGA DE LOS CLUSTERS, USAREMOS: tomcat balancer app spring boot
    * https://stackoverflow.com/questions/3877294/writing-a-weighted-load-balancing-algorithm
    * HAREMOS QUE SEAN THREADS LOS CLUSTERS Y Q HAGAN EL TRATAMIENTO PARA DEVOLVER EL XML
    * 
    * PUEDES TENER VARIAS COLAS Y IR METIENDO EN ORDEN 2,1, 2,1, 2,1 2,1 ,1, y que cada cluster vaya cogiendo; Weight-Based Load Balancing
    * CAMBIAR LA service url
    * 
    */
	
	
	@RequestMapping(
			value ="/getData", 
			params= {"accountCode","targetDevice","pluginVersion"},
			produces = { MediaType.APPLICATION_XML_VALUE},
			headers = "Accept=application/xml",
			method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<q> getEmployeeGood(@RequestParam("accountCode") String accountCode, @RequestParam("targetDevice") String targetDevice, @RequestParam("pluginVersion") String pluginVersion ) {

	    System.out.println("LOKO: " + targetDevice); 
	    System.out.println("LOKO: " + pluginVersion); 
	    
	    return new ResponseEntity<q>(new q(accountCode,targetDevice,pluginVersion), HttpStatus.OK);	
	}
}
