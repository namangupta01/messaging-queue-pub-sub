package messaging_queue.models;

import messaging_queue.public_interfaces.ISubscriber;

public class TopicSubscriber {
    private final ISubscriber subscriber;
    private Integer offset;

    public TopicSubscriber(ISubscriber subscriber) {
        this.subscriber = subscriber;
        this.offset = 0;
    }

    public ISubscriber getSubscriber() {
        return subscriber;
    }

    public Integer getOffset() {
        return offset;
    }

    public synchronized void setOffset(Integer offset) {
        this.offset = offset;
    }

    public synchronized void incrementOffset() {
        this.offset++;
    }
}
