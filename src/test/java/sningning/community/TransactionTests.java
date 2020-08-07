package sningning.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sningning.community.service.AlphaService;

/**
 * @author: Song Ningning
 * @date: 2020-08-07 10:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class TransactionTests {

    @Autowired
    private AlphaService alphaService;

    @Test
    public void testSave1() {
        String s = alphaService.save1();
        System.out.println(s);
    }

    @Test
    public void testSave2() {
        String s = alphaService.save2();
        System.out.println(s);
    }
}
