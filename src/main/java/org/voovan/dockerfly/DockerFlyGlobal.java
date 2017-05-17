package org.voovan.dockerfly;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;
import org.voovan.db.JdbcOperate;
import org.voovan.dockerfly.db.DataBaseUtils;

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
public class DockerFlyGlobal {


    public static void main(String[] args) throws Exception {

        DataSource dataSource = DataBaseUtils.getDataSource();

        String sql = "";
        JdbcOperate jOperate = DataBaseUtils.getOperator();
        DataBaseUtils.initDataBase();
    }
}
