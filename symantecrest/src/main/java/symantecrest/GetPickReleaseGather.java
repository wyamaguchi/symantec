package symantecrest;

import java.util.LinkedHashMap;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class GetPickReleaseGather extends AbstractMessageTransformer {
	  @Override
	  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		  Map<String , Object> payload  = new LinkedHashMap<>();
		  payload = (Map<String , Object>)message.getPayload();
		  payload.remove("request_id");
		  payload.remove("query_params");
		  payload.remove("request_source");

		  return message;
	  }
}
