package nl.luminis.rotterdam.springintegration.adapter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

import nl.luminis.rotterdam.springintegration.service.MailService;

@Service
public class SlackReceiverChannelAdapter {

	@Autowired
	private MailService mailService;
	
    /**
     * This method shows how to register a listener on a SlackSession
     */
    public void registeringAListener()
    {
    	// springintegration-bot // xoxb-93052495079-GAIRWeYvDctswznOEZ6kckQL
    	SlackSession session = SlackSessionFactory.createWebSocketSlackSession("xoxb-93052495079-GAIRWeYvDctswznOEZ6kckQL");
        // first define the listener
        SlackMessagePostedListener messagePostedListener = new SlackMessagePostedListener()
        {
			private String to = "<mailTo@mailToProvider.eu>";
			private String subject = "SpringIntegration-bot saw you get mentioned in a slack message on the 'integrationfeed' channel";

			@Override
            public void onEvent(SlackMessagePosted event, SlackSession session)
            {
				System.out.println("got a slack event");
                SlackUser messageSender = event.getSender();
                subject = subject.concat(" by '"+messageSender.getUserName()+"'");
                String messageContent = event.getMessageContent();
                System.out.println("messageContent: "+messageContent);
                if(messageContent.contains("<your user name on slack>")){
                	System.out.println("telling mailService to send a mail...");
                	mailService.sendMail(to, subject, messageContent);
                }
            }
        };
        //add it to the session
        session.addMessagePostedListener(messagePostedListener);
        try {
			session.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
}
