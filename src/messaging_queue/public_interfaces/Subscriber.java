package messaging_queue.public_interfaces;

import java.util.UUID;

public abstract class Subscriber implements ISubscriber {
    private final String id;

    protected Subscriber() {
        System.out.println("Parent constructor called");
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }


}
