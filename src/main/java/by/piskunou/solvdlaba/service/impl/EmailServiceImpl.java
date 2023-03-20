package by.piskunou.solvdlaba.service.impl;

import by.piskunou.solvdlaba.domain.event.SendEmailEvent;
import by.piskunou.solvdlaba.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final KafkaSender<String, SendEmailEvent> sender;

    @Override
    public Mono<Void> sendMessage(SendEmailEvent sendEmailEvent) {
        sender.send(
                Mono.just(
                        SenderRecord.create(
                                "sendEmail",
                                0,
                                System.currentTimeMillis(),
                                sendEmailEvent.getUuid().toString(),
                                sendEmailEvent,
                                null
                        )))
                .subscribe();
        return Mono.empty();
    }

}
