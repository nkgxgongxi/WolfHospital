import java.sql.*;

public interface Operable {
	public void update(Database db) throws SQLException;
	public void insert(Database db) throws SQLException;
//	public void select(Database db) throws SQLException;
}
