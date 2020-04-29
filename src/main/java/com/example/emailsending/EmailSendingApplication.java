package com.example.emailsending;

import com.example.emailsending.dataReader.StockInfoGenerator;
import com.example.emailsending.email.EmailSender;
import com.example.emailsending.email.MessageGenerator;
import com.example.emailsending.model.Customer;
import com.example.emailsending.model.StockData;
import com.example.emailsending.model.StockDataId;
import com.example.emailsending.repositories.CustomerRepository;
import com.example.emailsending.repositories.StockDataRepository;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Optional;

@EnableEncryptableProperties
@SpringBootApplication
public class EmailSendingApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(EmailSendingApplication.class);

	@Autowired
	private EmailSender emailSender;

	@Value("${stocks}")
	private String[] stocks;

	@Autowired
	private StockInfoGenerator infoGenerator;

	@Autowired
	private MessageGenerator messageGenerator;

	@Autowired
	private StockDataRepository stockDataRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(EmailSendingApplication.class, args).close();
	}

	@Override
	public void run(String... args) throws JSONException, ParseException {

		logger.info("running the application");

		Customer customer1 = new Customer("Val", "Yip", 20);
		Customer customer2 = new Customer("Xiao Meng", "Yip", 3);
		customerRepository.save(customer1);
		customerRepository.save(customer2);

		for (Customer customer : customerRepository.findAll()) {
			System.out.println(customer.getFirstName() + customer.getLastName());
		}

		StockData stockData1 = new StockData();
		stockData1.setName("VOO");
		stockData1.setDate("2020-04-29");
		stockData1.setOpenPrice("260");
		stockData1.setClosePrice("263");
		StockDataId id1 = new StockDataId("VOO", "2020-04-29");
		stockDataRepository.save(stockData1);

		StockData stockData2 = new StockData();
		stockData2.setName("VOO");
		stockData2.setDate("2020-04-30");
		stockData2.setOpenPrice("250");
		stockData2.setClosePrice("253");
		StockDataId id2 = new StockDataId("VOO", "2020-04-30");
		stockDataRepository.save(stockData2);

		for (StockData stockData : stockDataRepository.findAll()) {
			System.out.println(stockData.getName() + stockData.getClosePrice());
		}
		System.out.println(stockDataRepository.existsById(id1));


//		HashMap<String, StockData> stocksCollection = infoGenerator.generateInfo();
//
//		infoGenerator.saveInfo(stockDataRepository, stocksCollection);
//
//		logger.info("Stock data found with findAll(): ");
//		for (StockData stockData : stockDataRepository.findAll()) {
//			logger.info(stockData.toString());
//		}
//
//		logger.info("Stock data found using findById(): ");
//		for (int i = 0; i< stocks.length; i++) {
//			Optional<StockData> stockData = stockDataRepository.findById(stocks[i]);
//			logger.info(String.valueOf(stockData));
//
//		}

//		String message = messageGenerator.generateMessage(data);
//
//		try {
//			emailSender.sendEmail(message);
//		} catch (MessagingException e) {
//			logger.error("Error: " + e);
//		}



	}
}
