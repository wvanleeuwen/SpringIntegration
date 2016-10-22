package nl.luminis.rotterdam.springintegration.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.luminis.rotterdam.springintegration.service.SlackContentService;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterReceiverChannelAdapter {

	@Autowired
	private SlackContentService slackService;

	public void registeringAListener() {

		// Configuration for twitter4j
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("<OAuthConsumerKey>")
				.setOAuthConsumerSecret("<OAuthConsumerSecret>")
				.setOAuthAccessToken("<OAuthAccessToken>")
				.setOAuthAccessTokenSecret("<OAuthAccessTokenSecret>");

		TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());

		TwitterStream twitterStream = tf.getInstance();
		StatusListener listener = new StatusListener() {

			public void onStatus(Status status) {
				System.out.println("got a status change event from twitter");
				String subject = "<Spring Integration> : detected new tweet from " + status.getUser().getName() + " "
						+ status.getText();
				slackService.sendMessageToAChannel(subject);
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// do nothing
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// do nothing
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				// do nothing
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				// do nothing
			}
		};

		FilterQuery fq = new FilterQuery();
		String keywords[] = { "Luminis Rotterdam", "Luminis" };

		fq.track(keywords);

		twitterStream.addListener(listener);
		twitterStream.filter(fq);
	}
}
