package org.voovan.dockerfly.db;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;
import org.voovan.db.JdbcOperate;
import org.voovan.dockerfly.DataOperate.OperUser;
import org.voovan.dockerfly.model.Host;
import org.voovan.dockerfly.model.User;
import org.voovan.tools.TFile;
import org.voovan.tools.log.Logger;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

/**
 * 类文字命名
 *
 * @author: helyho
 * DockerFly Framework.
 * WebSite: https://github.com/helyho/DockerFly
 * Licence: Apache v2 License
 */
public class DataBaseUtils {
    private static String dbURL = "jdbc:sqlite:./db/dockerfly";
    private static SQLiteDataSource dataSource;

    public static synchronized DataSource getDataSource(){
        if(dataSource == null){
            SQLiteConfig sqLiteConfig = new SQLiteConfig();
            dataSource = new SQLiteDataSource(sqLiteConfig);
            dataSource.setDatabaseName("DockerFly");
            dataSource.setUrl(dbURL);
        }

        return dataSource;
    }

    public static JdbcOperate getOperator(){
        return new JdbcOperate(getDataSource());
    }

    public static void initDataBase(){
        int count = 0;
        JdbcOperate jdbcOperate = getOperator();
        try {

            TFile.mkdir(TFile.getContextPath()+ File.separator+"db");
            count = jdbcOperate.queryObject("select count(*) count from sqlite_master where tbl_name = 'DF_USER'", Integer.class);

            if (count==0){
                Logger.simple("Database not init , initalize now.");
                //创建表
                jdbcOperate.update("CREATE TABLE \"DF_USER\" (\n" +
                                    "\t \"UserId\" integer NOT NULL DEFAULT 0 PRIMARY KEY AUTOINCREMENT,\n" +
                                    "\t \"UserName\" varchar(50,0) NOT NULL,\n" +
                                    "\t \"Password\" varchar(50,0) NOT NULL,\n" +
                                    "\t \"Role\" varchar(50,0) NOT NULL,\n" +
                                    "\t \"DefaultHost\" integer,\n" +
                                    "\t \"Hosts\" varchar(5000,0),\n" +
                                    "\t \"Registrys\" varchar(5000,0),\n" +
                                    "\t \"CreateDate\" varchar(50,0) NOT NULL DEFAULT (datetime('now', 'localtime')||'.000')\n" +
                                    ")");

                jdbcOperate.update("CREATE UNIQUE INDEX \"uk_username\" ON DF_USER (\"UserName\" ASC)");
                //增加默认管理用户
                User adminUser = new User();
                adminUser.setUserName("admin");
                adminUser.setPassword("1234");
                adminUser.setDefaultHost(0);
                adminUser.setRole("Admin");
                adminUser.getHosts().add(new Host("Default", "127.0.0.1", 2735));
                OperUser.addUser(adminUser);
            }

        } catch (SQLException e) {
            Logger.error("DataBaseUtils.initDataBase create [DF_USER] table error: ");
        } catch (ReflectiveOperationException e) {
            Logger.error("DataBaseUtils.initDataBase add admin user error: ");
        }
    }

}
