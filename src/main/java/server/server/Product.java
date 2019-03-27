package server.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
public class Product {
	   
	   private String h;
	   
	   private String pt;

	   public String getId() {
	      return h;
	   }
	   
	   @XmlElement
	   public void seth(String h) {
	      this.h = h;
	   }
	   public String getName() {
	      return pt;
	   }
	   @XmlElement
	   public void setpt(String pt) {
	      this.pt = pt;
	   }
	   
	   public String toString() {
		return "PRODUCTO: " + h;
		   
	   
	   }
	}