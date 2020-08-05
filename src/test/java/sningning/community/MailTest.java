package sningning.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sningning.community.util.MailClient;

/**
 * @author: Song Ningning
 * @date: 2020-08-03 11:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {

    @Autowired
    public MailClient mailClient;

    @Autowired
    public TemplateEngine templateEngine;

    @Test
    public void testTextMail() {
        mailClient.sendMail("724718513@qq.com", "SpringMailTest", "测试");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "Jeremy");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("724718513@qq.com", "TestHtmlMail", content);
    }
}
