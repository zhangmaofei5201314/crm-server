package com.donbala.messageQueue.kafkaDemo;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 消费监听
 * @date 2019/10/30
 */
public class MyConsumerInterceptor implements ConsumerInterceptor<String,String> {
    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> consumerRecords) {
        System.out.println("onConsume");
        return consumerRecords;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {
        System.out.println("onCommit");
    }

    @Override
    public void close() {
        System.out.println("MyConsumerInterceptor is closed!");
    }

    @Override
    public void configure(Map<String, ?> configs) {
        System.out.println("MyConsumerInterceptor configs>>>"+configs.toString());
    }
}
