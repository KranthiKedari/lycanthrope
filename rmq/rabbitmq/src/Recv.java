import java.io.IOException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Recv {

	private static final String Q_NAME ="kk";
	
	public static void main(String args[]) throws IOException , InterruptedException
	{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		
		channel.queueDeclare(Q_NAME, false, false, false, null);
		System.out.println(" waiting fot messages [x]");
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		boolean autoAck = false;
		channel.basicConsume(Q_NAME, autoAck, consumer);
		
		while(true)
		{
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			System.out.println("Received: "+ msg);
		}
		
	}
}
