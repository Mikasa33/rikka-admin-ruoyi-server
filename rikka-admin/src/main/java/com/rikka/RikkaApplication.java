package com.rikka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author rikka
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RikkaApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RikkaApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  六花启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " ███████   ██ ██     ██              \n" +
                "░██░░░░██ ░░ ░██    ░██              \n" +
                "░██   ░██  ██░██  ██░██  ██  ██████  \n" +
                "░███████  ░██░██ ██ ░██ ██  ░░░░░░██ \n" +
                "░██░░░██  ░██░████  ░████    ███████ \n" +
                "░██  ░░██ ░██░██░██ ░██░██  ██░░░░██ \n" +
                "░██   ░░██░██░██░░██░██░░██░░████████\n" +
                "░░     ░░ ░░ ░░  ░░ ░░  ░░  ░░░░░░░░ \n");
    }
}
