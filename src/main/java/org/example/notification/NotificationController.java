package org.example.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody UserNotification userNotification) {
        userNotification.sendEmail(userNotification.getEmail(),"Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
        return ResponseEntity.ok("Notification sent successfully.");
    }
}
