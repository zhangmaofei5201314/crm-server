package com.donbala.messageQueue.kafkaDemo;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 *
 * @date 2019/10/30
 */
public class MyCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        System.out.println("There is MyCallback!");
    }
}
