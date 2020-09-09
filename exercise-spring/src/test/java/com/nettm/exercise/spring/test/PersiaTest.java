package com.nettm.exercise.spring.test;

import com.nettm.exercise.spring.bean.MyApplicationContextAware;
import com.nettm.exercise.spring.cat.ICat;
import com.nettm.exercise.spring.cat.MyCat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PersiaTest extends BaseTest {

    @Autowired
    private MyCat myCat;

    @Test
    public void run() {
        ICat iCat = MyApplicationContextAware.getBean(ICat.class);
        iCat.run();
        myCat.run();
    }
}
