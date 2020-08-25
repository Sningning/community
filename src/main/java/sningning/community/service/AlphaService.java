package sningning.community.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import sningning.community.dao.AlphaDao;
import sningning.community.dao.DiscussPostMapper;
import sningning.community.dao.UserMapper;
import sningning.community.entity.DiscussPost;
import sningning.community.entity.User;
import sningning.community.util.CommunityUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * @author: Song Ningning
 * @date: 2020-04-21 0:09
 */
@Service
// @Scope("prototype")
public class AlphaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlphaService.class);

    public AlphaService() {
        // System.out.println("实例化AlphaService");
    }

    @PostConstruct
    public void init() {
        // System.out.println("初始化AlphaService");
    }

    @PreDestroy
    public void destroy() {
        // System.out.println("销毁AlphaService");
    }

    @Autowired
    private AlphaDao alphaDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    TransactionTemplate transactionTemplate;

    public String find() {
        return alphaDao.select();
    }

    /**
     * 声明式事务
     *
     * 常用的传播机制：
     *     REQUIRED: 支持当前事务(外部事务),如果不存在则创建新事务.
     *     REQUIRES_NEW: 创建一个新事务,并且暂停当前事务(外部事务).
     *     NESTED: 如果当前存在事务(外部事务),则嵌套在该事务中执行(独立的提交和回滚),否则就会REQUIRED一样.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String save1() {
        // 新增用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
        user.setEmail("alpha@qq.com");
        user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        // 新增帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle("Hello");
        post.setContent("新人报道!");
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);

        Integer.valueOf("abc");

        return "ok";
    }

    /**
     * 编程式事务
     */
    public String save2() {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return transactionTemplate.execute(new TransactionCallback<String>() {
            @Override
            public String doInTransaction(TransactionStatus transactionStatus) {
                // 新增用户
                User user = new User();
                user.setUsername("beta");
                user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
                user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
                user.setEmail("beta@qq.com");
                user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);

                // 新增帖子
                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId());
                post.setTitle("你好");
                post.setContent("新人报道!");
                post.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(post);

                Integer.valueOf("abc");

                return "ok";
            }
        });
    }

    /**
     * 让该方法在多线程环境下，被异步调用
     */
    @Async
    public void execute1() {
        LOGGER.debug("execute1");
    }

    /**
     * 让该方法在多线程环境下，被异步调用
     */
    @Scheduled(initialDelay = 5000, fixedRate = 1000)
    public void execute2() {
        LOGGER.debug("execute2");
    }

}
