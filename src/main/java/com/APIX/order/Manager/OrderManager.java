package com.APIX.order.Manager;
import com.APIX.ObserverPattern.Observer;
import com.APIX.ObserverPattern.Subject;
import com.APIX.order.model.Order;
import com.APIX.order.model.OrderState;
import com.APIX.order.model.SimpleOrder;
import com.APIX.payment.service.PaymentService;
import com.APIX.user.model.Language;
import com.APIX.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

public abstract class OrderManager implements Subject {
    protected List<Observer> notificationObservers = new ArrayList<>();
    PaymentService paymentService;

    public abstract boolean placeOrder(Order order);
    public abstract boolean cancel(Order order);
    public abstract boolean shipOrder(Order order);

    public void addObserver(Observer observer){
        notificationObservers.add(observer);
    };
    public void removeObserver(Observer observer){
        notificationObservers.remove(observer);
    };
    public void notifyObservers(Language language, Order order){
        for(Observer obs : notificationObservers){
            obs.update(language,order);
        }
    };
    void setNotificationObservers(List<Observer>observers){
        this.notificationObservers=observers;
    }

    public void changeOrderStatus(Order order, OrderState newState){
        order.setStatus(newState);
        notifyObservers(UserService.getUserById(order.getUserID()).getLanguage(), order);
    }







}
