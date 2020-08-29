package sningning.community;

import org.junit.*;
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
 * @date: 2020-08-29 10:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SpringBootTests {

    @Autowired
    private DiscussPostService discussPostService;

    private DiscussPost data;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("@BeforeClass");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("@AfterClass");
    }

    @Before
    public void before() {
        System.out.println("@Before");

        // 初始化测试数据
        data = new DiscussPost();
        data.setUserId(111);
        data.setTitle("Test Title");
        data.setContent("Test Content");
        data.setCreateTime(new Date());
        discussPostService.addDiscussPost(data);
    }

    @After
    public void after() {
        System.out.println("@After");

        // 删除测试数据
        discussPostService.updateStatus(data.getId(), 2);
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void testFindById() {
        DiscussPost post = discussPostService.findDiscussPostById(data.getId());
        Assert.assertNotNull(post);
        Assert.assertEquals(data.getTitle(), data.getTitle());
        Assert.assertEquals(data.getContent(), data.getContent());
        Assert.assertEquals(data.getCreateTime(), data.getCreateTime());
    }

    @Test
    public void testUpdateScore() {
        int rows = discussPostService.updateScore(data.getId(), 2000.0);
        Assert.assertEquals(1, rows);
        DiscussPost post = discussPostService.findDiscussPostById(data.getId());
        Assert.assertEquals(2000.0, post.getScore(), 2);
    }
}
