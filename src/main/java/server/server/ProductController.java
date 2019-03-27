package server.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {
   private static Map<String, Product> productRepo = new HashMap<String, Product>();
   static {
      Product honey = new Product();
      honey.seth("1");
      honey.setpt("Honey");
      productRepo.put(honey.getId(), honey);
      
      Product almond = new Product();
      almond.seth("2");
      almond.setpt("Almond");
      productRepo.put(almond.getId(), almond);
   }

}