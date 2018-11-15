package com.csdm.newsfeed;

import com.csdm.newsfeed.controller.SyndicationController;
import com.csdm.newsfeed.util.BeanUtil;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class App {

	/**
	 * Entry point for the Spring Boot App
	 * @param args String[]
	 *
	 */
	public static void main(String[] args) throws IOException {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"config/rss-inbound.xml");
		BeanUtil.getBean(SyndicationController.class);

		try {
			PollableChannel feedChannel = context.getBean("feedChannel", PollableChannel.class);
			for (int i = 0; i < 10; i++) {
				Message<SyndEntry> message = (Message<SyndEntry>) feedChannel.receive(300000);
				if (message != null) {
					BeanUtil.getBean(SyndicationController.class)
							.handleMessage(message);
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
	}

	//SpringApplication.run(App.class, args);
}
