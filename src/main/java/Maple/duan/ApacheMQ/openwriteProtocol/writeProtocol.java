package Maple.duan.ApacheMQ.openwriteProtocol;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

public class writeProtocol {
	Logger log = Logger.getLogger(this.getClass());
	private Connection connection;
	public static String XHJQUERYSendQueueName = "MQ_XHJ_QUERY_Send"; //查询MQ发送
	@Test
	public void b() throws Exception{
		for(;;){
			Thread.sleep(1000);
			consumer_listener("system","manager","127.0.0.1","61616");
		}
	}
	
	/**
	 * 主动接受消息receive()阻塞方式
	 * @param user
	 * @param password
	 * @param ip
	 * @param port
	 */
	public void consumer(String user,String password,String ip,String port){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(  
			      user,   
			      password,   "failover:(tcp://127.0.0.1:61613)"
			      );  
	    try  
	    {  
	      Connection connection = connectionFactory.createConnection();  	        
	      connection.start();  	  
	      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  	
	      log.info("MQ连接成功");
	      Queue destination = session.createQueue(XHJQUERYSendQueueName);  // 创建连接的消息队列
	      MessageConsumer messageConsumer = session.createConsumer(destination); // 创建消息消费者
	        while(true){ 
	            TextMessage textMessage=(TextMessage)messageConsumer.receive(100000);
	            if(textMessage!=null){
	                log.info("收到的消息："+textMessage.getText());
	            }else{
	                break;
	            }
	        }
	    }  
	    catch (Exception e) {  
	      e.printStackTrace();  
	      log.error("MQ连接失败："+e.getMessage());
	    }
	}
	public void consumer_listener(String user,String password,String ip,String port){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(  
			      user,   
			      password,"failover:(tcp://127.0.0.1:61616)"//"tcp://"+ip + ":"+port   
			      );  
	        MessageConsumer messageConsumer; // 消息的消费者
	        // 实例化连接工厂
	        try {
	            connection=connectionFactory.createConnection();  // 通过连接工厂获取连接
	            connection.start(); // 启动连接
	            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE); // 创建Session
	            Queue destination = session.createQueue(XHJQUERYSendQueueName);  // 创建连接的消息队列
	            messageConsumer=session.createConsumer(destination); // 创建消息消费者
	            messageConsumer.setMessageListener(new Listener()); // 注册消息监听
	        } catch (JMSException e) {
	            // TODO Auto-generated catch block
	            log.error(e.getMessage());
	        } 
	}
}
class Listener implements MessageListener{
	Logger log = Logger.getLogger(this.getClass());
	public void onMessage(Message message) {
        // TODO Auto-generated method stub
        try {
            log.info("收到的消息："+((TextMessage)message).getText());
        } catch (JMSException e) {
            // TODO Auto-generated catch block
        	log.info("listener发生错误"+e.getMessage());
        }
	}
}