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
    private static Map<String, Product> productRepo = new HashMap<String, Product>();
   static {
      Product honey = new Product();
      honey.setId("1");
      honey.setName("Honey");
      productRepo.put(honey.getId(), honey);
      
      Product almond = new Product();
      almond.setId("2");
      almond.setName("Almond");
      productRepo.put(almond.getId(), almond);
   }
   

   @RequestMapping(value = "/products")
   public ResponseEntity<Collection<Product>> getProduct() {
      return new ResponseEntity<Collection<Product>>(productRepo.values(), HttpStatus.OK);
   }
   
	@RequestMapping(value ="/getData/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Collection<Product>> getEmployee(@PathVariable("id") int empId) {
	    System.out.println("LOKO: " + productRepo);  
	    System.out.println("LOKO: " + productRepo.values()); 
	    System.out.println(Arrays.asList( productRepo.get(String.valueOf(String.valueOf(empId)))));
	    return new ResponseEntity<Collection<Product>>(Arrays.asList( productRepo.get(String.valueOf(empId))), HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value ="/getData", 
			params= {"id","second"},
			//produces = { "application/xml", "text/xml" }, consumes = MediaType.ALL_VALUE,
			produces = { MediaType.APPLICATION_XML_VALUE},
			headers = "Accept=application/xml",
			method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Collection<Product>> getEmployeeGood(@RequestParam("id") int id, @RequestParam("second") String second) {
	    System.out.println("LOKO: " +id);  
	    System.out.println("LOKO: " + second); 

	    return new ResponseEntity<Collection<Product>>(Arrays.asList( productRepo.get(String.valueOf(id))), HttpStatus.OK);
		
	}
}