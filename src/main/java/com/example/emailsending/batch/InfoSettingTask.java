package com.example.emailsending.batch;

import com.example.emailsending.dataReader.StockInfoGenerator;
import org.json.simple.parser.ParseException;
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
 * tasklet that calls StockInfoGenerator to save data into repository
 */
@Component
public class InfoSettingTask implements Tasklet, StepExecutionListener {

    private final static Logger logger = LoggerFactory.getLogger(InfoSettingTask.class);


    @Autowired
    private StockInfoGenerator stockInfoGenerator;

    private static byte counter = 0;

    private static boolean setInfoStatus = false;

    /**
     *
     * calls settingInfo() for up to 20 times and sleeps for two minutes after each
     * failure since only 5 API calls can by made per minutes
     * (allows up to 100 API calls in theory)
     * @param stepContribution
     * @param chunkContext
     * @return
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        try {
            logger.info("inside infoSettingTask, trying to set info");
            stockInfoGenerator.settingInfo();

        } catch (NullPointerException nullPointerException) {
            logger.error("null pointer exception caught");
            counter += 1;
            if (counter == 20) {
                logger.error("failed to set all info (after 20 tries)");
            }
            Thread.sleep(2*60*1000);
            return RepeatStatus.CONTINUABLE;
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    /**
     * if info is not successfully set, exitstatus would be FAILED
     * @param stepExecution
     * @return
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (!setInfoStatus) {
            return ExitStatus.FAILED;
        }
        return ExitStatus.COMPLETED;
    }
}
