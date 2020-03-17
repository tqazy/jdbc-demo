import com.tqazy.jdbc.JDBCUtils;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestJDBC {

    private String path = "database.properties";

    private String selectSQL = "SELECT * FROM user";

    private String insertSQL = "INSERT INTO user (name, password, age, remark) VALUES ('巨山', 'admin', 25, '这是一个强壮的青年')";

    private String updateSQL = "UPDATE user SET age = 28 WHERE name = '巨山'";

    private String deleteSQL = "DELETE FROM user WHERE name = '巨山'";

    @Test
    public void select() {
        try {
            ResultSet rs = JDBCUtils.select(selectSQL, path);
            while (rs.next()) {
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("password"));
                System.out.println(rs.getInt("age"));
                System.out.println(rs.getString("remark"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close();
        }
    }

    @Test
    public void update(){
        int num = JDBCUtils.update(deleteSQL, path);
        System.out.println(num);
        select();
    }


}
