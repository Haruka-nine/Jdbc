package study.blob;

import study.bean.Customer;
import org.junit.Test;
import study.base.JDBCUtils;

import java.io.*;
import java.sql.*;

public class BlobTest {
    //向数据表customers中插入Blob类型的字段
    @Test
    public void testInsert()throws Exception
    {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into customers(name, email, birth, photo) value (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,"张宇豪");
        preparedStatement.setObject(2,"zhang@qq.com");
        preparedStatement.setObject(3,"1992-09-08");
        FileInputStream fileInputStream = new FileInputStream(new File("src\\CG_210423_9_nine_.jpg"));
        preparedStatement.setBlob(4,fileInputStream);
        preparedStatement.execute();
        JDBCUtils.closeResource(connection,preparedStatement);
        fileInputStream.close();
    }
    //修改数据表中的Blob类型的字段
    @Test
    public void testUpdate()throws Exception
    {
        Connection connection = JDBCUtils.getConnection();
        String sql = "update  customers set photo =? where id=20";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        FileInputStream fileInputStream = new FileInputStream(new File("src\\libimge\\_cover.jpg"));
        preparedStatement.setBlob(1,fileInputStream);
        preparedStatement.execute();
        JDBCUtils.closeResource(connection,preparedStatement);
        fileInputStream.close();
    }
    //查询数据表中customers中Blob类型的字段
    @Test
    public void testQuery() throws Exception
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        InputStream binaryStream = null;
        FileOutputStream fileOutputStream= null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,20);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                Customer customer = new Customer(id,name,email,date);
                //将Blob类型的字段下载下来，
                Blob blob = resultSet.getBlob(5);
                binaryStream = blob.getBinaryStream();
                fileOutputStream = new FileOutputStream("src\\tian.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len=binaryStream.read(buffer))!=-1)
                {
                    fileOutputStream.write(buffer,0,len);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (binaryStream!=null)
                binaryStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream!=null)
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }


    }

}
