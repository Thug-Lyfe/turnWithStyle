/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstyle;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author butwhole
 */
public class Counter {

    AtomicInteger count = new AtomicInteger();
    AtomicInteger noStill = new AtomicInteger();

    public long getValue() {
        return count.longValue();
    }

    public synchronized void incr() {

        count.getAndAdd(1);

    }

    public long getNoStill() {
        return noStill.longValue();
    }

    public synchronized void incrNoStill() {

        noStill.getAndAdd(1);

    }
}
