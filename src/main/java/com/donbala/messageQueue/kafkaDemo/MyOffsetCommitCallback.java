package com.donbala.messageQueue.kafkaDemo;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 自定义Offset提交回调方法
 * @date 2019/10/30
 */
public class MyOffsetCommitCallback implements OffsetCommitCallback {
    @Override
    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception e) {
        if (offsets != null)
            System.out.println("offsets>>>" + offsets.toString());
        if (e != null)
            e.printStackTrace();
    }
}
