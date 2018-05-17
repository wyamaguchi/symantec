package symantecrest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class GetPurchaseOrdersFormatter extends AbstractMessageTransformer {
	  @Override
	  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		  //Payload type: class java.util.LinkedHashMap
	      Map<String , Object> payload  = new LinkedHashMap<>();
	      payload = (LinkedHashMap<String, Object>)message.getPayload();
	      
	      // First, remove repeated purchase_orders
	      ArrayList<Map<String, Object>> purchaseOrders = (ArrayList<Map<String, Object>>)payload.get("PURCHASE_ORDERS");
	      // use set to dump dupes
	      Set<Map<String, Object>> poSet = new HashSet<>();
	      poSet.addAll(purchaseOrders);
	      purchaseOrders.clear();
	      purchaseOrders.addAll(poSet);
	      // All items in purchaseOrders now unique
	      
	      // Second, assemble purchase_order_lines
	      ArrayList<Map<String, Object>> poLines = (ArrayList<Map<String, Object>>)payload.get("PO_LINES");
	      ArrayList<Map<String, Object>> matchingPoLines;
	      int poHeaderId, poId = 0;
	      
	      // O(ij) >> O(n^2) :(
	      for(int i=0; i<purchaseOrders.size(); i++) {
	    	  //can't just matchingPoLines.clear(), since it's a reference
	    	  matchingPoLines = new ArrayList<>();
	    	  poHeaderId = (Integer)purchaseOrders.get(i).get("PO_HEADER_ID");
	    	  for(int j=0; j<poLines.size(); j++) {
	    		  poId = (Integer)poLines.get(j).get("PO_ID");
	    		  if(poHeaderId == poId) {
	    			  // Matching id found, add po_line to matching list
	    			  matchingPoLines.add(poLines.get(j));
	    		  }
	    	  }
	    	  // Add matching po_line list to po
	    	  purchaseOrders.get(i).put("PO_LINES", matchingPoLines);
	      }
	      payload.remove("PO_LINES");
	      return message;
	  }
}
