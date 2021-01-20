package ru.vlarp.musorg.rmql.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vlarp.musorg.rmql.utils.TopicNameList;

@Configuration
public class RabbitMqConfig {
    @Value("${rmq.addr}")
    private String rmqAddress;

    @Value("${rmq.username}")
    private String rmqUsername;

    @Value("${rmq.password}")
    private String rmqPassword;

    @Bean
    public ConnectionFactory rmqConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rmqAddress);
        connectionFactory.setUsername(rmqUsername);
        connectionFactory.setPassword(rmqPassword);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory rmqConnectionFactory) {
        return new RabbitAdmin(rmqConnectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory rmqConnectionFactory) {
        return new RabbitTemplate(rmqConnectionFactory);
    }

    @Bean
    public Queue sampleQuery() {
        return new Queue(TopicNameList.SAMPLE_QUEUE_NAME);
    }

    @Bean
    public Queue rawTrackInfoQueue() {
        return new Queue(TopicNameList.RAW_TRACK_INFO);
    }
}
