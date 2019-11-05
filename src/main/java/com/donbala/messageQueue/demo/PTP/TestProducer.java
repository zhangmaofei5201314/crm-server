package com.donbala.messageQueue.demo.PTP;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 发送一个字符串文本到ActiveMQ
 * @date 2019/9/27
 */
public class TestProducer {


    public void sendMessage(String datas){
        //连接工厂
        ConnectionFactory factory = null;
        //连接
        Connection connection = null;
        //目的地
        Destination destination = null;
        //会话
        Session session = null;
        //消息发送者
        MessageProducer producer = null;
        //消息对象
        Message message = null;
        try {
            //三个参数，用户名，密码，连接地址
            factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.137.129:61616");
            //创建连接的方法有重载，createConnection(String username,String password);
            //可以在创建工厂时，只传地址，创建连接时传用户名和密码
            connection = factory.createConnection();
            //建议启动连接，消息的发送者不是必须启动连接，消息的消费者必须启动连接
            connection.start();
            //通过连接对象创建会话，必须绑定目的地
            /*
             * 创建会话要传递两个参数，分别代表是否支持事务和消息的确认方式
             * 第一个参数，是否支持事务，类型为boolean，true-支持，false-不支持
             * true，第二个参数无效，建议传递Session.SESSION_TRANSACTED
             * false，第二个参数必须传，且必须有效
             *
             * 第二个参数，如何确认消息的处理。使用确认机制实现。
             *  AUTO_ACKNOWLEDGE  自动确认消息，消息的消费者处理消息后，自动确认。
             *  CLIENT_ACKNOWLEDGE  客户端手动确认，消息的消费者处理后，必须手工确认。
             *  DUPS_OK_ACKNOWLEDGE  有副本的客户端手动确认
             *        一个消息多次处理，在可以容忍重复的情况下使用(不推荐使用)
             */
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("first-mq");
            //创建消息的发送者，发到一定的目的地
            producer = session.createProducer(destination);
            //创建消息对象，作为数据的载体
            message = session.createObjectMessage(datas);
            for (int i = 0; i <5 ; i++) {
                //发送
                producer.send(message);
            }

            System.out.println("消息已发送！");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //回收资源
            if(producer!=null){
                try {
                    producer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(session!=null){
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static void main(String[] args) {
        TestProducer testProducer = new TestProducer();
        testProducer.sendMessage("以我热血染青天");
    }
}
