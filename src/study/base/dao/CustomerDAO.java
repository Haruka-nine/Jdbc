package study.base.dao;

import com.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface CustomerDAO {
    /**
     * 将cust对象添加到数据库中
     * @param connection
     * @param customer
     */
    void insert(Connection connection, Customer customer);

    /**
     * 针对指定的id，删除表中的一条记录
     * @param connection
     * @param id
     */
    void deleteById(Connection connection,int id);

    /**
     * 针对于内存中的customer对象，去修改数据表中指定的记录
     * @param connection
     * @param customer
     */
    void update(Connection connection,Customer customer);

    /**
     * 针对指定的id查询得到对应的Customer对象
     * @param connection
     * @param id
     */
    Customer getCustomerById(Connection connection,int id);

    /**
     * 查询表中所有记录构成的集合
     * @param connection
     * @return
     */
    List<Customer> GetAll(Connection connection);

    /**
     * 返回数据表中数据的条目数
     * @param connection
     * @return
     */
    Long getCount(Connection connection);

    Date getMaxBirth(Connection connection);




}
