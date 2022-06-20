package messaging_queue.public_interfaces;

import messaging_queue.models.Message;

public interface ISubscriber {
    public String getId();
    public void consume(Message message);
}
