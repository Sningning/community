package sningning.community.dao.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import sningning.community.entity.DiscussPost;

/**
 * @author: Song Ningning
 * @date: 2020-08-18 22:42
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {

}
