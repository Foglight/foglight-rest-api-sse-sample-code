package sse.sse;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

/**
 * SSE Client Example!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	String accessTokenString = <<Access_Token>>;
    	String urlString = "http://localhost:8080/api/v1/subscription/alarm/subscribe?Accept=application/json&Access-Token="+accessTokenString;
    	Client client = ClientBuilder.newBuilder().readTimeout(1, TimeUnit.HOURS).build();
    	
    	WebTarget target = client.target(urlString);
    	
		SseEventSource source = SseEventSource.target(target).build();
	    source.register((inboundSseEvent) -> {
	    	System.err.println(System.currentTimeMillis()+"===="+inboundSseEvent.readData());
	    }, (exception) -> {
	    	System.err.println("Exception occure "+ exception.getMessage());
	    }, new Runnable() {
			
			@Override
			public void run() {
				System.err.println("Source complete.");
				
			}
		});
	    
	    source.open();
    }
}
