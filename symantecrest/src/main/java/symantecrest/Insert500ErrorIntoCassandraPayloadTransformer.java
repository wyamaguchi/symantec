package symantecrest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class Insert500ErrorIntoCassandraPayloadTransformer extends AbstractMessageTransformer {
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		/*
		Map<String, Object> newPayload = new LinkedHashMap<String, Object>();
		newPayload.put("http_status_code", 500);
		//newPayload.put("exception_class", message.exception.getCauseException().toString());
		try {
			message.setPayload(newPayload);
		} catch (Exception e) {
			System.out.println("Exception in setting 500 error payload for CassandraDB: " + e.toString());
		}
		*/
		return message;  
	}
}