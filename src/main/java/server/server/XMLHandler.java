package server.server;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	 * 
	 * If there is something to highlight, is the fact that in order to agilize the 
	 */
	public static Configuration check_configuration (String accountCode, String targetDevice, String pluginVersion, String configFile) {
		try {
	            File archivo = new File(configFile);
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
	            Document document = (Document) documentBuilder.parse(archivo);
	            
	            NodeList clients = document.getElementsByTagName("account");	//we look for all the different accounts
	            for(int temp = 0; temp < clients.getLength(); temp++) {	
	            	  Node nNode = clients.item(temp);
	            	  if(nNode.getNodeType() == Node.ELEMENT_NODE){
	            	    Element eElement = (Element) nNode;
	            	    if(accountCode.equalsIgnoreCase(eElement.getElementsByTagName("type").item(0).getTextContent())){
	            	    	
	            	    	for (int i=0;i<eElement.getElementsByTagName("targetDevice").getLength();i++) {
		            	    	if (eElement.getElementsByTagName("targetDevice").item(i).getTextContent().equalsIgnoreCase(targetDevice)) { //if the chosen platform is correct let's look at the plugin version
		            	    		NodeList devices = document.getElementsByTagName("device");
		            	    		for(int j = 0; j < devices.getLength(); j++) {
		            	    			Node node = devices.item(j);
		            	    			if(node.getNodeType() == Node.ELEMENT_NODE) {
		            	    				Element device = (Element) node;
		            	    				if (device.getElementsByTagName("type").item(0).getTextContent().equalsIgnoreCase(targetDevice)){ //if the device is correct le's see its possible versions
		            	    					if(device.getElementsByTagName("pluginVersion").item(0).getTextContent().equalsIgnoreCase(pluginVersion)) {//correct plugin version	            	    						
		            	    						
		            	    						Configuration config=new Configuration();  
		            	    						
		            	    						config.setPingTime(device.getElementsByTagName("pingTime").item(0).getTextContent()); 

		            	    						NodeList clusters = document.getElementsByTagName("cluster");
		            	    						
		            	    						Integer totalWeight=0;
		            	    						
		            	    						for (int k=0; k<clusters.getLength() ; k++) {
		            	    							if (clusters.item(k).getTextContent().contains(targetDevice)) {
		            	    			
		            	    								 totalWeight += Integer.parseInt(clusters.item(k).getTextContent().split("\n")[3]);
		            	    								 config.getClusters().put(totalWeight,clusters.item(k).getTextContent().split("\n")[2]);
		            	    													
		            	    							}
		            	    						}
		            	    						config.setTotalWeight(totalWeight);
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
	
	
	/*
	 * This method returns a structure containing all of the configuration of the xml file. The reason behind is the fact that this method is used to preload the configuration.
	 */
	public static  Map<String,Map<String,Configuration>> returnInfo (String configFile) {
		try {
				Map<String, Map<String, Configuration>> info = new ConcurrentHashMap<String,Map<String,Configuration>>();
	            File archivo = new File(configFile);
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
	            Document document = (Document) documentBuilder.parse(archivo);
	            
	            NodeList clients = document.getElementsByTagName("account");	//different account in the file
	            for(int temp = 0; temp < clients.getLength(); temp++) {	
	            	  Node nNode = clients.item(temp);//temp CAMBIO
	            	  if(nNode.getNodeType() == Node.ELEMENT_NODE){
	            	    Element eElement = (Element) nNode;
	            	    String client=eElement.getElementsByTagName("type").item(0).getTextContent();	//take the client    
	            	    //System.out.println(client);
	            	    for (int i=0;i<eElement.getElementsByTagName("targetDevice").getLength();i++) {
	            	    	NodeList devices = document.getElementsByTagName("device");
	            	    	for(int j = 0; j < devices.getLength(); j++) {
            	    			Node node = devices.item(j);
            	    			if(node.getNodeType() == Node.ELEMENT_NODE) {
            	    				Element device = (Element) node;
            	    				String technology=device.getElementsByTagName("type").item(0).getTextContent();
            	    				String plugin_version=device.getElementsByTagName("pluginVersion").item(0).getTextContent();
            	    				Configuration config=check_configuration(client,technology,plugin_version,configFile);
            	    				if (config!=null) {
  
            	    					Map<String,Configuration> hash = new Hashtable<String,Configuration>();
            	    					hash.put(technology+plugin_version, config);	//hash:tecno+plugin, value:treemap,pingtime

            	    					if (info.get(client)!=null) {
										
            	    						
            	    						if (info.get(client).get(technology+plugin_version)!=null) {
            	    							
            	    						}
            	    						else info.get(client).put(technology+plugin_version, config);
            	    						
            	    					}
            	    					else info.put(client, hash);
            	    				
            	    				}
            	    				
            	    				
            	    			
            	    			}
	            	  
	            	    	}
	            	    	
	            	    }
	            	   
	            	  }
	            }
	          
	            return info;	//url information not correct and then null object is sent
	        			            			   		
				}
	            catch (Exception e) {
	                e.printStackTrace();
	            }
		   
		  return null;	//url information not correct and then null object is sent. Besides the method caught an exception.
		}	
	
	}
