package study.base;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    /**
     * 建立数据库连接
     *
     */
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        //1.读取配置文件中的4个基本信息
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String user =properties.getProperty("user");
        String password =properties.getProperty("password");
        String url =properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        //2.加载驱动
        Class.forName(driverClass);
        //3.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * 关闭连接和Statement操作
     * @param conn  数据库连接
     * @param ps    preparedStatement的参数
     */
    public static void closeResource(Connection conn, Statement ps)
    {
        try {
            if (ps!=null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn!=null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭连接和Statement还有resultSet返回
     * @param conn  数据库连接
     * @param ps    preparedStatement的参数
     * @param resultSet resultSet返回参数
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet resultSet)
    {
        try {
            if (ps!=null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn!=null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(resultSet!=null)
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
