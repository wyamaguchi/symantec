package symantecrest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class GetPickReleaseOrdersFormatter extends AbstractMessageTransformer {
	  @Override
	  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		  //Payload type: class java.util.LinkedHashMap
	      Map<String , Object> payload  = new LinkedHashMap<>();
	      payload = (LinkedHashMap<String, Object>)message.getPayload();
	      
	      // First, remove repeated orders
	      ArrayList<Map<String, Object>> pickReleaseOrders = (ArrayList<Map<String, Object>>)payload.get("ORDERS");
	      // use set to dump dupes
	      Set<Map<String, Object>> prSet = new HashSet<>();
	      prSet.addAll(pickReleaseOrders);
	      pickReleaseOrders.clear();
	      pickReleaseOrders.addAll(prSet);
	      // All items in pickReleaseOrders now unique
	      
	      // Second, assemble order lines
	      ArrayList<Map<String, Object>> orderLines = (ArrayList<Map<String, Object>>)payload.get("LINES");
	      ArrayList<Map<String, Object>> matchingOrderLines;
	      int orderId, orderNumber = 0;
	      
	      // O(ij) >> O(n^2) :(
	      for(int i=0; i<pickReleaseOrders.size(); i++) {
	    	  //can't just matchingOrderLines.clear(), since it's a reference
	    	  matchingOrderLines = new ArrayList<>();
	    	  orderId = (Integer)pickReleaseOrders.get(i).get("ORDER_ID");
	    	  for(int j=0; j<orderLines.size(); j++) {
	    		  orderNumber = (Integer)orderLines.get(j).get("ORDER_NUMBER");
	    		  if(orderId == orderNumber) {
	    			  // Matching id found, add po_line to matching list
	    			  matchingOrderLines.add(orderLines.get(j));
	    		  }
	    	  }
	    	  // Add matching po_line list to po
	    	  pickReleaseOrders.get(i).put("LINES", matchingOrderLines);
	      }
	      payload.remove("LINES");
	      return message;
	  }
}
