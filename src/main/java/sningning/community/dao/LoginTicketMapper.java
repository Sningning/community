package sningning.community.dao;

import org.apache.ibatis.annotations.*;
import sningning.community.entity.LoginTicket;

/**
 * @author: Song Ningning
 * @date: 2020-08-03 22:28
 */
@Mapper
@Deprecated
public interface LoginTicketMapper {

    /**
     * 插入 LoginTicket
     *
     * @param loginTicket
     * @return
     */
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    /**
     * 通过 ticket 查询 LoginTicket
     *
     * @param ticket
     * @return
     */
    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket} "
    })
    LoginTicket selectByTicket(String ticket);

    /**
     * 更改 ticket 状态
     *
     * @param ticket
     * @param status 状态
     * @return
     */
    @Update({
            "update login_ticket set status=#{status} ",
            "where ticket=#{ticket} "
    })
    int updateStatus(String ticket, int status);
}
