package com.kafkademo.source;

import com.kafkademo.source.data.RawUser;
import com.kafkademo.source.data.RawUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableBinding(Source.class)
public class KafkaSource {
    private static final String[] names = "Willie Stroker, Ben Dover, Dixie Normous, Jack Knauf, Ivanna Humpalot".split(", ");

    @Autowired
    private Source source;

    @Autowired
    private RawUserRepository rawUserRepository;

    @GetMapping(value = "/send")
    public ResponseEntity<Void> send() throws  Exception {
        generateUsers().stream().forEach(this::emitRawUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/raw-users")
    public ResponseEntity<List<RawUser>> getRawUsers() {
        List<RawUser> rawUsers = rawUserRepository.findAll();
        return new ResponseEntity<List<RawUser>>(rawUsers, HttpStatus.OK);
    }

    private void emitRawUser(RawUser rawUser) {
        source.output().send(MessageBuilder.withPayload(rawUser).build());
    }

    private List<RawUser> generateUsers() {
        List<RawUser> rawUsers = new ArrayList<>();
        for (String name: names) {
            RawUser rawUser = new RawUser();
            rawUser.setFullName(name);
            rawUser.setEmail(name.split(" ")[0].toLowerCase() + "@gmail.com");
            rawUsers.add(rawUserRepository.save(rawUser));
        }
        return rawUsers;
    }

}
