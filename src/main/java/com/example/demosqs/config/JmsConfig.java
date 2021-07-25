package com.example.demosqs.config;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.Session;

@Configuration
@EnableJms
public class JmsConfig {

	@Value("${amazon.accessKey}")
	private String accessKey;
	
	@Value("${amazon.secretKey}")
	private String secretKey;

	@Autowired
	private SQSConnectionFactory connectionFactory;

	@Bean
	public SQSConnectionFactory createConnectionFactory() {
		return SQSConnectionFactory.builder().withRegion(Region.getRegion(Regions.US_EAST_1))
				.withAWSCredentialsProvider(
						new StaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.build();
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(this.connectionFactory);
		factory.setDestinationResolver(new DynamicDestinationResolver());
		factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);

		return factory;
	}

	@Bean
	public JmsTemplate defaultJmsTemplate() {
		return new JmsTemplate(this.connectionFactory);
	}
}
