package sningning.community.dao.impl;

import org.springframework.stereotype.Repository;
import sningning.community.dao.AlphaDao;

/**
 * @author: Song Ningning
 * @date: 2020-04-20 23:19
 */
@Repository("hibernate")
public class AlphaDaoHibernateImpl implements AlphaDao {
    @Override
    public String select() {
        return "Hibernate";
    }
}
