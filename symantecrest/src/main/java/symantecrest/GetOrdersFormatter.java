package symantecrest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

class GetOrdersFormatter extends AbstractMessageTransformer {
	  @Override
	  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		Map<String , Object> dbResult  = new LinkedHashMap<>();
		dbResult = (LinkedHashMap<String, Object>)message.getPayload();
		ArrayList<Map<String, Object>> orders = (ArrayList<Map<String, Object>>)dbResult.get("ORDERS");
		
		
		//System.out.println("dbResult dump: " + dbResult.toString());
		//System.out.println("orders dump: " + orders.toString());
		//ArrayList - System.out.println("lines type: " + orders.get(0).get("LINES").getClass().toString());
		ArrayList<Map<String, String>> orderLines = new ArrayList<Map<String, String>>(); 
		int currentOrderId;
		int baseOrderId = -1;
		int baseIndex = -1;
		
		// DEBUG
		System.out.println("Orders(0) Lines (ArrayList): " + orders.get(0).get("LINES").toString());
		//System.out.println("Orders(0) Lines(0) (Map): " + orders.get(0).get("LINES").get(0).toString());
		ArrayList<Map<String, String>>debugList = (ArrayList<Map<String, String>>)orders.get(0).get("LINES");
		System.out.println("debugList: " + debugList.toString());
		System.out.println("debugList(0) (Map): " + debugList.get(0).toString());
		
		
		ArrayList<Map<String, String>> orderLinesList;// = (ArrayList<Map<String, String>>)orders.get(0).get("LINES");
		ArrayList<Integer> deleteIndexes = new ArrayList<>();
		// put orderlines in correct nested data type
		for(int i=0; i<orders.size(); i++) {
			currentOrderId = (int)orders.get(i).get("ORDER_ID");
			orderLinesList = (ArrayList<Map<String, String>>)orders.get(i).get("LINES");
			orderLines.add(orderLinesList.get(0));
			//DEBUG
			System.out.println("orderLines dump in for loop " + i + ": " + orderLines.toString());
			if (currentOrderId != baseOrderId) {
				baseOrderId = currentOrderId;
				baseIndex = i;
			} else {
				deleteIndexes.add(i);
				//orderLinesList = (ArrayList<Map<String, String>>)orders.get(i).get("LINES");
				//orderLines.add(orderLinesList.get(0));
				
				//DEBUG
				System.out.println("orders dump before put in for loop " + i + ": " + orders.toString());
				orders.get(baseIndex).put("LINES", orderLines);
				//DEBUG - correct here
				System.out.println("orders dump before put in for loop " + i + ": " + orders.toString());
				// This messes up the list? Don't need?
				//orderLines.clear();
			}
				
		}
		for(int i=0; i<deleteIndexes.size(); i++) {
			orders.remove(deleteIndexes.get(i) - i);
		
		}
		//DEBUG
		System.out.println("orders dump after for loop: " + orders.toString());
		
		
		Map<String , Object> returnPayload  = new LinkedHashMap<>();
		returnPayload.put("ORDERS", orders);
		returnPayload.put("IS_LAST_PAGE", "Y");
		// DEBUG
	    System.out.println("dbResult: " + dbResult.toString());
	    System.out.println("returnPayload: " + returnPayload.toString());
	    
		try {
		  message.setPayload(returnPayload);     
	      // DEBUG: print out payload
		  //System.out.println("message.getPayloadAsString() : " + message.getPayloadAsString());
		} catch (Exception e){
		  System.out.println("Exception: " + e.toString());
		}
		
	    return message;
	  }
	}