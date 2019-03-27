package server.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
public class Product {
	   private String id;
	   private String name;

	   public String getId() {
	      return id;
	   }
	   
	   @XmlElement
	   public void setId(String id) {
	      this.id = id;
	   }
	   public String getName() {
	      return name;
	   }
	   
	   @XmlElement
	   public void setName(String name) {
	      this.name = name;
	   }
	   
	   public String toString() {
		return "PRODUCTO: " + id;
		   
	   
	   }
	}