package study.use.Druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

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

    private static DataSource source;
    static {
        try {
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            source = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()throws Exception
    {
        Connection connection = source.getConnection();
        return connection;
    }
}
