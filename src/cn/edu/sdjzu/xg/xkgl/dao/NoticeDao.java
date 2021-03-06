package cn.edu.sdjzu.xg.xkgl.dao;


import cn.edu.sdjzu.xg.xkgl.domain.Notice;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;

public class NoticeDao {
    //声明数据库的各个对象的引用
    private static Connection conn = null;
    private static Statement statement = null;
    private static ResultSet rs = null;
    private static PreparedStatement pstmt = null;

    //声明userDao对象引用
    private static NoticeDao noticeDao = null;
    //返回本类的惟一对象
    public static NoticeDao getInstance() {
        if(NoticeDao.noticeDao==null){
            NoticeDao.noticeDao = new NoticeDao();
        }
        return NoticeDao.noticeDao;
    }
    /*查找所有通知*/
    public Collection<Notice> findAll() throws SQLException {
        //通知集合
        Collection<Notice> notices = new TreeSet<>();
        //sql语句查询所有的课题
        String selectSql = "SELECT * FROM notice";
        //获取数据库连接对象
        conn = JdbcHelper.getConn();
        //根据连接对象准备语句对象，如果SQL语句为多行，注意语句不同部分之间要有空格
        statement = conn.createStatement();
        //执行查询语句，返回结果集
        rs = statement.executeQuery(selectSql);
        //遍历结果集放到集合中
        while (rs.next()) {
            int id = rs.getInt("id");
            Date issueTime = rs.getDate("issueTime");
            String text = rs.getString("text");
            String title = rs.getString("title");
            int eduAdmin_id = rs.getInt("eduAdmin_id");
            Notice notice = new Notice(id,title,text,issueTime,EduAdminDao.getInstance().find(eduAdmin_id));
            notices.add(notice);
        }
        //关闭资源
        JdbcHelper.close(statement,conn);
        return notices;
    }
    /*通过id查找所有通知*/
    public Notice find(int id) throws SQLException{
        //声明通知引用
        Notice notice = null;
        //获取数据库连接对象
        conn = JdbcHelper.getConn();
        //根据连接对象准备查询语句对象
        pstmt = conn.prepareStatement("SELECT * FROM notice WHERE id=?");
        //对预编译语句对象的参数赋值
        pstmt.setInt(1,id);
        //执行预编译语句，返回结果集
        rs = pstmt.executeQuery();
        //获得结果集，对通知对象属性赋值
        while (rs.next()){
            Date issueTime = rs.getDate("issueTime");
            String text = rs.getString("text");
            String title = rs.getString("title");
            int eduAdmin_id = rs.getInt("eduAdmin_id");
            notice = new Notice(id,title,text,issueTime,EduAdminDao.getInstance().find(eduAdmin_id));
        }
        JdbcHelper.close(pstmt,conn);
        return  notice;
    }
    /*增加一个通知*/
    public boolean add(Notice notice) throws SQLException{
        //sql插入语句
        String addSql="INSERT INTO notice("
                +"issueTime,"
                +"text,"
                +"title,"
                +"eduAdmin_id,"
                +"VALUES (?,?,?,?)";
        //获取数据库连接对象
        conn = JdbcHelper.getConn();
        //根据连接对象准备语句对象，如果SQL语句为多行，注意语句不同部分之间要有空格
        pstmt = conn.prepareStatement("insert into notice " +
                " (issueTime, text, title, eduAdmin_id)" +
                " values(?,?,?,?)");
        //对预编译语句对象的参数赋值
        pstmt.setDate(1, (java.sql.Date) notice.getIssueTime());
        pstmt.setString(2,notice.getText());
        pstmt.setString(3,notice.getTitle());
        pstmt.setInt(4,notice.getEduAdmin().getId());
        //执行插入语句，返回受影响的行数
        int rowAffected = pstmt.executeUpdate();
        //关闭资源
        JdbcHelper.close(pstmt, conn);
        //如果影响的行数大于0，则返回true，否则返回false
        return rowAffected > 0;
    }
    /*更改通知*/
    public boolean update(Notice notice) throws SQLException{
        //sql更新语句
        String updateSql="UPDATE notice SET "
                +"issueTime,"
                +"text,"
                +"title,"
                +"eduAdmin_id,"
                + "where id=?";
        //获取数据库连接对象
        conn = JdbcHelper.getConn();
        //根据连接对象准备语句对象，如果SQL语句为多行，注意语句不同部分之间要有空格
        pstmt = conn.prepareStatement(updateSql);
        //对预编译语句对象的参数赋值
        pstmt.setDate(1,(java.sql.Date) notice.getIssueTime());
        pstmt.setString(2,notice.getText()+"");
        pstmt.setString(3,notice.getTitle()+"");
        pstmt.setInt(4,notice.getEduAdmin().getId());
        pstmt.setString(5,notice.getId()+"");
        //执行更新语句，返回受影响的行数
        int rowAffected = pstmt.executeUpdate();
        //关闭资源
        JdbcHelper.close(pstmt,conn);
        //如果影响的行数大于0，则返回true，否则返回false
        return rowAffected>0;
    }
    /*通过id删除通知*/
    public boolean delete(Integer id)throws SQLException{
        //sql删除语句
        String deleteSql="DELETE FROM notice WHERE id=?";
        //得到数据库连接对象
        conn = JdbcHelper.getConn();
        //根据连接对象准备语句对象，如果SQL语句为多行，注意语句不同部分之间要有空格
        pstmt = conn.prepareStatement(deleteSql);
        //对预编译语句对象的参数赋值
        pstmt.setInt(1, id);
        //执行删除操作，返回受影响的行数
        int rowAffected = pstmt.executeUpdate();
        //关闭资源
        JdbcHelper.close(pstmt,conn);
        //如果影响的行数大于0，则返回true，否则返回false
        return rowAffected>0;
    }
    /*删除通知*/
    public boolean delete(Notice notice)throws SQLException{
        //sql删除语句
        String deleteSql="DELETE FROM notice WHERE id=?";
        //得到数据库连接对象
        conn = JdbcHelper.getConn();
        //根据连接对象准备语句对象，如果SQL语句为多行，注意语句不同部分之间要有空格
        pstmt = conn.prepareStatement(deleteSql);
        //对预编译语句对象的参数赋值
        pstmt.setInt(1, notice.getId());
        //执行删除操作，返回受影响的行数
        int rowAffected = pstmt.executeUpdate();
        //关闭资源
        JdbcHelper.close(pstmt,conn);
        //如果影响的行数大于0，则返回true，否则返回false
        return rowAffected>0;
    }
}
