package study.base.dao;

import com.bean.Customer;
import com.connection2.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomerDAOImplTest {

    private CustomerDAOImpl dao = new CustomerDAOImpl();
    @Test
    public void insert() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customer = new Customer(1, "于小飞", "xiaofei@126.com", new Date(43534646435L));
            dao.insert(connection,customer);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }

    }

    @Test
    public void deleteById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            dao.deleteById(connection,13);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

    @Test
    public void update() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customer = new Customer(18,"贝多芬","beiduofen@126.com",new Date(453465656L));
            dao.update(connection,customer);
            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

    @Test
    public void getCustomerById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customer = dao.getCustomerById(connection,19);

            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

    @Test
    public void GetAll() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection1();
            List<Customer> customers = dao.GetAll(connection);
            customers.forEach(System.out::println);

            System.out.println("查找成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

    @Test
    public void getCount() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection2();
            Long count = dao.getCount(connection);
            System.out.println("表中的数据为："+count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

    @Test
    public void getMaxBirth() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection3();
            Date maxBirth = dao.getMaxBirth(connection);
            System.out.println("最大生日："+maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,null);
        }
    }
}