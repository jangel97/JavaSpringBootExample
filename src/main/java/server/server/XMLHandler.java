package server.server;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* Programmer: Jose Angel Morena
 * This class implements singleton pattern, because in this case it does not require to be instanced more than once.
 * Its goal is to accomplish that the provided information in the request url is correct according to the xml configuration file.
*/
public final class XMLHandler {
	
		private static XMLHandler INSTANCE;
		private XMLHandler() {}
		     
		public static XMLHandler getInstance() {
		        if(INSTANCE == null) {
		            INSTANCE = new XMLHandler();
		        }
		         
		        return INSTANCE;
		 }
		    
	/* This method handles the configuration file content according to the accountCode, the targetDevice and the pluginVersion.
	 * It also requires the filename of the configuration file to read.
	 * If the provided information is not correct because the client does not exist or does not have that target device assigned or there is not
	 * matching plugin version, then a null object is returned. Otherwise a Configuration instance is returned with all of the information necessary to 
	 * return, besides from a Map containing the clusters to work and its corresponding weights.
	 * 
	 * Partly the code looks so big because the method getElementsByTagName returns a list with no iterable so I had to check element by element. There was
	 * neither a filter method.
	 */
	public static Configuracio check_configuration (String accountCode, String targetDevice, String pluginVersion, String configFile) {
		try {
	            File archivo = new File(configFile);
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
	            Document document = (Document) documentBuilder.parse(archivo);
	            
	            NodeList clients = document.getElementsByTagName("account");	//miramos los accounts diferentes que hay segun el fichero XML
	            for(int temp = 0; temp < clients.getLength(); temp++) {	//0 account A, la primera ; 1 account B, la segunda.
	            	  Node nNode = clients.item(temp);
	            	  if(nNode.getNodeType() == Node.ELEMENT_NODE){
	            	    Element eElement = (Element) nNode;
	            	    if(accountCode.equalsIgnoreCase(eElement.getElementsByTagName("type").item(0).getTextContent())){
	            	    	
	            	    	for (int i=0;i<eElement.getElementsByTagName("targetDevice").getLength();i++) {
		            	    	if (eElement.getElementsByTagName("targetDevice").item(i).getTextContent().equalsIgnoreCase(targetDevice)) { //la plataforma elegida es correcta, vamos a mirar la informacion del plugin
		            	    		NodeList devices = document.getElementsByTagName("device");
		            	    		for(int j = 0; j < devices.getLength(); j++) {
		            	    			Node node = devices.item(j);
		            	    			if(node.getNodeType() == Node.ELEMENT_NODE) {
		            	    				Element device = (Element) node;
		            	    				if (device.getElementsByTagName("type").item(0).getTextContent().equalsIgnoreCase(targetDevice)){ //si el dispositivo es el elegido, consultaremos sus versiones disponibles
		            	    					if(device.getElementsByTagName("pluginVersion").item(0).getTextContent().equalsIgnoreCase(pluginVersion)) {//VERSION DE PLUGIN CORRECTA		            	    						
		            	    						
		            	    						Configuracio config=new Configuracio();  
		            	    						
		            	    						config.setPingTime(Integer.parseInt(device.getElementsByTagName("pingTime").item(0).getTextContent())); 
		            	    						
		            	    						NodeList clusters = document.getElementsByTagName("cluster");
		            	    						
		            	    						for (int k=0; k<clusters.getLength() ; k++) {
		            	    							if (clusters.item(k).getTextContent().contains(targetDevice)) {
		            	    								config.addCluster(clusters.item(k).getTextContent().split("\n")[2], Integer.parseInt(clusters.item(k).getTextContent().split("\n")[3]));
		            	    							}
		            	    						}
		            	    						return config;
		            	    					}
		            	    					//Incorrect version
		            	    					
		            	    				}
		            	    			}
		            	    		}
		            	    	}//Incorrect platform
		            	    
	            	    	}
	            	    }//Non-existing client
	            	    
	            	    
	            	  }
	            }
	            return null;	//url information not correct and then null object is sent
	        			            			   		
	      }
	            catch (Exception e) {
	                e.printStackTrace();
	            }
		   
		  return null;	//url information not correct and then null object is sent. Besides the method caught an exception.
		}	
	}
