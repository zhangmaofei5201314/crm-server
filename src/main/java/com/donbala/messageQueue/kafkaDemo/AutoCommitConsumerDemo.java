package com.donbala.messageQueue.kafkaDemo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 自动提交
 * @date 2019/10/30
 */
public class AutoCommitConsumerDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.137.128:9092,192.168.137.128:9093,192.168.137.128:9094");
        props.put("group.id", "test-consumer-group");
        props.put("enable.auto.commit", "true");
        //想要读取之前的数据，必须加上
        //props.put("auto.offset.reset", "earliest");
        /* 自动确认offset的时间间隔 */
        props.put("auto.commit.interval.ms", "1000");
        /*
         * 一旦consumer和kakfa集群建立连接，
         * consumer会以心跳的方式来高速集群自己还活着，
         * 如果session.timeout.ms 内心跳未到达服务器，服务器认为心跳丢失，会做rebalence
         */
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //配置自定义的拦截器，可以在拦截器中引入第三方插件实现日志记录等功能。
        //props.put("interceptor.classes", "com.lt.kafka.consumer.MyConsumerInterceptor");

        @SuppressWarnings("resource")
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String,String>(props);
        try {
            /* 消费者订阅的topic, 可同时订阅多个 ，用逗号隔开*/
            consumer.subscribe(Arrays.asList("first"));
            while (true) {
                //轮询数据。如果缓冲区中没有数据，轮询等待的时间为毫秒。如果0，立即返回缓冲区中可用的任何记录，则返回空
                ConsumerRecords<String,String> records = consumer.poll(100);
                for (ConsumerRecord<String,String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(),
                        record.value());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
