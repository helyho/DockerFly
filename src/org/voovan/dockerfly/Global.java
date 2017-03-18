package org.voovan.dockerfly;

import org.h2.jdbcx.JdbcConnectionPool;
import org.voovan.db.JdbcOperate;
import javax.sql.DataSource;
import java.sql.SQLException;

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
//    private static String dbURL = "jdbc:h2:tcp://localhost:9093/./db/dockerfly;MODE=MySQL";
    private static String dbURL = "jdbc:h2:./db/dockerfly;AUTO_SERVER=TRUE;MODE=MySQL";
    private static String dbUser = "sa";
    private static String dbPassword = "";
    private static DataSource dataSource;

    public static synchronized DataSource getDataSource(){
        if(dataSource == null){
            dataSource = JdbcConnectionPool.create(dbURL, dbUser, dbPassword);
        }

        return dataSource;
    }


    public static void main(String[] args) throws Exception {

        DataSource dataSource = getDataSource();

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
                for(int i=0;i<100;i++) {
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
                for(int i=0;i<100;i++) {
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
                for(int i=0;i<100;i++) {
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
                for(int i=0;i<100;i++) {
                    jOperate4.update("insert into person4 values("+i+",'ZhangSan');");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        t3.start();
    }
}
