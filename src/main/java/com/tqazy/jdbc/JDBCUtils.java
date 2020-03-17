package com.tqazy.jdbc;

import com.tqazy.utils.PropertiesUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 散场前的温柔
 */
public class JDBCUtils {

    private static Connection con;
    private static Statement sm;
    private static ResultSet rs;

    private static Map<String, String>  map;

    /**
     * 获取数据库连接信息
     */
    private static void getProperties(String path){
        List<String> list = new ArrayList<String>();
        list.add("driver");
        list.add("url");
        list.add("username");
        list.add("password");
        map = PropertiesUtils.readProperties(path, list);
    }

    /**
     * 获取数据库连接
     */
    public static void getConnection(String path){
        try {
            if(map == null){
                getProperties(path);
            }
            Class.forName(map.get("driver"));
            con = DriverManager.getConnection(map.get("url"), map.get("username"), map.get("password"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行新增/修改/删除操作的方法
     * @param sql
     * @param path
     * @return
     */
    public static int update(String sql, String path){
        int num = 0;
        getConnection(path);
        // 如果创建连接失败，返回0行
        if(con == null){
            System.out.println("创建数据库连接失败");
            return 0;
        }
        try {
            // 通过connection的createStatement()方法获取statement链接
            sm = con.createStatement();
            // 通过statement的executeUpdate()方法执行SQL语句
            num =  sm.executeUpdate(sql);
        } catch (SQLException e) {
            // 从连接中获取Statement异常
            e.printStackTrace();
        } finally {
            // 关闭statement和connection连接
            close();
        }
        return num;
    }

    /**
     * 执行查询操作的方法
     * @param sql
     * @param path
     * @return
     */
    public static ResultSet select(String sql, String path){
        getConnection(path);
        // 如果创建连接失败，返回0行
        if(con == null){
            System.out.println("创建数据库连接失败");
            return null;
        }
        try {
            // 通过connection的createStatement()方法获取statement链接
            sm = con.createStatement();
            // 通过statement的executeQuery(sql)方法获取resultSet的实例
            rs = sm.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 关闭数据库相关连接，释放数据库资源
     */
    public static void close(){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(sm != null){
            try {
                sm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
