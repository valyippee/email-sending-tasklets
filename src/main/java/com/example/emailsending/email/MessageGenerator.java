package com.example.emailsending.email;

import com.example.emailsending.EmailSendingApplication;
import com.example.emailsending.dataReader.StockInfoGenerator;
import com.example.emailsending.model.Data;
import com.example.emailsending.model.StockData;
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
import java.util.HashMap;

@Component
public class MessageGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MessageGenerator.class);

    @Autowired
    private StockData data;

    @Autowired
    private StockInfoGenerator infoGenerator;

    @Value("${stocks}")
    private String[] stocksAnalysed;

    /**
     * construct HTML format email using stock data and velocity template
     *
     * @param stock data
     * @return HTML format message with data in a string
     */
    public String generateMessage(Data data) {
        Velocity.init();

        logger.info("initialised velocity");

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        VelocityContext velocityContext = new VelocityContext();

        Template template = velocityEngine.getTemplate("templates/message.vm");

        // put stuff in context (from stock data class)

        velocityContext.put("stocks", data.getStocks().entrySet());
        velocityContext.put("date", infoGenerator.getDate());

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);

        return stringWriter.toString();


    }
}
