package study.base.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC建立连接步骤
 * 1,设置url，user，password,driverClass
 * 2,加载driverClass(mysql会自动加载)
 * 3，DriverManager.getConnection(url, user, password);获取连接
 */
public class ConnectionUtils {
    //方式1
    @Test
    public void testConnection1() throws SQLException
    {
        Driver diver = null;
        diver = new com.mysql.cj.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        //将用户名和密码封装在Properties中
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","password");

        Connection conn = null;

        conn = diver.connect(url, info);

        System.out.println(conn);
    }
    //方式2
    @Test
    public void testConnection2() throws Exception
    {
        //1.获取Driver实现类对象：使用反射
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        //将用户名和密码封装在Properties中
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","password");

        Connection conn = null;

        conn = driver.connect(url, info);

        System.out.println(conn);
    }
    //方式3：使用DriverManager代替Driver
    @Test
    public void testConnection3() throws Exception {
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user ="root";
        String password = "password";
        //注册驱动
        DriverManager.registerDriver(driver);
        //获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
    //方式4：
    @Test
    public void testConnection4() throws Exception
    {
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user ="root";
        String password = "password";
        Class.forName("com.mysql.cj.jdbc.Driver");//对于mysql这个可以省略，因为mysql的驱动在我们导入的时候已经帮我们加载了Driver
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
    //方式5：对配置文件进行读取
    @Test
    public void getConnection5() throws Exception
    {
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
        System.out.println(conn);
    }
}
