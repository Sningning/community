package sningning.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sningning.community.entity.DiscussPost;
import sningning.community.service.DiscussPostService;

import java.util.Date;

/**
 * @author: Song Ningning
 * @date: 2020-08-28 22:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CaffeineTests {

    @Autowired
    private DiscussPostService postService;

    @Test
    public void initDataForTest() {
        for (int i = 0; i < 300000; i++) {
            DiscussPost post = new DiscussPost();
            post.setUserId(111);
            post.setTitle("今年就业真是难");
            post.setContent("今年的就业形势，确实不容乐观。来了个疫情，仿佛跳水一般，21届真的没人要了吗？！");
            post.setCreateTime(new Date());
            post.setScore(Math.random() * 2000);
            postService.addDiscussPost(post);
        }
    }

    @Test
    public void testCache() {
        System.out.println(postService.findDiscussPost(0, 0, 10, 1));
        System.out.println(postService.findDiscussPost(0, 0, 10, 1));
        System.out.println(postService.findDiscussPost(0, 0, 10, 1));
        System.out.println(postService.findDiscussPost(0, 0, 10, 0));
    }
}
