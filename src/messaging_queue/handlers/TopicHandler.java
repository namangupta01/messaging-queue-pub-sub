package messaging_queue.handlers;

import messaging_queue.models.Message;
import messaging_queue.models.Topic;
import messaging_queue.models.TopicSubscriber;
import messaging_queue.public_interfaces.ISubscriber;

import java.util.ArrayList;
import java.util.HashMap;

public class TopicHandler {
    private final Topic topic;
    private final HashMap<TopicSubscriber, SubscriberWorker> subscriberSubscriberWorkerHashMap;

    public TopicHandler(Topic topic) {
        this.topic = topic;
        this.subscriberSubscriberWorkerHashMap = new HashMap<>();
    }

    public void publish() {
        ArrayList<TopicSubscriber> subscribers = topic.getSubscribers();
        for(TopicSubscriber topicSubscriber: subscribers) {
            new Thread(() -> startSubscriberWorker(topicSubscriber.getSubscriber())).start();
        }
    }

    public void startSubscriberWorker(ISubscriber subscriber) {
        TopicSubscriber tSubscriber = null;
        synchronized (subscriber) {
            for(TopicSubscriber topicSubscriber : topic.getSubscribers()) {
                if(topicSubscriber.getSubscriber().equals(subscriber)) {
                    tSubscriber = topicSubscriber;
                    if(!subscriberSubscriberWorkerHashMap.containsKey(topicSubscriber)) {
                        SubscriberWorker subscriberWorker = new SubscriberWorker(topicSubscriber, topic);
                        subscriberSubscriberWorkerHashMap.put(topicSubscriber, new SubscriberWorker(topicSubscriber, topic));
                        new Thread(subscriberWorker).start();
                    }
                }
            }
        }
        if(tSubscriber == null) return;
        final SubscriberWorker subscriberWorker = subscriberSubscriberWorkerHashMap.get(tSubscriber);
        subscriberWorker.wakeUpIfNeeded();
    }

}
