package messaging_queue.handlers;

import messaging_queue.models.Topic;
import messaging_queue.models.TopicSubscriber;

public class SubscriberWorker implements Runnable {
    private final TopicSubscriber topicSubscriber;
    private final Topic topic;


    public SubscriberWorker(TopicSubscriber topicSubscriber, Topic topic) {
        this.topicSubscriber = topicSubscriber;
        this.topic = topic;
    }

    @Override
    public void run() {
        synchronized (topicSubscriber) {
            do {
                while(topicSubscriber.getOffset() < topic.getMessageCount()) {
                    topicSubscriber.getSubscriber().consume(topic.getMessage(topicSubscriber.getOffset()));
                    topicSubscriber.incrementOffset();
                }
                try {
                    topicSubscriber.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    public void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }

}
