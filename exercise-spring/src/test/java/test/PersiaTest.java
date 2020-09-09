package test;

import com.nettm.exercise.spring.bean.MyApplicationContextAware;
import com.nettm.exercise.spring.cat.ICat;
import org.junit.Test;

public class PersiaTest extends BaseTest {

    @Test
    public void run() {
        ICat iCat = MyApplicationContextAware.getBean(ICat.class);
        iCat.run();
    }
}
