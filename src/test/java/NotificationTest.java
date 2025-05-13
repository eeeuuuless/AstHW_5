import org.example.notification.NotificationService;
import org.example.notification.UserNotification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class NotificationTest {
    @Autowired
    private NotificationService notificationService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    public void testSend() {
        UserNotification userNotification = new UserNotification();
        userNotification.setEmail("test@test.com");
        userNotification.setOperation("CREATE");

        notificationService.listen(userNotification);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
