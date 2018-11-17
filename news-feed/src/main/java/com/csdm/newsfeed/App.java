package com.csdm.newsfeed;

import com.csdm.newsfeed.controller.SyndicationController;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class App {

    @Autowired
    private SyndicationController syndicationController;

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "config/rss-inbound.xml");

	/**
	 * Entry point for the Spring Boot App
     *
	 * @param args String[]
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

    /**
     * Bean that runs when it is contained within a SpringApplication
     *
     * @return
     */
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

            try {
                PollableChannel feedChannel = context.getBean("feedChannel", PollableChannel.class);
                for (int i = 0; i < 100; i++) {
                    Message<SyndEntry> message = (Message<SyndEntry>) feedChannel.receive(300000);
                    if (message != null) {
                        syndicationController.handleMessage(message);
                    } else {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Press 'Enter' to terminate");
                System.in.read();
                context.close();
            }
        };
    }
}
