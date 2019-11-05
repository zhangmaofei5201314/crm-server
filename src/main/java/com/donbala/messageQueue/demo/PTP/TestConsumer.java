package com.donbala.messageQueue.demo.PTP;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 消费端
 * @date 2019/9/27
 */
public class TestConsumer {

    public String receiveMessage() {

        String result = "";
        //连接工厂
        ConnectionFactory factory = null;
        //连接
        Connection connection = null;
        //目的地
        Destination destination = null;
        //会话
        Session session = null;
        //消息接收者
        MessageConsumer consumer = null;
        //消息对象
        Message message = null;
        try {
            factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.137.129:61616");
            connection = factory.createConnection();
            //消费者必须启动连接，否则无法处理消息
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("first-mq");
            //创建消费者，从指定目的地获取消息
            consumer = session.createConsumer(destination);
            //获取队列中的消息，这种只是测试学习用
            message = consumer.receive();
            result = ((TextMessage) message).getText();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //回收资源
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        TestConsumer testConsumer = new TestConsumer();
        String result = testConsumer.receiveMessage();
        System.out.println("获取的消息是：" + result);
    }

}
