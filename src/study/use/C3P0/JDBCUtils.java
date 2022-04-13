package study.use.C3P0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {
    /**
     * 关闭连接和Statement操作
     * @param conn  数据库连接
     * @param ps    preparedStatement的参数
     */
    public static void closeResource(Connection conn, Statement ps)
    {
        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
    }

    /**
     * 关闭连接和Statement还有resultSet返回
     * @param conn  数据库连接
     * @param ps    preparedStatement的参数
     * @param resultSet resultSet返回参数
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet resultSet)
    {
        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(resultSet);

    }

    static ComboPooledDataSource cpds =new ComboPooledDataSource("helloc3p0");

    /**
     * 使用C3P0数据库连接池连接数据库，需要写配置文件，且命名为c3p0-config.xml
     * @return  数据库连接
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {

        Connection connection = cpds.getConnection();
        return connection;
    }

}
