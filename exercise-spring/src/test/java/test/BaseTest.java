package test;

import com.nettm.exercise.spring.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public abstract class BaseTest {

    @Before
    public void before() {

    }

    @After
    public void after() {

    }
}
