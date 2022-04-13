package study.optimization;

import org.junit.Test;
import study.base.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 使用PreparedStatement实现批量数据的操作
 * update、delete本身就具有批量操作的效果
 * 此时的批量操作，主要指批量插入。使用PreparedStatement如何实现高效的批量插入？
 *
 *
 */
public class InsertTest {
    /**
     * 批量插入
     */
    @Test
    public void testInsert()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql= "insert into goods(NAME)values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i=1;i<=20000;i++)
            {
                preparedStatement.setObject(1,"name_"+i);
                preparedStatement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,preparedStatement);
        }

    }
    /**
     * 批量插入优化
     * mysql服务器是默认关闭批处理的，我们需要一个参数，然mysql开启批处理的支持
     * &rewriteBatchedStatements=true
     * 如果首位就是?代替&
     */
    @Test
    public void testInsert2()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            String sql= "insert into goods(NAME)values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i=1;i<=20000;i++)
            {
                preparedStatement.setObject(1,"name_"+i);
                //1,'攒'sql
                preparedStatement.addBatch();
                if (i%500==0)
                {
                    //2,执行batch
                    preparedStatement.executeBatch();
                    //3，清空batch
                    preparedStatement.clearBatch();
                }

            }
            //long end = System.currentTimeMillis();
            //System.out.println(end-start);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,preparedStatement);
        }

    }
    /**
     * 设置不允许自动提交，因为每次批处理都会提交一次，可以一起提交(但个人认为可能会有内存问题)
     */
    @Test
    public void testInsert3()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            String sql= "insert into goods(NAME)values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i=1;i<=20000;i++)
            {
                preparedStatement.setObject(1,"name_"+i);
                //1,'攒'sql
                preparedStatement.addBatch();
                if (i%500==0)
                {
                    //2,执行batch
                    preparedStatement.executeBatch();
                    //3，清空batch
                    preparedStatement.clearBatch();
                }

            }
            //提交数据
            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,preparedStatement);
        }

    }



}
