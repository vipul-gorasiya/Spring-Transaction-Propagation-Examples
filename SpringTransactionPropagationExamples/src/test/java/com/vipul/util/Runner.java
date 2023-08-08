package com.vipul.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Runner {

    @Transactional
    public void runWithTransaction(Runnable target) {
        target.run();
    }

    public void runWithoutTransaction(Runnable target) {
        target.run();
    }
}
