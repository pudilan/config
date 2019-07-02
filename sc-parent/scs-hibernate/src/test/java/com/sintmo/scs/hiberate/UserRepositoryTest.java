package com.sintmo.scs.hiberate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sintmo.scs.hiberate.entity.User;
import com.sintmo.scs.hiberate.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void start() {
        System.out.println("------------finish!");
    }

    @Test
    public void insert() throws Exception {
        userRepository.save(new User("abc11", "scrovor1", "https://sdfdsfsd1"));
        userRepository.save(new User("abc12", "scrovor2", "https://sdfdsfsd2"));
        userRepository.save(new User("abc13", "scrovor3", "https://sdfdsfsd3"));
    }

}
