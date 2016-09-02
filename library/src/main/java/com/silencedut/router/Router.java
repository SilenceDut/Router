package com.silencedut.router;

import com.silencedut.router.dispatcher.Dispatcher;
import com.silencedut.router.dispatcher.DispatcherFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by SilenceDut on 16/8/31.
 */

public class Router {

    private Map<Class<?>,ReceiverHandler> mReceiverHandlerByType = new ConcurrentHashMap<>();

    private Set<Object> mAllReceivers = new CopyOnWriteArraySet<>();

    private Set<Dispatcher> mDispatchers = new CopyOnWriteArraySet<>();

    boolean annotateMethodOnInterface;

    private Router() {}

    private static class InstanceHolder {
        private static Router sInstance = new Router();
    }

    public static Router getInstance() {
        return InstanceHolder.sInstance;
    }

    public <T> T getReceiver(Class<T> receiverType) {
        ReceiverHandler receiverHandler = mReceiverHandlerByType.get(receiverType);

        if(receiverHandler==null) {
            receiverHandler = new ReceiverHandler(this,receiverType,mAllReceivers);
            mReceiverHandlerByType.put(receiverType,receiverHandler);
        }

        return (T)receiverHandler.receiverProxy;
    }

    public void setAnnotateMethodOnInterface(boolean annotateMethodOnInterface) {
        this.annotateMethodOnInterface = annotateMethodOnInterface;
    }

    void dispatchEvent(Reception reception) {
        Dispatcher dispatcher = DispatcherFactory.getEventDispatch(reception.runThread);
        dispatcher.dispatch(reception.event);
        mDispatchers.add(dispatcher);
    }

    public void register(Object receiver) {
        mAllReceivers.add(receiver);
    }

    public void unregister(Object receiver) {
        mAllReceivers.remove(receiver);

        Iterator iterator = mReceiverHandlerByType.keySet().iterator();
        while (iterator.hasNext()) {
            Class type = (Class) iterator.next();
            if(type.isInstance(receiver)&&
                    mReceiverHandlerByType.get(type).getSameTypeReceivesCount()==0) {
                iterator.remove();
            }
        }
        if(mAllReceivers.size()==0) {
            closeCenter();
        }
    }

    private void closeCenter() {
        for(Dispatcher dispatcher : mDispatchers) {
            dispatcher.stop();
        }
    }
}
