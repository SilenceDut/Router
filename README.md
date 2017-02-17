##Latest version
router 1.2.8 ——  modified code to avoid concurrent bugs
router 1.2.6 --  use apt to process subscribe annotation 
router 1.2.0 --  use WeakReference to avoid MemoryLeak cause by forgot unregister

##What's Router

English | [中文文档](http:www.silencedut.com/2016/09/04/Router—一个高效，使用方便，基于动态代理实现的Android事件总线库)

**Router -- A substitute good of EventBus similar implemented by dynamic proxy** 

![dynamic proxy](http://ww1.sinaimg.cn/large/801b780agw1f7hxd0b7oyj20fr08vwfc.jpg)

> what's dynamic proxy and how to use , see my blog [代理模式的学习与应用](http://www.silencedut.com/2016/08/12/代理模式的学习与应用)

## Background
EventBus is good ,but there are some problems when use.

- **[EventBus](https://github.com/greenrobot/EventBus) is not friendly to IDE such as AS.so on registered method ,control+click(or other shortcut key) can't take 
you to what's actually being called , and there no any prompt when you post a event even you get a mistake.** 

- **when called post(Event event),EventBus use `Event` type to find the received method ,so you must define many class for 
different received methods to avoid invoked unexpected even they have same class type or you only want dispatch a base type 
such as a int value**

above all , when project gets larger or multiple developers work on project,which greatly increases the cost of developing and maintaining.

## Feature
- take even no time and memory when register , only add a object reference to a map.
- friendly to IDE, you can easily find the action from where it was called. and when you try to send a event there while be a prompt by ide.
- defined method to match event instead of many methods.
- both Router and MVP design model using implement interface as a way of communication ,so it fit perfectly in MVP .
- proven in practice by mature apps.

## Use
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

    //register on a appropriate lifecycle or constructor
    Router.getInstance().register(this); 
    
    //it will be invoked on UI Thread
    void anyMethod(AnyType object)  {
        //do what you want
    }
}
```

Step3. invoke the method you defined before

```java
Router.getInstance().getReceiver(AnyInterface.class).anyMethod(AnyType object);
```

Step4. unregister when not use anymore or a appropriate lifecycle
```java
Router.getInstance().unregister(this);
```

now a publish/subscribe is completed,simple as EventBus.

#### Method Invoked Thread

Use Annotation invoke method in different threads

```java
 @Subscribe(runThread = RunThread.MAIN)
 @Subscribe(runThread = RunThread.POSTING)
 @Subscribe(runThread = RunThread.ASYNC)
 @Subscribe(runThread = RunThread.BACKGROUND)
```

Just like EventBus's way to reduce learning cost.
if not add Annotation, will use @Subscribe(runThread = RunThread.Main)  as default.

#### Annotation Mode
#####mode 1 :add Annotation on implemented method, it as default
```java
@Subscribe(runThread = RunThread.BACKGROUND)
public void anyMethod(String msg) {
    ...
}
```

anyMethod will invoked on RunThread.BACKGROUND thread

#####mode 2 :add Annotation on interface's method
```java
Router.getInstance().setAnnotateMethodOnInterface(true);

interface TestRunThread {
     @Subscribe(runThread = RunThread.POSTING)
     void anyMethod(String posting) ;
}
```
on this mode, all class which implement the method will be invoked on the RunThread.POSTING thread.

more use details , see the simple.

## Add to project
**gradle**

    compile 'com.silencedut:router:1.2.6'
**maven**

    <dependency>
      <groupId>com.silencedut</groupId>
      <artifactId>router</artifactId>
      <version>1.2.6</version>
      <type>pom</type>
    </dependency>
    
License
-------

    Copyright 2015-2016 SilenceDut

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
