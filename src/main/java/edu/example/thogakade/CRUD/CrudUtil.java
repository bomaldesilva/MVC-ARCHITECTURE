package edu.example.thogakade.CRUD;

import edu.example.thogakade.DB.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T>T execute(String SQL, Object... obj) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(SQL);
        for(int i=0;i<obj.length;i++){
            pstm.setObject(i+1,obj[i]);
        }
        if(SQL.startsWith("SELECT")|| SQL.startsWith("select")){
            return (T) pstm.executeQuery();
        }
        else {
            int result = pstm.executeUpdate();
            return (T) (Boolean)( result> 0);
        }
    }
}
