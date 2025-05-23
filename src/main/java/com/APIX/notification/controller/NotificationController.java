package com.APIX.notification.controller;


import com.APIX.notification.model.Notification;
import com.APIX.notification.service.NotificationService;
import com.APIX.notification.service.NotificationStat;
import com.APIX.notification.service.StatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/notifications")
@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    NotificationStat notificationStat;

    @PostMapping
    public ResponseEntity<String> addNotification(@RequestBody Notification notification) {
        boolean added = NotificationService.addNotification(notification);
        if(added){
            return ResponseEntity.status(HttpStatus.CREATED).body("Notification has been added successfully");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, could not add notification");
        }
    }


    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications() {
        List<Notification> notifications = NotificationService.getNotifications();
        return ResponseEntity.ok(notifications);
    }
    @GetMapping(path = "stat")
    public ResponseEntity<StatDTO> calcStat() {
        return ResponseEntity.ok(notificationStat.getMostNotified());
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable("id") Long id) {
        Notification product = NotificationService.getNotification(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") Long id) {
        boolean deleted = NotificationService.deleteNotification(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Notification has been deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Notification ID");
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<String> updateNotification(@PathVariable("id") Long id, @RequestBody Notification notification) {
        if(NotificationService.getNotification(id) == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid notification ID");
        notification.setId(id);
        if (NotificationService.updateNotification(notification)) {
            return ResponseEntity.status(HttpStatus.OK).body("Notification has been updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Notification");
        }
    }

}
