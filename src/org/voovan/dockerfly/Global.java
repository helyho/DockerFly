package org.voovan.dockerfly;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.voovan.db.JdbcOperate;
import org.voovan.tools.TEnv;
import org.voovan.tools.TObject;
import org.voovan.tools.TProperties;
import org.voovan.tools.log.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 类文字命名
 *
 * @author helyho
 *         <p>
 *         DockerFly Framework.
 *         WebSite: https://github.com/helyho/DockerFly
 *         Licence: Apache v2 License
 */
public class Global {
    public static void main(String[] args) throws Exception {



        DruidDataSource dataSource = null;
        try {
            String druidpath = TEnv.getSystemPath("conf" + File.separator + "datasource.properties");
            Properties druidProperites = TProperties.getProperties(new File(druidpath));
            dataSource = (DruidDataSource) TObject.cast(DruidDataSourceFactory.createDataSource(druidProperites));
            dataSource.init();
            Logger.info("Database connection pool init finished");
        } catch (Exception e) {
            Logger.error(e);
        }


        String sql = "";
        JdbcOperate jOperate = new JdbcOperate(dataSource);
        jOperate.update("drop table if exists person1");
        jOperate.update("create table person1 (id int, name varchar(50))");
        jOperate.update("drop table if exists person2");
        jOperate.update("create table person2 (id int, name varchar(50))");
        jOperate.update("drop table if exists person3");
        jOperate.update("create table person3 (id int, name varchar(50))");
        jOperate.update("drop table if exists person4");
        jOperate.update("create table person4 (id int, name varchar(50))");

        JdbcOperate jOperate1 = new JdbcOperate(dataSource);
        Thread t = new Thread(()->{
            try {
                for(int i=0;i<10;i++) {
                    jOperate1.update("insert into person1 values("+i+",'ZhangSan');");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        t.start();

        JdbcOperate jOperate2 = new JdbcOperate(dataSource);
        Thread t1 = new Thread(()->{
            try {
                for(int i=0;i<10;i++) {
                    jOperate2.update("insert into person2 values("+i+",'ZhangSan');");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        t1.start();

        JdbcOperate jOperate3 = new JdbcOperate(dataSource);
        Thread t2 = new Thread(()->{
            try {
                for(int i=0;i<10;i++) {
                    jOperate3.update("insert into person3 values("+i+",'ZhangSan');");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        t2.start();

        JdbcOperate jOperate4 = new JdbcOperate(dataSource);
        Thread t3 = new Thread(()->{
            try {
                for(int i=0;i<10;i++) {
                    jOperate4.update("insert into person4 values("+i+",'ZhangSan');");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        t3.start();
    }
}
