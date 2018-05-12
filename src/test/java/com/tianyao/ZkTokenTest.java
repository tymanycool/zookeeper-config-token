package com.tianyao;

import com.tianyao.entity.TokenConfig;
import org.junit.Test;

public class ZkTokenTest {

    @Test
    public void init() {
        ZkTokenManager mag = new ZkTokenManager();
        TokenConfig config = mag.downLoadConfigFromDB();
        System.out.println("....加载数据库配置...." + config.toString());
        mag.syncConfigToZk();
        System.out.println("....同步配置文件到zookeeper....");


    }

    @Test
    public void update() {
        ZkTokenManager mag = new ZkTokenManager();
        TokenConfig config = mag.downLoadConfigFromDB();
//        System.out.println("....加载数据库配置...."+config.toString());
//        mag.syncConfigToZk();
//        System.out.println("....同步配置文件到zookeeper....");

        //歇会，这样看比较清晰
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mag.upLoadConfigToDB("newTokenValue666", "20180512120000");
        System.out.println("....修改配置文件...." + config.toString());
        mag.syncConfigToZk();
        System.out.println("....同步配置文件到zookeeper....");


    }
}
