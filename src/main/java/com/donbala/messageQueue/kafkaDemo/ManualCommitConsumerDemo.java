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
 * 手动提交
 * @date 2019/10/30
 */
public class ManualCommitConsumerDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.0.218:9092,172.16.0.219:9092,172.16.0.217:9092");
        props.put("group.id", "leixiang");
        props.put("enable.auto.commit", "false");//手动确认
        /* 自动确认offset的时间间隔 */
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");//想要读取之前的数据，必须加上
        /*
         * 一旦consumer和kakfa集群建立连接，
         * consumer会以心跳的方式来高速集群自己还活着，
         * 如果session.timeout.ms 内心跳未到达服务器，服务器认为心跳丢失，会做rebalence
         */
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //配置自定义的拦截器，可以在拦截器中引入第三方插件实现日志记录等功能。
        props.put("interceptor.classes", "com.lt.kafka.consumer.MyConsumerInterceptor");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String,String>(props);
        /* 消费者订阅的topic, 可同时订阅多个 ，用逗号隔开*/
        consumer.subscribe(Arrays.asList("leixiang"));
        while (true) {
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String,String> record : records) {
                //处理消息
                saveMessage(record);
                //手动提交，并且设置Offset提交回调方法
                //consumer.commitAsync(new MyOffsetCommitCallback());
                consumer.commitAsync();
            }
        }
    }

    public static void saveMessage(ConsumerRecord<String,String> record){
        System.out.printf("处理消息：offset = %d, key = %s, value = %s%n", record.offset(), record.key(),
                record.value());
    }
}
