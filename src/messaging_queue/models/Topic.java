package messaging_queue.models;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private final String id;
    private final String name;
    private final ArrayList<TopicSubscriber> subscribers;
    private final ArrayList<Message> messages;

    public Topic(String id, String name) {
        this.id = id;
        this.name = name;
        this.subscribers = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<TopicSubscriber> getSubscribers() {
        return new ArrayList<>(this.subscribers);
    }

    public void addSubscriber(TopicSubscriber topicSubscriber) {
        this.subscribers.add(topicSubscriber);
    }

    public synchronized void addMessage(Message message) {
        this.messages.add(message);
    }

    public Message getMessage(Integer offset) {
        return messages.get(offset);
    }

    public Integer getMessageCount() {
        return messages.size();
    }
}
