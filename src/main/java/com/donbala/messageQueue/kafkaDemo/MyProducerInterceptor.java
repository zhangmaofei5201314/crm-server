package com.donbala.messageQueue.kafkaDemo;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 自定义producer拦截器
 * @date 2019/10/30
 */
public class MyProducerInterceptor implements ProducerInterceptor<String,String> {
    /**
     * 打印配置相关信息
     */
    public void configure(Map<String,?> configs) {
        // TODO Auto-generated method stub
        System.out.println(configs.toString());
    }

    /**
     * producer发送信息拦截方法
     */
    public ProducerRecord<String,String> onSend(ProducerRecord<String, String> record) {
        System.out.println("拦截处理前》》》");
        String topic=record.topic();
        String value=record.value();
        System.out.println("拦截处理前的消息："+value);
        ProducerRecord<String, String> record2=new ProducerRecord<String, String>(topic, value+" (intercepted)");
        System.out.println("拦截处理后的消息："+record2.value());
        System.out.println("拦截处理后《《《");
        return record2;
    }

    /**
     * 消息确认回调函数，和callback的onCompletion方法相似。
     * 在kafkaProducer中，如果都设置，两者都会调用。
     */
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (metadata != null)
            System.out.println("MyProducerInterceptor onAcknowledgement:RecordMetadata=" + metadata.toString());
        if (exception != null)
            exception.printStackTrace();
    }

    /**
     * interceptor关闭回调
     */
    public void close() {
        System.out.println("MyProducerInterceptor is closed!");
    }


}
