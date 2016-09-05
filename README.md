##What's Router
**Router -- A substitute good of EventBus similar implemented by dynamic proxy** 

![dynamic proxy](http://ww1.sinaimg.cn/large/801b780agw1f7hxd0b7oyj20fr08vwfc.jpg)

##Background
EventBus is good ,but there are some problems when use.

**1. too much reflection use when register,and need manage mach cache such as ,especially there are many methods 
in registered class, which adds to the memory requirements as well as initialization time.**

**2. [EventBus](https://github.com/greenrobot/EventBus) is not friendly to IDE such as AS.so on registered method ,control+click(or other shortcut key) can't take 
you to what's actually being called , and there no any prompt when you post a event even you get a mistake.** 

**3. when called post(Event event),EventBus use `Event` type to find the received method ,so you must define many class for 
different received methods to avoid invoked unexpected even they have same class type or you only want dispatch a base type 
such as a int value**

above all , when project gets larger or multiple developers work on project,which greatly increases the cost of developing and maintaining.

##Feature
- take even no time and memory when register , only add a object reference to a map.
- friendly to IDE, you can easily find the action from where it was called. and when you try to send a event there while be a prompt by ide.
- defined method to match event instead of many methods.
- proven in practice by mature apps

##Use
Router is easily use especially when you had used EventBus before.

Step1. define interface and method whatever you want

```java
interface AnyInterface {
    void anyMethod(AnyType posting) ;
}
```

Step2. register to Router where you need implement the interface

```java
class AnyClass implement AnyInterface {

    //register
    Router.getInstance()register(this); 
    
    void anyMethod(AnyType object)  {
        //do what you want
    }
}
```

Step3. invoke the method you defined before

```java
Router.getInstance().getReceiver(AnyInterface.class).anyMethod(AnyType object);
```

