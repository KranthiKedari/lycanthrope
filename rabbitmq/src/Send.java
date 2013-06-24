import java.io.IOException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;

public class Send {

	private static final String Q_NAME = "kk";
	
	public static void main(String args[]) throws IOException
	{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		
		channel.queueDeclare(Q_NAME, false, false, false, null);
		String msg = "WTF";
		
		for(int i = 1; i<= 20; i++)
		{
			
			channel.basicPublish("", Q_NAME, null, (msg+i).getBytes());
			System.out.println("sent "+ (msg+i));
		}
		channel.close();
		conn.close();
	}
}
