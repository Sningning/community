package sningning.community.dao.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import sningning.community.dao.AlphaDao;

/**
 * @author: Song Ningning
 * @date: 2020-04-21 0:02
 */
@Repository
@Primary
public class AlphaDaoMyBatisImpl implements AlphaDao {
    @Override
    public String select() {
        return "MyBatis";
    }
}
