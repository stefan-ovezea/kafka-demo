package com.kafkademo.sink;

import com.kafkademo.sink.data.RawUser;
import com.kafkademo.sink.data.User;
import com.kafkademo.sink.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableBinding(Sink.class)
public class KafkaSink {

    @Autowired
    private UserRepository userRepository;

    @StreamListener(target = Sink.INPUT)
    public void getUsers(@Payload RawUser rawUser) {
        User user = userRepository.save(mapToUser(rawUser));
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    private User mapToUser(RawUser rawUser) {
        User user = new User();
        user.setEmail(rawUser.getEmail());
        user.setFirstName(rawUser.getFullName().split(" ")[0]);
        user.setLastName(rawUser.getFullName().split(" ")[1]);
        return user;
    }

}
