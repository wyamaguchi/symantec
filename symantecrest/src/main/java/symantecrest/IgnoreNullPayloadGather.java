package symantecrest;

import java.util.List;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.context.MuleContextAware;
import org.mule.api.routing.AggregationContext;
import org.mule.api.routing.RouterResultsHandler;
import org.mule.routing.AggregationStrategy;
import org.mule.routing.DefaultRouterResultsHandler;

public class IgnoreNullPayloadGather implements AggregationStrategy, MuleContextAware { 
	private RouterResultsHandler resultsHandler = new DefaultRouterResultsHandler();
	private MuleContext muleContext;
	
	@Override
	public MuleEvent aggregate(AggregationContext context) throws MuleException {
		List<MuleEvent> eventsWithoutExceptions = context.collectEventsWithoutExceptions();		
		return resultsHandler.aggregateResults(eventsWithoutExceptions,	context.getOriginalEvent(), muleContext);
	}

	@Override
	public void setMuleContext(MuleContext context) {
		this.muleContext = context;
	}
    
}