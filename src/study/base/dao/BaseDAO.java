package study.base.dao;

import study.base.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *封装了对于数据表的通用的操作
 */
public abstract class BaseDAO {
    /**
     * 通用的增删改--考虑事务
     * @param connection 数据库连接
     * @param sql  sql语句
     * @param args  sql填充数据
     * @return   返回更改了几条语句
     */
    public int update(Connection connection, String sql, Object ...args)
    {
        PreparedStatement ps = null;
        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            for (int i = 0;i<args.length;i++)
            {
                ps.setObject(i+1,args[i]);
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null,ps);
        }
        return 0;
    }


    /**
     * 通用多条查询，返回多条语句List--考虑事务
     * @param connection 数据库连接
     * @param clazz  对象类的反射
     * @param sql   sql语句
     * @param args  sql填充数据
     * @param <T>  对象类
     * @return    返回多个对象的List数组
     */
    public <T> List<T> getForList(Connection connection, Class<T> clazz, String sql, Object ...args)
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0;i<args.length;i++)
            {
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = metaData.getColumnCount();
            ArrayList<T> ts = new ArrayList<>();
            while(resultSet.next())
            {
                T t = clazz.getDeclaredConstructor().newInstance();
                //处理结果集一行数据中的每一个列
                for (int i=0;i<columnCount;i++)
                {
                    Object value = resultSet.getObject(i + 1);
                    //获取每个列的列名
                    /*String columnName = metaData.getColumnName(i + 1); 通过这个方法是不能获取别名的*/
                    String columnLabel = metaData.getColumnLabel(i + 1);//推荐使用这个，可以获取返回的列名
                    //要给customer某个属性，赋值为value;
                    Field declaredField = clazz.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t,value);
                }
                ts.add(t);
            }
            return ts;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     *通用的查询--返回一条语句---考虑事务
     * @param connection 数据库连接
     * @param clazz  对象类的反射
     * @param sql   sql语句
     * @param args  sql填充数据
     * @param <T>  对象类
     * @return    返回一个对象
     */
    public <T> T getInstance(Connection connection, Class<T> clazz, String sql, Object ...args)
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0;i<args.length;i++)
            {
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = metaData.getColumnCount();
            if(resultSet.next())
            {
                T t = clazz.getDeclaredConstructor().newInstance();
                //处理结果集一行数据中的每一个列
                for (int i=0;i<columnCount;i++)
                {
                    Object value = resultSet.getObject(i + 1);
                    //获取每个列的列名
                    /*String columnName = metaData.getColumnName(i + 1); 通过这个方法是不能获取别名的*/
                    String columnLabel = metaData.getColumnLabel(i + 1);//推荐使用这个，可以获取返回的列名
                    //要给customer某个属性，赋值为value;
                    Field declaredField = clazz.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t,value);
                }
                return t;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null,preparedStatement,resultSet);
        }
        return null;
    }

    //用于查询特殊值的方法
    public <E> E getValue(Connection connection,String sql,Object ...args)
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0;i<args.length;i++)
            {
                preparedStatement.setObject(i+1,args[i]);
            }

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return (E) resultSet.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(null,preparedStatement,resultSet);
        }
        return null;

    }


}
