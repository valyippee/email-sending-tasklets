package com.example.emailsending.email;

import com.example.emailsending.dataReader.StockInfoGenerator;
import com.example.emailsending.model.StockData;
import com.example.emailsending.repositories.StockDataRepository;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MessageGenerator.class);

    @Value("${stocks}")
    private String[] stocksAnalysed;

    @Autowired
    private StockDataRepository stockDataRepository;

    /**
     * construct HTML format email using stock data and velocity template
     *
     * @return HTML format message with data in a string
     */
    public String generateMessage() {
        Velocity.init();

        logger.info("initialised velocity");

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        VelocityContext velocityContext = new VelocityContext();

        Template template = velocityEngine.getTemplate("templates/message.vm");

        // put stuff in context (from stock data repository)

        String marketDate = StockInfoGenerator.getDate();

        // stockDataList contains stocks that need to be included in email
        List<StockData> stockDataList = new ArrayList<StockData>();
        for (StockData stockData : stockDataRepository.findAll()) {
            if (stockData.getDate().equals(marketDate)) {
                for (String stock : stocksAnalysed) {
                    if (stockData.getName().equals(stock)) {
                        stockDataList.add(stockData);
                    }
                }
            }
        }

        velocityContext.put("date", marketDate);
        velocityContext.put("stocks", stockDataList);

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);

        return stringWriter.toString();


    }
}
