package org.example.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {
    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "user-notifications", groupId = "notification-group")
    public void listen(UserNotification userNotification) {
        String message;

        if ("CREATE".equals(userNotification.getOperation())) {
            message = "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
        } else if ("DELETE".equals(userNotification.getOperation())) {
            message = "Здравствуйте! Ваш аккаунт был удалён.";
        } else {
            return;
        }

        sendEmail(userNotification.getEmail(), message);
    }

    private void sendEmail(String to, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setText(body);
        mailSender.send(message);
    }
}
