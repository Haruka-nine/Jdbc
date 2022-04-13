package study.use.DBCP;

import org.apache.commons.dbcp.BasicDataSourceFactory;
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
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            source = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用C3P0数据库连接池连接数据库，需要写配置文件dbcp.properties
     * @return 数据库连接
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {

        Connection connection = source.getConnection();
        return connection;

    }

}
