package sningning.community.config;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import sningning.community.quartz.AlphaJob;

/**
 * @author: Song Ningning
 * @date: 2020-08-25 20:46
 */
@Configuration
public class QuartzConfig {
    /**
     * 只会调用一次，存入数据库后，后面会从数据库读取
     */

    /**
     * FactoryBean 可简化Bean的实例化过程：
     * 1.通过FactoryBean封装Bean的实例化过程；
     * 2.将FactoryBean装配到spring容器
     * 3.将FactoryBean注入给其他的Bean
     * 4.该Bean得到的是FactoryBean所管理的对象实例
     */

    /**
     * 配置 JobDetail
     *
     * @return
     */
    // @Bean
    public JobDetailFactoryBean alphaJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AlphaJob.class);
        factoryBean.setName("alphaJob");
        factoryBean.setGroup("alphaJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    /**
     * 配置 Trigger(SimpleTriggerFactoryBean, CronTriggerFactoryBean)
     *
     * @param alphaJobDetail
     * @return
     */
    // @Bean
    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(alphaJobDetail);
        factoryBean.setName("alphaTrigger");
        factoryBean.setGroup("alphaTriggerGroup");
        factoryBean.setRepeatInterval(3000);
        factoryBean.setJobDataMap(new JobDataMap());
        return factoryBean;
    }
}
