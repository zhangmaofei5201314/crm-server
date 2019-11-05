package com.donbala.messageQueue.kafkaDemo;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 自定义路由规则，可以根据自己的需要定义消息发送到哪个分区。自定义路由规则需要实现Partitioner。
 * @date 2019/10/30
 */
public class MyPartition implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
