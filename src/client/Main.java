package client;

import messaging_queue.public_interfaces.ISubscriber;
import messaging_queue.public_interfaces.Queue;

public class Main {

    public static void main(String[] args) throws Exception {
        ISubscriber subscriber = new TestSubscriberOne();
        ISubscriber subscriber2 = new TestSubscriberTwo();

        String topic1 = "topic1";
        String topic2 = "topic2";

        Queue queue = new Queue();
        queue.addNewTopic(topic1);
        queue.addNewTopic(topic2);

        queue.subscribe(subscriber, topic1);
        queue.subscribe(subscriber2, topic1);
        queue.subscribe(subscriber2, topic2);

        queue.publishMessage(topic1, "-1-1-1-1");
        queue.publishMessage(topic2, "0000");
        queue.publishMessage(topic2, "1111");
        queue.publishMessage(topic1, "2222");
        queue.publishMessage(topic2, "3333");
        queue.publishMessage(topic1, "4444");
        queue.publishMessage(topic2, "5555");
        queue.publishMessage(topic1, "6666");
        queue.publishMessage(topic2, "7777");

        Thread.sleep(15000);

        queue.publishMessage(topic1, "8888");
        queue.publishMessage(topic2, "9999");

        queue.resetTopicSubscriberOffset(subscriber2, topic2, 0);

    }

}
