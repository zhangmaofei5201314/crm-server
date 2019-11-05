package com.donbala.messageQueue.kafkaDemo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 生产者
 * @date 2019/10/30
 */
public class ProducerDemo {
    public static void main(String[] args) {
        Properties properties =new Properties();
        //zookeeper服务器集群地址，用逗号隔开
        properties.put("bootstrap.servers", "192.168.137.128:9092,192.168.137.128:9093,192.168.137.128:9094");
        properties.put("acks", "all");//0-producer只管发，不论leader是否收到；1-producer只有leader确认收到后，才会发送下一条；all-所有的leader和副本全部确认收到，才会发送下一条
        //重试次数
        properties.put("retries", 0);
        //批量大小
        properties.put("batch.size", 16384);
        //延时时间
        properties.put("linger.ms", 1);
        //缓存
        properties.put("buffer.memory", 33554432);
        //指定序列化类
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //自定义producer拦截器
//        properties.put("interceptor.classes", "com.donbala.messageQueue.kafkaDemo.MyProducerInterceptor");
        //自定义消息路由规则（消息发送到哪一个Partition中）
        //properties.put("partitioner.class", "com.lt.kafka.producer.MyPartition");

        Producer<String, String> producer = null;
        try {
            producer = new KafkaProducer<String, String>(properties);
            for (int i = 0; i < 2; i++) {
                String msg = "This is Message:" + i;

                /**
                 * kafkaproducer中会同时调用自己的callback的onCompletion方法和producerIntercepter的onAcknowledgement方法。
                 * 关键源码：Callback interceptCallback = this.interceptors == null
                 * callback : new InterceptorCallback<>(callback,
                 * this.interceptors, tp);
                 */
                producer.send(new ProducerRecord<String, String>("first", msg),new MyCallback());
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(producer!=null)
                producer.close();
        }
    }
}
