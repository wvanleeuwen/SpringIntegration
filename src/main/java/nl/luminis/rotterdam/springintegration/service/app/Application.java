package nl.luminis.rotterdam.springintegration.service.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import nl.luminis.rotterdam.springintegration.adapter.SlackReceiverChannelAdapter;
import nl.luminis.rotterdam.springintegration.adapter.TwitterReceiverChannelAdapter;

/**
 * Main entry-point into the test application
 */
public class Application {

    private static ClassPathXmlApplicationContext applicationContext;

	public static void main( String[] args )
    {
        applicationContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
        
        // activating twitter listener
        TwitterReceiverChannelAdapter twitterAdapter = applicationContext.getBean( "twitterReceiverChannelAdapter", TwitterReceiverChannelAdapter.class );
        twitterAdapter.registeringAListener();
        
        // activating slack listener
        SlackReceiverChannelAdapter slackAdapter = applicationContext.getBean( "slackReceiverChannelAdapter", SlackReceiverChannelAdapter.class );
        slackAdapter.registeringAListener();
    }

}
