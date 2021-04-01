package com.murraybroz.kafkademo;

import com.mycorp.mynamespace.SchemaOrdersValueV1;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;

@SpringBootApplication
public class KafkaDemoApplication {
	int min = 1;
	int max = 100;

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(KafkaTemplate<String, SchemaOrdersValueV1.SampleRecord> template) {
		Integer orderId = (int)(Math.random() * (max - min + 1) + min);

		SchemaOrdersValueV1.SampleRecord order = SchemaOrdersValueV1.SampleRecord.newBuilder()
				.setOrderId(orderId)
				.setOrderTime((int) Instant.now().toEpochMilli())
				.setOrderAddress(orderId + " East St. East Helena, MT 59635")
				.build();

		return args -> {
			template.send("orders", order);
		};
	}
}
