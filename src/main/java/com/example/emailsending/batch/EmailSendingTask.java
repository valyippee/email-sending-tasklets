package com.example.emailsending.batch;

import com.example.emailsending.email.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * tasklet to send email by calling EmailSender sendEmail() method
 */
@Component
public class EmailSendingTask implements Tasklet, StepExecutionListener {

    private final static Logger logger = LoggerFactory.getLogger(EmailSendingTask.class);

    @Autowired
    private EmailSender emailSender;

    private static byte counter = 0;

    private static boolean emailSentStatus = false;

    /**
     *
     * calls sendEmail method for up to three times if there is an error
     * @param stepContribution
     * @param chunkContext
     * @return RepeatStatus of the step
     */
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        logger.info("inside email sending task");

        try {
            counter += 1;
            emailSender.sendEmail();
            emailSentStatus = true;
        } catch (Exception e) {
            logger.error("messaging exception, email not sent");
            if (counter < 3) {
                return RepeatStatus.CONTINUABLE;
            }
            if (counter == 3) {
                logger.error("email cannot be sent (after three tries)");
            }
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    /**
     * if email is not successfully sent, exitstatus would be FAILED
     * @param stepExecution
     * @return
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (!emailSentStatus) {
            return ExitStatus.FAILED;
        }
        return ExitStatus.COMPLETED;
    }
}
