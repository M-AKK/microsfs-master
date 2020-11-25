package net.wenz.service.fs.model.dao.typehandler;

import net.wenz.service.fs.constant.FileType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(value = FileType.class)
public class FileTypeHandler implements TypeHandler {

    /*
     * (non-Javadoc)
     *
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet,
     * java.lang.String)
     */
    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        int columnValue = rs.getInt(columnName);
//        if ( columnValue == 0 )
//            return null;
        return FileType.getFileType(columnValue);
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int columnValue = cs.getInt(columnIndex);
//        if ( columnValue == 0 )
//            return null;
        return FileType.getFileType(columnValue);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement,
     * int)
     */
    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        int columnValue = rs.getInt(columnIndex);
//        if ( columnValue == 0 )
//            return null;
        return FileType.getFileType(columnValue);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.ibatis.type.TypeHandler#setParameter(java.sql.PreparedStatement,
     * int, java.lang.Object, org.apache.ibatis.type.JdbcType)
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null)
            ps.setInt(i, 0);
        else {
            FileType value = (FileType) parameter;
            ps.setInt(i, value.getValue());
        }
    }

}
