package com.silencedut.router;

import com.silencedut.router.dispatcher.Dispatcher;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by SilenceDut on 16/8/1.
 */

public class Router {

    private Map<Class<?>, ReceiverHandler> mReceiverHandlerByInterface = new ConcurrentHashMap<>();

    private Set<WeakReference<Object>> mReceivers = new CopyOnWriteArraySet<>();

    private Set<Dispatcher> mDispatchers = new HashSet<>();

    boolean mAnnotateMethodOnInterface;

    private Router() {
    }

    private static class InstanceHolder {
        private static Router sInstance = new Router();
    }

    /**
     * use  {@link #instance()} method
     * @return
     */
    @Deprecated
    public static Router getInstance() {
        return InstanceHolder.sInstance;
    }

    public static Router instance() {
        return InstanceHolder.sInstance;
    }

    public <T> T getReceiver(Class<T> interfaceType) {
        ReceiverHandler receiverHandler = mReceiverHandlerByInterface.get(interfaceType);

        if (!interfaceType.isInterface()) {
            throw new RouterException(String.format("receiverType must be a interface , " + "%s is not a interface", interfaceType.getName()));
        }

        if (receiverHandler == null) {
            receiverHandler = new ReceiverHandler(this, interfaceType, mReceivers);
            mReceiverHandlerByInterface.put(interfaceType, receiverHandler);
        }

        return (T) receiverHandler.mReceiverProxy;
    }

    public void setmAnnotateMethodOnInterface(boolean mAnnotateMethodOnInterface) {
        this.mAnnotateMethodOnInterface = mAnnotateMethodOnInterface;
    }

    void addDispatch(Dispatcher dispatcher) {
        mDispatchers.add(dispatcher);
    }

    public synchronized void register(Object receiver) {
        if (receiver == null) {
            return;
        }
        mReceivers.add(new WeakReference<>(receiver));
    }

    public synchronized void unregister(Object receiver) {

        if (receiver == null) {
            return;
        }

        for (Object mReceiver : mReceivers) {
            WeakReference weakReference = (WeakReference) mReceiver;
            Object o = weakReference.get();
            if (receiver.equals(o) || o == null) {
                mReceivers.remove(mReceiver);
            }
        }

        Iterator iterator = mReceiverHandlerByInterface.keySet().iterator();
        while (iterator.hasNext()) {
            Class type = (Class) iterator.next();
            if (type.isInstance(receiver) && mReceiverHandlerByInterface.get(type).getSameTypeReceivesCount() == 0) {
                iterator.remove();
            }
        }

        if (mReceivers.size() == 0) {
            stopRouter();
        }
    }

    private void stopRouter() {
        for (Dispatcher dispatcher : mDispatchers) {
            dispatcher.stop();
        }
    }
}
