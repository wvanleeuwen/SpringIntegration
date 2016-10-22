package nl.luminis.rotterdam.springintegration.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

@Service
public class SlackContentService {
	
	/**
     * This method shows how to send a message to a given channel (public channel, private group or direct message channel)
     */
    public void sendMessageToAChannel(String subject)
    {
    	// springintegration-bot // xoxb-93052495079-GAIRWeYvDctswznOEZ6kckQL
    	SlackSession session = SlackSessionFactory.createWebSocketSlackSession("<token>");
    	try {
			session.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //get a channel
        SlackChannel channel = session.findChannelByName("integratiefeed");
    	String integrationToolName = "<Spring Integration> :";
    	String text = integrationToolName.concat(subject);
        session.sendMessage(channel, text, null);
    }
}
