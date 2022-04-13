package study.use.C3P0;

import com.bean.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * dbutils是一个JDBC工具类库，封装了针对数据库的增删改查操作
 */
public class QueryRunnerTest {
    //测试插入
    @Test
    public void testInsert() {

        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql="insert into customers(name, email, birth)value (?,?,?)";
            int insertCount = runner.update(connection, sql, "蔡徐坤", "caixukun@126.com", "1997-06-08");
            System.out.println(insertCount+"条数据");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }

    }
    //测试查询
    @Test
    public  void  testQuery1() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id=?";
            BeanHandler<Customer> handler = new BeanHandler<Customer>(Customer.class);
            Customer customer = runner.query(connection, sql, handler, 21);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }


    }
    //查询多条
    @Test
    public  void  testQuery2(){
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id<?";
            BeanListHandler<Customer> handler = new BeanListHandler<Customer>(Customer.class);
            List<Customer> customers = runner.query(connection, sql, handler, 21);
            customers.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

    //MapHandler
    @Test
    public  void  testQuery3(){
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id=?";
            MapHandler handler = new MapHandler();
            Map<String, Object> map = runner.query(connection, sql, handler, 21);
            System.out.println(map);
            System.out.println(map.get("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

    //MapListHandler
    @Test
    public  void  testQuery4(){
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id<?";
            MapListHandler handler = new MapListHandler();
            List<Map<String, Object>> maps = runner.query(connection, sql, handler, 21);
            maps.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

    //用于查询特殊值
    @Test
    public  void  testQuery5(){
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select max(birth) from customers";
            ScalarHandler Handler = new ScalarHandler();
            Date count = (Date) runner.query(connection, sql, Handler);
            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }
    @Test
    public  void  testQuery6(){
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id=?";

            ResultSetHandler<Customer> handler =new  ResultSetHandler<Customer>()
            {

                @Override
                public Customer handle(ResultSet resultSet) throws SQLException {
                    if (resultSet.next())
                    {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");
                        Date birth = resultSet.getDate("birth");
                        Customer customer = new Customer(id,name,email,birth);
                        return customer;
                    }
                    return null;
                }


            };
            Customer customer = runner.query(connection, sql, handler,21);
            System.out.println(customer);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }




}
