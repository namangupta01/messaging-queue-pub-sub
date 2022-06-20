package client;

import messaging_queue.models.Message;
import messaging_queue.public_interfaces.Subscriber;

public class TestSubscriberTwo extends Subscriber {

    @Override
    public void consume(Message message) {
        System.out.println(Thread.currentThread().getName());
        System.out.println("In subscriber 2 " + message.getBody());
    }

}
