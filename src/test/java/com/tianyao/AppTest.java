package com.tianyao;

import static org.junit.Assert.assertTrue;

import com.tianyao.dao.TokenDao;
import com.tianyao.entity.TokenConfig;
import com.tianyao.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AppTest 
{
    @Autowired
    TokenDao tokenDao;
    @Autowired
    RestTemplate restTemplate;

    @Test
    public void test()
    {
        TokenConfig config = tokenDao.getConfig();
        System.out.println(config);
    }

    @Test
    public void testUpdate()
    {
        TokenConfig config = new TokenConfig();
        config.setTokenId("testid");
        config.setTokenValue("testvalue");
        config.setExpDate("testexpdate");
        int cnt = tokenDao.updateConfig(config);
        System.out.println(cnt);
    }

    @Test
    public void testDelete()
    {
        TokenConfig config = new TokenConfig();
        config.setTokenId("testid");
        config.setTokenValue("testvalue");
        config.setExpDate("testexpdate");
        int cnt = tokenDao.deleteConfig(config);
        System.out.println(cnt);
    }

    @Test
    public void testString(){
        String str = "20180511012342";
        String str2 = DateUtil.getCurrentTime();
        System.out.println(str.compareTo(str2));

    }

    @Test
    public void testApplyToken(){
        String ret = restTemplate.getForObject("http://localhost:8080/applyToken", String.class);
        System.out.println(ret);
    }
}
