package ro.upt.ac.pcbe.sys;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ro.upt.ac.pcbe.news.Topic;
import ro.upt.ac.pcbe.subscribers.Registerable;

public class NewsSys {
    private final EventBus mNewsDispatcher;
    private NewsFakeDB mNewsDB;

    public int getNoOfSubscribers() {
        return noOfSubscribers;
    }

    private int noOfSubscribers;

    public NewsSys() {
        this.mNewsDB = new NewsFakeDB();
        this.mNewsDispatcher = new EventBus("News Dispatcher");
    }

    public void newTopic(Topic topic) {
        mNewsDB.addTopic(topic);
        mNewsDispatcher.post(topic);
    }

    public void updateTopic(int id, String content, String editor) {
        if (editor == null) {
            mNewsDB.updateTopic(id, content);
        } else {
            mNewsDB.updateTopic(id, content, editor);
        }
        mNewsDispatcher.post(mNewsDB.findTopicByID(id));
    }

    public void delete(int id) {
        mNewsDB.deleteTopic(mNewsDB.findTopicByID(id));
        mNewsDispatcher.post(mNewsDB.findTopicByID(id));
    }

    public void registerSubscriber(Registerable sub) {
        mNewsDispatcher.register(sub);
        noOfSubscribers++;
    }

    public void unregisterSubscriber(Registerable registerable) {
        mNewsDispatcher.unregister(registerable);
        noOfSubscribers--;
    }
}
