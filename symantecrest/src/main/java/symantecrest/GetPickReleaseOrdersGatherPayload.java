package symantecrest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class GetPickReleaseOrdersGatherPayload extends AbstractMessageTransformer {
	@Override
	  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		  //System.out.println("Scatter 2: " + message.getPayload().getClass());
        CopyOnWriteArrayList<Map<String, Object>> myPayloadArrayList = new CopyOnWriteArrayList<>();
        myPayloadArrayList = (CopyOnWriteArrayList<Map<String, Object>>)message.getPayload();
        Map<String, Object> myOrders = (LinkedHashMap<String, Object>)myPayloadArrayList.get(0);

        try {
      	  message.setPayload(myOrders);
        } catch (Exception e) {
      	  
        }
		  return message;  
	  }
}
