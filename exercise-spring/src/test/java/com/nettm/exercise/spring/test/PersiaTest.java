package com.nettm.exercise.spring.test;

import com.nettm.exercise.spring.cat.ICat;
import com.nettm.exercise.spring.cat.MyCat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PersiaTest extends BaseTest {

    @Autowired
    private MyCat myCat;

    @Autowired
    private ICat iCat;

    @Test
    public void run() {
        iCat.run();
        myCat.run();
    }
}
