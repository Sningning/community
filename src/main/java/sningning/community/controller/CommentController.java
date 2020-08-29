package sningning.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sningning.community.entity.Comment;
import sningning.community.entity.DiscussPost;
import sningning.community.entity.Event;
import sningning.community.event.EventProducer;
import sningning.community.service.CommentService;
import sningning.community.service.DiscussPostService;
import sningning.community.util.CommunityConstant;
import sningning.community.util.HostHolder;
import sningning.community.util.RedisKeyUtil;

import java.util.Date;

/**
 * @author: Song Ningning
 * @date: 2020-08-08 10:12
 */
@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") Integer discussPostId, Comment comment) {
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        // 触发评论事件
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId", discussPostId);

        // 评论的目标是帖子或评论

        // 如果评论的目标是帖子
        if (comment.getEntityType().equals(ENTITY_TYPE_POST)) {
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        } else if (comment.getEntityType().equals(ENTITY_TYPE_COMMENT)) {
            // 如果评论的目标是评论
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }

        eventProducer.fireEvent(event);

        // 触发发帖事件
        if (comment.getEntityType().equals(ENTITY_TYPE_POST)) {
            event = new Event()
                    .setTopic(TOPIC_PUBLISH)
                    .setEntityUserId(comment.getUserId())
                    .setEntityType(ENTITY_TYPE_POST)
                    .setEntityId(discussPostId);
            eventProducer.fireEvent(event);

            // 计算帖子分数
            String postKey = RedisKeyUtil.getPostScoreKey();
            redisTemplate.opsForSet().add(postKey, discussPostId);
        }

        return "redirect:/discuss/detail/" + discussPostId;
    }
}
