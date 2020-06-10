package sningning.community;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @ClassName: LoggerTests
 * @Author: Song Ningning
 * @Date: 2020-04-23 17:30
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoggerTests {

    private static final Logger LOGGER = LoggerFactory.getLogger("LoggerTests");

    @Test
    public void testLogger() {

        System.out.println(LOGGER.getName());
        LOGGER.debug("debug log");
        LOGGER.info("info log");
        LOGGER.warn("warn log");
        LOGGER.error("error log");
    }

}
