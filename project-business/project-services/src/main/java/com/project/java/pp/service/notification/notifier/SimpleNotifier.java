package com.project.java.pp.service.notification.notifier;

import com.project.java.pp.service.notification.EmailGenerator;
import com.project.java.pp.service.notification.Notifier;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "rabbitmq", name = "use-queue", havingValue = "false")
public class SimpleNotifier implements Notifier {
    private final EmailGenerator emailGenerator;
    private final JavaMailSender mailSender;

    @SneakyThrows
    @Override
    public void sendMessage(String email, String memberName) {
        mailSender.send(emailGenerator.generateEmail(email, memberName));
        log.info("Notification sent to {}", email);
    }
}