package cn.edu.sdjzu.xg.kcsj;

import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public class CommodityDao {
    public static CommodityDao commodityDao = new CommodityDao();
    private CommodityDao(){}
    public static CommodityDao getInstance(){return commodityDao;}
    public static Collection<Commodity>commodities;
    public Collection<Commodity> findAll() throws SQLException {
        commodities = new TreeSet<>();
        //获取数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //执行sql查询语句并获得结果集对象
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from commodity");
        while (resultSet.next()){
            commodities.add(new Commodity(resultSet.getInt("id"),resultSet.getString("name"),
                    resultSet.getString("description"),resultSet.getDouble("price"),resultSet.getString("type")));
        }
        JdbcHelper.close(stmt,connection);
        return CommodityDao.commodities;
    }

    public Commodity find(Integer id) throws SQLException {
        Commodity desiredCommodity = null;
        Connection connection = JdbcHelper.getConn();
        //根据连接对象准备语句对象，如果sql语句为多行，注意语句不同部分之间要有空格
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM commodity WHERE id = ?");
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Commodity对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            desiredCommodity = new Commodity(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("description"),resultSet.getDouble("price"),resultSet.getString("type"));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return desiredCommodity;
    }

    public boolean update(Commodity commodity) throws SQLException {
        //获取数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //根据连接对象准备语句对象，如果sql语句为多行，注意语句不同部分之间要有空格
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE commodity SET name = ? ,description = ?,price = ?,type = ?WHERE id = ?");
        //为预编译语句赋值
        preparedStatement.setString(1,commodity.getName());
        preparedStatement.setString(2,commodity.getDescription());
        preparedStatement.setDouble(3,commodity.getPrice());
        preparedStatement.setString(4,commodity.getType());
        preparedStatement.setInt(5,commodity.getId());
        int affectedRowNum = preparedStatement.executeUpdate();
        //执行预编译语句，用其返回值、影响的行为数为赋值affectedRowNum
        JdbcHelper.close(preparedStatement,connection);
        return affectedRowNum>0;
    }

    public boolean add(Commodity commodity) throws SQLException {
        //获取数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //根据连接对象准备语句对象，如果sql语句为多行，注意语句不同部分之间要有空格
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO commodity (name,description,price,type) VALUES (?,?,?,?)");
        preparedStatement.setString(1,commodity.getName());
        preparedStatement.setString(2,commodity.getDescription());
        preparedStatement.setDouble(3,commodity.getPrice());
        preparedStatement.setString(4,commodity.getType());
        int affectedRowNum = preparedStatement.executeUpdate();
        //执行预编译语句，用其返回值、影响的行为数为赋值affectedRowNum
        JdbcHelper.close(preparedStatement,connection);
        return affectedRowNum>0;
    }

    public boolean delete(Integer id) throws SQLException{
        //获取数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句
        String deleteCommodity_sql = "DELETE FROM commodity WHERE id =?";
        //根据连接对象准备语句对象，如果sql语句为多行，注意语句不同部分之间要有空格
        PreparedStatement preparedStatement = connection.prepareStatement(deleteCommodity_sql);
        preparedStatement.setInt(1, id);
        //执行预编译对象的executeUpdate方法，获取删除的记录行数
        int affectedRowNum = preparedStatement.executeUpdate();
        JdbcHelper.close(preparedStatement,connection);
        return affectedRowNum>0;
    }

    public boolean delete(Commodity commodity) throws SQLException {
        return delete(commodity.getId());
    }

    public static void main(String[] args) throws SQLException{
        //找到id为2的commodity1对象
        Commodity commodity1 = CommodityDao.getInstance().find(606);
        System.out.println(commodity1);
        commodity1.setDescription("博士");
        CommodityDao.getInstance().update(commodity1);
        //找到id为2的commodity2对象
        Commodity commodity2 = CommodityDao.getInstance().find(606);
        System.out.println(commodity2.getDescription());
        System.out.println(commodity2);
        //Commodity commodity = new Commodity("博士","07","");
        //System.out.println(CommodityDao.getInstance().add(commodity));
        //System.out.println(commodity2);
    }



}
