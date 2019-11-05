package com.donbala.messageQueue.demo.PTP;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 消费端-监听模式
 *
 * @date 2019/9/27
 */
public class TestConsumerListener {

    public static void main(String[] args) {
        TestConsumerListener tcl = new TestConsumerListener();
        tcl.receiveMessage();
    }

    public void receiveMessage() {

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
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("first-mq");
            //创建消费者，从指定目的地获取消息
            consumer = session.createConsumer(destination);

            //监听器注册成功后，队列中信息变化就会自动触发监听器的代码逻辑
            consumer.setMessageListener(new MessageListener() {
                /*
                 * 监听器一旦注册，永久有效，-consumer线程不关闭
                 * 处理消息的方式：只要有消息未处理，自动调用onMessage方法，处理消息
                 * 监听器可以注册多个，多个监听器相当于集群
                 *
                 * ActiveMQ自动轮循调用各个监听器，处理队列中的信息
                 *
                 */
                @Override
                public void onMessage(Message message) {
                    try {
                        //消息确认方法，代表consumer已收到消息，确认后MQ删除对应的消息
                        message.acknowledge();
                        ObjectMessage om = (ObjectMessage) message;
                        Object obj = om.getObject();
                        System.out.println("获取的消息是：" + obj);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }


                }
            });
            //挂起
            System.in.read();

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

    }
}
