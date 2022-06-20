package messaging_queue.public_interfaces;

import messaging_queue.handlers.TopicHandler;
import messaging_queue.models.Message;
import messaging_queue.models.Topic;
import messaging_queue.models.TopicSubscriber;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

// Add a topic to a Queue
// Add a subscriber to a topic
// Publish a message to a topic
// Reset offset for a particular subscriber
public class Queue {

    private final HashMap<String, Topic> topicNameToTopicMap;
    private final HashMap<Topic, TopicHandler> topicTopicHandlerHashMap;

    public Queue() {
        this.topicNameToTopicMap = new HashMap<>();
        this.topicTopicHandlerHashMap = new HashMap<>();
    }

    public Topic addNewTopic(String topicName) throws Exception {
        if(topicNameToTopicMap.containsKey(topicName)) throw new Exception("Topic with same name already Exists");
        Topic topic = new Topic(UUID.randomUUID().toString(), topicName);
        topicNameToTopicMap.put(topicName, topic);
        topicTopicHandlerHashMap.put(topic, new TopicHandler(topic));
        return topic;
    }

    public void subscribe(ISubscriber subscriber, String topicName) throws Exception {
        TopicSubscriber topicSubscriber = new TopicSubscriber(subscriber);
        Topic topic = getTopic(topicName);
        topic.addSubscriber(topicSubscriber);
    }

    public void publishMessage(String topicName, String messageBody) throws Exception {
        Topic topic = getTopic(topicName);
        Message message = new Message(UUID.randomUUID().toString(), messageBody);
        topic.addMessage(message);
        new Thread(() -> getTopicHandler(topic).publish()).start();
    }

    public void resetTopicSubscriberOffset(ISubscriber subscriber, String topicName, Integer newOffset) throws Exception {
        Topic topic = getTopic(topicName);
        for (TopicSubscriber topicSubscriber: topic.getSubscribers()) {
            if(topicSubscriber.getSubscriber().equals(subscriber)) {
                topicSubscriber.setOffset(newOffset);
            }
        }
    }

    private TopicHandler getTopicHandler(Topic topic) {
        return topicTopicHandlerHashMap.get(topic);
    }

    private Topic getTopic(String topicName) throws Exception {
        if(topicNameToTopicMap.containsKey(topicName)) return topicNameToTopicMap.get(topicName);
        throw new Exception("Topic with this name doesn't exists");
    }

    private Topic findTopic(String topicName) {
        if(topicNameToTopicMap.containsKey(topicName)) return topicNameToTopicMap.get(topicName);
        return null;
    }
}



