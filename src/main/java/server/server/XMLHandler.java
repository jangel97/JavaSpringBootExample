package server.server;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class XMLHandler {
	
		private static XMLHandler INSTANCE;
		private XMLHandler() {}
		     
		public static XMLHandler getInstance() {
		        if(INSTANCE == null) {
		            INSTANCE = new XMLHandler();
		        }
		         
		        return INSTANCE;
		 }
		    
	public static Configuracio check_configuration (String accountCode, String targetDevice, String pluginVersion) {
		Configuracio config=new Configuracio();  
		try {
	            File archivo = new File("Configuration.xml");
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
	            Document document = (Document) documentBuilder.parse(archivo);
	            
	            NodeList clients = document.getElementsByTagName("account");	//miramos los accounts diferentes que hay segun el fichero XML
	            for(int temp = 0; temp < clients.getLength(); temp++) {	//0 account A, la primera ; 1 account B, la segunda.
	            	  Node nNode = clients.item(temp);
	            	  if(nNode.getNodeType() == Node.ELEMENT_NODE){
	            	    Element eElement = (Element) nNode;
	            	    if(accountCode.equalsIgnoreCase(eElement.getElementsByTagName("type").item(0).getTextContent())){
	            	    	System.out.println("EL CLIENTE SI QUE ESTA, "+ accountCode);
	            	    	for (int i=0;i<eElement.getElementsByTagName("targetDevice").getLength();i++) {
		            	    	//System.out.println(eElement.getElementsByTagName("targetDevice").item(i).getTextContent());
		            	    	if (eElement.getElementsByTagName("targetDevice").item(i).getTextContent().equalsIgnoreCase(targetDevice)) {
		            	    		//la plataforma elegida es correcta, vamos a mirar la informacion del plugin
		            	    		System.out.println("PLATAFORMA CORRECTA, "+targetDevice );
		            	    		NodeList devices = document.getElementsByTagName("device");
		            	    		for(int j = 0; j < devices.getLength(); j++) {
		            	    			Node node = devices.item(j);
		            	    			if(node.getNodeType() == Node.ELEMENT_NODE) {
		            	    				Element device = (Element) node;
		            	    				//System.out.println(device.getElementsByTagName("type").item(0).getTextContent());
		            	    				if (device.getElementsByTagName("type").item(0).getTextContent().equalsIgnoreCase(targetDevice)){
		            	    					//si el dispositivo es el elegido, consultaremos sus versiones disponibles
		            	    					if(device.getElementsByTagName("pluginVersion").item(0).getTextContent().equalsIgnoreCase(pluginVersion)) {
		            	    						System.out.println("VERSION DE PLUGIN CORRECTA: " +pluginVersion );
		            	    						
		            	    						System.out.println("PING ITMEEE: " +Integer.parseInt(device.getElementsByTagName("pingTime").item(0).getTextContent()) );
		            	    						config.setPingTime(Integer.parseInt(device.getElementsByTagName("pingTime").item(0).getTextContent()));
		            	    						config.setPingTime(Integer.parseInt(device.getElementsByTagName("pingTime").item(0).getTextContent()));
		            	    						//la version del plugin es correcta, vamos a ver los clusters que se usaran
		            	    						NodeList clusters = document.getElementsByTagName("cluster");
		            	    						for (int k=0; k<clusters.getLength() ; k++) {
		            	    							if (clusters.item(k).getTextContent().contains(targetDevice)) {
		            	    								System.out.print(clusters.item(k).getTextContent().split("\n")[2]);System.out.println("kvjvk");
		            	    								config.addCluster(clusters.item(k).getTextContent().split("\n")[2], Integer.parseInt(clusters.item(k).getTextContent().split("\n")[3]));
		            	    							}
		            	    						}
		            	    						return config;
		            	    					}
		            	    					else {
		            	    						//System.out.println("VERSION NO CORRECTA");
			            	    					//else version del plugin no es buena
		            	    						//return null;
		            	    					}
		            	    					
		            	    					
		            	    				}
		            	    			}
		            	    		}
		            	    	}else {
		            	    		//System.out.println("PLATAFORMA NO CORRECTA");
			            	    	//la plataforma no es correcta
		            	    		//return null;
		            	    	}
		            	    
	            	    	}
	            	    }else {
	            	    	//System.out.println("NO EXISTE CLIENTE");;
		            	    //CLIENTE NO ESTA
	            	    	//return null;
	            	    }
	            	    
	            	    
	            	  }
	            }
	            return null;
	        			            			   		
	      }
	            catch (Exception e) {
	                e.printStackTrace();
	            }
		   
		  return config;
		}	
	/*
	public static void main (String args[]) {
		System.out.println(XMLHandler.check_configuration("clienteA","XBox","3.3.1"));;
	}
	*/
	
	}
