package com.example.main;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.example.main.BMSState;


	public class XMLParser {

	    public static final String TOTAL_BATTERY_CAPACITY = "totalBatteryCapacity";                //Total battery capacity provided by Manufacturer
	    public static final String TOTAL_CAPACITY_EACH_CELLS = "totalCapacityEachCell";				//Total capacity of each cell can hold
	    public static final String CHARGE_AMOUNT_CELL1 = "chargeCell1";                            //Amount of charge in Cell 1, provided by user
	    public static final String CHARGE_AMOUNT_CELL2 = "chargeCell2";                            //Amount of charge in Cell 2, provided by user
	    public static final String CHARGE_AMOUNT_CELL3 = "chargeCell3";                            //Amount of charge in Cell 3, provided by user
	    public static final String CHARGE_AMOUNT_CELL4 = "chargeCell4";                            //Amount of charge in Cell 4, provided by user
	    public static final String CHARGE_AMOUNT_CELL5 = "chargeCell5";                            //Amount of charge in Cell 5, provided by user
	    public static final String BATTERY_LEVEL = "batteryLevel";                                //Total battery level (%) in the batter, stored by Charge Group
	    public static final String CAR_STATUS = "carstatus";  										//Car status - moving or charging
	    public static final String BATTERY_CHARGE_AMOUNT = "batteryChargeLevel";                    //Total charge amount in the battery
	    public static BMSState BMS_STATE;
	    public BatteryReport socBatteryReport;							//Object for storing observers to the list, used by Charge group
	    BatteryMonitor chargeBatteryMonitor;

	    
	    public static ConcurrentHashMap<String, Object> centralStorage;                //Collection for storing data of BMS in (key, pair) format
	    
	    public XMLParser() throws ValueOutOfBoundException {
	        centralStorage = new ConcurrentHashMap<String, Object>();
	        BMS_STATE = BMSState.IDLE;
	        
	        socBatteryReport=new BatteryReport();
	      
	    }


	    public static void reader() throws ValueOutOfBoundException {
	    	
	    	try {
	    	File fXmlFile = new File("src/database.xml");
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(fXmlFile);
	    			
	    	doc.getDocumentElement().normalize();

	    //	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	    			
	    	NodeList nList = doc.getElementsByTagName("cellSOCInput");
	    	NodeList nList1 = doc.getElementsByTagName("cellCapacity");
	    	NodeList nList2 = doc.getElementsByTagName("batteryCapacity");
	    	NodeList nList3 = doc.getElementsByTagName("carStatus");	    			
	    	System.out.println("----------------------------");
	    	
	    	for (int temp = 0; temp < nList.getLength(); temp++) {

	    		Node nNode = nList.item(temp);
	    				
	    	//	System.out.println("\nCurrent Element :" + nNode.getNodeName());
	    				
	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	    			Element eElement = (Element) nNode;
/*
	    			System.out.println("chargeCell1 : " + eElement.getElementsByTagName("chargeCell").item(0).getTextContent());
	    			System.out.println("chargeCell2 : " + eElement.getElementsByTagName("chargeCell").item(1).getTextContent());
	    			System.out.println("chargeCell3 : " + eElement.getElementsByTagName("chargeCell").item(2).getTextContent());
	    			System.out.println("chargeCell4 : " + eElement.getElementsByTagName("chargeCell").item(3).getTextContent());
	    			System.out.println("chargeCell5 : " + eElement.getElementsByTagName("chargeCell").item(4).getTextContent());*/
	    			
	    			String chargeCell1 = eElement.getElementsByTagName("chargeCell").item(0).getTextContent();
	    			centralStorage.put(XMLParser.CHARGE_AMOUNT_CELL1, Float.parseFloat(chargeCell1));
	    			String chargeCell2 = eElement.getElementsByTagName("chargeCell").item(0).getTextContent();
	    			centralStorage.put(XMLParser.CHARGE_AMOUNT_CELL2, Float.parseFloat(chargeCell2));
	    			String chargeCell3 = eElement.getElementsByTagName("chargeCell").item(0).getTextContent();
	    			centralStorage.put(XMLParser.CHARGE_AMOUNT_CELL3, Float.parseFloat(chargeCell3));
	    			String chargeCell4 = eElement.getElementsByTagName("chargeCell").item(0).getTextContent();
	    			centralStorage.put(XMLParser.CHARGE_AMOUNT_CELL4, Float.parseFloat(chargeCell4));
	    			String chargeCell5 = eElement.getElementsByTagName("chargeCell").item(0).getTextContent();
	    			centralStorage.put(XMLParser.CHARGE_AMOUNT_CELL5, Float.parseFloat(chargeCell5));


	    		}
	    	}
	    	
	    	for (int temp = 0; temp < nList1.getLength(); temp++) {
	    		
	    		Node nNode = nList1.item(temp);
	    		System.out.println("\nCurrent Element :" + nNode.getNodeName());
	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	    			Element eElement = (Element) nNode;

	    			//System.out.println("totalCapacityEachCell : " + eElement.getElementsByTagName("totalCapacityEachCell").item(0).getTextContent());
	    			String cellCapacity = eElement.getElementsByTagName("totalCapacityEachCell").item(0).getTextContent();
	    			centralStorage.put(XMLParser.TOTAL_CAPACITY_EACH_CELLS, Float.parseFloat(cellCapacity));
	    		}
	    	}
	    	for (int temp = 0; temp < nList2.getLength(); temp++) {
	    		
	    		Node nNode = nList2.item(temp);
	    		System.out.println("\nCurrent Element :" + nNode.getNodeName());
	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	    			Element eElement = (Element) nNode;

	    		//	System.out.println("totalBatteryCapacity : " + eElement.getElementsByTagName("totalBatteryCapacity").item(0).getTextContent());
	    			String batteryCapacity = eElement.getElementsByTagName("totalBatteryCapacity").item(0).getTextContent();
	    			centralStorage.put(XMLParser.TOTAL_BATTERY_CAPACITY, Float.parseFloat(batteryCapacity));
	    		}
	    	}
	    	for (int temp = 0; temp < nList3.getLength(); temp++) {
	    		
	    		Node nNode = nList3.item(temp);
	    	//	System.out.println("\nCurrent Element :" + nNode.getNodeName());
	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	    			Element eElement = (Element) nNode;

	    		//	System.out.println("status : " + eElement.getElementsByTagName("status").item(0).getTextContent());
	    			String carStatus = eElement.getElementsByTagName("status").item(0).getTextContent();
	    			if (carStatus.equalsIgnoreCase("driving")) {
	    	            setBMSStatus(BMSState.ONMOVE);
	    			}
	    			else if(carStatus.equalsIgnoreCase("charging")){
	    				setBMSStatus(BMSState.CHARGING);
	    			}
	    			centralStorage.put(XMLParser.CAR_STATUS, carStatus);
	    		}
	    	}
	    	
	        } catch (Exception e) {
	    	e.printStackTrace();
	        }
	    }

	  //Function to store data in Collection
	    public synchronized static void storeDataInCollection(String key, Object data) {
	        centralStorage.put(key, data);
	    }

	    //Function to get data from Collection
	    public static Object getDataInCollection(String key) {
	        return centralStorage.get(key);
	    }
	    
	    public static void writer() throws ValueOutOfBoundException {
	    	try {
	    		String filepath = "src/database.xml";
	    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    		Document doc1 = docBuilder.parse(filepath);
	    		
	    		// Get the element by tag name directly
	    		Node tag1 = doc1.getElementsByTagName("batteryLevel").item(0);
	    		Node tag2 = doc1.getElementsByTagName("chargeAmount").item(0);


	    				// loop the staff child node
	    				NodeList list = tag1.getChildNodes();
	    				NodeList list1 = tag2.getChildNodes();

	    				for (int i = 0; i < list.getLength(); i++) {
	    					
	    		                   Node node = list.item(i);

	    				   // get the salary element, and update the value
	    				   if ("bCharge".equals(node.getNodeName())) {
	    					node.setTextContent (Integer.toString((int) getDataInCollection(XMLParser.BATTERY_LEVEL)));
	    				   }

	    				}
	    				
	    				for (int i = 0; i < list1.getLength(); i++) {
	    					
 		                   Node node = list1.item(i);

 				   // get the salary element, and update the value
 				   if ("batteryCharge".equals(node.getNodeName())) {
 					node.setTextContent (Float.toString((Float) getDataInCollection(XMLParser.BATTERY_CHARGE_AMOUNT)));
 				   }

 				}

	    				// write the content into xml file
	    				TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    				Transformer transformer = transformerFactory.newTransformer();
	    				DOMSource source = new DOMSource(doc1);
	    				StreamResult result = new StreamResult(new File(filepath));
	    				transformer.transform(source, result);

	    				System.out.println("Written to the XML document");

	    			   } catch (ParserConfigurationException pce) {
	    				pce.printStackTrace();
	    			   } catch (TransformerException tfe) {
	    				tfe.printStackTrace();
	    			   } catch (IOException ioe) {
	    				ioe.printStackTrace();
	    			   } catch (SAXException sae) {
	    				sae.printStackTrace();
	    			   }

	    	}
	    
	    public static String getBMSStatus() {
	        return BMS_STATE.toString();
	    }

	    public static void setBMSStatus(BMSState b) {
	        BMS_STATE = b;
	    }
	    
	    public void executeBMSModule()
	    {
	    	this.chargeBatteryMonitor.start();	    	
	    }
	    
	    public static void main(String... args) throws NumberFormatException, InterruptedException, ValueOutOfBoundException {
	        
	    	//Declaring BMS Object
	        final XMLParser bmsObject = new XMLParser();
	        try {
				XMLParser.reader();
			} catch (ValueOutOfBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        bmsObject.chargeBatteryMonitor=new BatteryMonitor(bmsObject.socBatteryReport);	        	        
	        bmsObject.executeBMSModule();

	}
	    	
	    }
