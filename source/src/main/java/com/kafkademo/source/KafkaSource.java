package com.kafkademo.source;

import com.kafkademo.source.data.RawUser;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@EnableBinding(Source.class)
public class KafkaSource {

    private Source source;

    public void emitRawUser(RawUser user) {
        source.output().send(MessageBuilder.withPayload(user).build());
    }

}
