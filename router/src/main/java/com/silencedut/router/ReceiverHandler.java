package com.silencedut.router;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by SilenceDut on 16/8/1.
 */

class ReceiverHandler implements InvocationHandler {

    private Router mRouter;
    private Class mReceiverType;
    private Set<WeakReference<Object>> mReceivers= new CopyOnWriteArraySet<>();
    private AtomicInteger sameTypeReceivesCount = new AtomicInteger(0);
    Object mReceiverProxy;

    ReceiverHandler(Router router, Class receiverType,Set<WeakReference<Object>> mReceivers) {
        this.mRouter = router;
        this.mReceiverType = receiverType;
        this.mReceivers = mReceivers;
        this.mReceiverProxy = Proxy.newProxyInstance(mReceiverType.getClassLoader(), new Class[] {mReceiverType}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        for(WeakReference weakReference: mReceivers) {
            Object receiver = weakReference.get();
            if(mReceiverType.isInstance(receiver)) {
                if(!mRouter.mAnnotateMethodOnInterface) {
                    try {
                        method = receiver.getClass().getMethod(method.getName(), method.getParameterTypes());
                    } catch (NoSuchMethodException e) {
                        throw new RouterException(String.format("%s no method %s",receiver.getClass().getName(),method.getName()) ,e);
                    }
                }
                Reception reception = new Reception(receiver,method,args);
                reception.dispatchEvent();
                mRouter.addDispatch(reception.mDispatcher);
            }
        }
        return null;
    }

    int getSameTypeReceivesCount() {

        sameTypeReceivesCount.set(0);

        for(WeakReference weakReference : mReceivers) {
            Object receiver = weakReference.get();
            if(mReceiverType.isInstance(receiver)) {
                sameTypeReceivesCount.incrementAndGet();
            }
        }
        return sameTypeReceivesCount.get();
    }

}
