package symantecrest;

import java.util.LinkedHashMap;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class ClearPayload extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		//System.out.println(message.getPayload().getClass().toString());
		// payload class java.util.LinkedHashMap
		Map<String , Object> payload  = new LinkedHashMap<>();
		payload = (Map<String , Object>)message.getPayload();
		payload.clear();
		return message;
	}

}
