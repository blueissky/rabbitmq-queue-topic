package Maple.duan.ApacheMQ.openwriteProtocol;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

public class ProducerMaple {
	Logger log = Logger.getLogger(this.getClass());
	public static String XHJQUERYSendQueueName = "MQ_XHJ_QUERY_Send"; //查询MQ发送
	private Connection connection;
	@Test
	public void a(){
		producer("system","manager","localhost","61616");	
	}
	
	public void producer(String user,String password,String ip,String port) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user,password,"tcp://"+ip + ":"+port);  
	    try  
	    {  
	      connection = connectionFactory.createConnection();  	        
	      connection.start();  	  
	      log.info("MQ连接成功");
	      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  	
	      Queue destination = session.createQueue(XHJQUERYSendQueueName); // 创建消息队列
	      MessageProducer messageProducer = session.createProducer(destination); // 创建消息生产者
	      sendMessage(session,messageProducer);
	      session.commit();
	    } catch (Exception e) {
	        log.info("连接失败"+e.getMessage());
	    } finally{
	        if(connection!=null){
	            try {
	                connection.close();
	            } catch (JMSException e) {
	            	log.info("关闭连接失败"+e.getMessage());
	            }
	            log.info("关闭连接");
	        }
	    }  
	}
	public void sendMessage(Session session,MessageProducer messageProducer)throws Exception{
        for(;;){
            TextMessage message=session.createTextMessage("ActiveMQ 发送的消息");
            messageProducer.send(message);
            log.info("send success!");
            Thread.sleep(1000);
        }
	}
}
