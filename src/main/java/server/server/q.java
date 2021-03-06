package server.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*Programmer: Jose Angel Morena
 * This class encapsulates the information to be returned in the request.
 */
@SuppressWarnings({ "restriction", "unused" })
@XmlRootElement
public class q {
		//@XmlElement
	    private String h;	//cluster
		//@XmlElement
		private String pt;	//pingtime
		//@XmlElement
		private String c;	//code

		public q(String h, String pt, String c) {
			this.h=h;
			this.c=c;
			this.pt=pt;
		}
	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}
	   	
	public String toString() {
		return c;
	}
	   
}
