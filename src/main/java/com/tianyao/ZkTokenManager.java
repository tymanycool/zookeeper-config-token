package com.tianyao;

import com.tianyao.dao.TokenDao;
import com.tianyao.entity.TokenConfig;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZkTokenManager {
    private TokenConfig config;

    @Autowired
    TokenDao tokenDao;
    /**
     * 从数据库加载配置
     */
    synchronized public TokenConfig downLoadConfigFromDB(){
        //getDB
       //config = new TokenConfig("token_value", "20180512120000");
        config = tokenDao.getConfig();
        return config;
    }

    /**
     * 配置文件上传到数据库
     */
    synchronized public void upLoadConfigToDB(String tokenValue, String expDate){
        if(config==null)config = new TokenConfig();
        config.setTokenValue(tokenValue);
        config.setExpDate(expDate);
        //updateDB
        tokenDao.updateConfig(config);
    }

    /**
     * 配置文件同步到zookeeper
     */
     public void  syncConfigToZk(){
        ZkClient zk = new ZkClient("192.168.33.128:2181");
        if(!zk.exists("/zkConfig")){
            zk.createPersistent("/zkConfig",true);
        }
        zk.writeData("/zkConfig", config);
        zk.close();
    }
}
