package symantecrest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;

import java.util.UUID;

public class GenerateUUIDForCassandraDBInsert extends AbstractMessageTransformer {
	  @Override
	  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		  // Can now replace this transformer with Message Properties and Transform Message elements
		  
		  //Payload type: class java.util.LinkedHashMap
	      Map<String , Object> payload  = new LinkedHashMap<>();
	      
	      // Replace request_id with one generated at start of flow
	      //UUID myUUID = UUID.randomUUID();

	      //message.getProperty("shipmentResponse", PropertyScope.SESSION)
	      
	      payload.put("request_id", message.getProperty("request_id", PropertyScope.SESSION));
	      payload.put("query_params", message.getProperty("query_params", PropertyScope.SESSION));
	      Map<String , Object> queryParams  = new LinkedHashMap<>();
	      queryParams = (Map<String , Object>)message.getInboundProperty("http.query.params");
	      payload.put("request_source", queryParams.get("SOURCE"));
	      
	      
	      // Replace timestamp with one generated at start of flow
	      //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	  	  //Date date = new Date();
	  	  //String timeStampString = dateFormat.format(date).toString();
	  	  payload.put("timestamp",  message.getProperty("timestamp", PropertyScope.SESSION));
	  	  
	  	  try {
	    	  message.setPayload(payload);
	    	  //message.setInvocationProperty("request_id", message.getOutboundProperty("request_id"));
	    	  //message.setInvocationProperty("query_params", message.getInboundProperty("http.query.string"));
	    	  //message.setInvocationProperty("request_source", queryParams.get("SOURCE"));
	    	  //message.setInvocationProperty("timestamp", message.getOutboundProperty("timestamp"));
	    	  
	      } catch (Exception e) {
	    	  System.out.println("setPayload Exception: " + e.toString());
	      }
	      
	      return message;
	  }
}
