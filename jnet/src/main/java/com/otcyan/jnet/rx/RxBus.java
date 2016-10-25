package com.otcyan.jnet.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * @author snamon
 * 事件总线类似enentBus.
 */
public class RxBus<T> {

    private static volatile RxBus sRxBus;
    public Subject<T,T> mSubject ;  //这是一个观察者又是一个被观察者 即充当了Observer和Observable的角色

    private RxBus(){
        mSubject = new SerializedSubject<>(PublishSubject.<T>create()) ;
    }
    public static RxBus get(){
        if(sRxBus == null){
            synchronized (RxBus.class){ //多线程并发
                if(sRxBus == null){ //双重检测
                    sRxBus = new RxBus() ;
                }
            }
        }
        return sRxBus;
    }

    /**
     * 发送数据
     * @param t 发送事件的类型
     */
    public void  post( T t){
        mSubject.onNext(t);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     * @param eventType 注册事件的类型
     */
    public  Observable<T> regist(Class<T> eventType){
        return mSubject.ofType(eventType) ;
    }

}
