package programming3.chatsys.data;

import java.sql.*;
import java.util.List;

public class SQLiteDatabase implements Database {
    private Connection connection;

    public SQLiteDatabase(String url) {
        try {
            this.connection = DriverManager.getConnection(url);
            this.createUsersTable();
            this.createMessagesTable();
        } catch(SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    private void createUsersTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS user (\n" +
                "id integer PRIMARY KEY,\n" +
                "username text UNIQUE NOT NULL,\n" +
                "fullname text NOT NULL,\n" +
                "password text NOT NULL,\n" +
                "last_read_id integer DEFAULT 0\n" +
                ");";
        Statement statement = this.connection.createStatement();
        statement.execute(query);
    }

    private void createMessagesTable() {
        // TODO
    }

    @Override
    public boolean register(User user) {
        String query = "INSERT INTO user(username, fullname, password) VALUES(?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(query);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getPassword());
            return statement.executeUpdate() == 1;
        } catch(SQLException e) {
            if (e.getErrorCode() == 19) {
                return false;
            } else {
                throw new DatabaseAccessException(e);
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DatabaseAccessException(e);
                }
            }
        }
    }

    @Override
    public int getNumberUsers() {
        String query = "SELECT COUNT(*) FROM user";
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            statement.execute(query);
            return statement.getResultSet().getInt(1);
        } catch(SQLException e) {
            throw new DatabaseAccessException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DatabaseAccessException(e);
                }
            }
        }
    }

    @Override
    public User getUser(String userName) {
        String query = "SELECT * FROM user WHERE username = ?";
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = this.connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.execute();
            result = statement.getResultSet();
            if (result.next()) {
                return new User(
                        result.getString("username"),
                        result.getString("fullname"),
                        result.getString("password")
                );
            } else {
                throw new IllegalArgumentException(userName + " is not a registered user");
            }
        } catch(SQLException e) {
            throw new DatabaseAccessException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DatabaseAccessException(e);
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    throw new DatabaseAccessException(e);
                }
            }
        }
    }

    @Override
    public boolean authenticate(String userName, String password) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ?";
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = this.connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.execute();
            result = statement.getResultSet();
            int n = result.getInt(1);
            return n == 1;
        } catch(SQLException e) {
            throw new DatabaseAccessException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DatabaseAccessException(e);
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    throw new DatabaseAccessException(e);
                }
            }
        }
    }

    @Override
    public ChatMessage addMessage(String userName, String message) {
        return null;
    }

    @Override
    public int getNumberMessages() {
        return 0;
    }

    @Override
    public List<ChatMessage> getRecentMessages(int n) {
        return null;
    }

    @Override
    public List<ChatMessage> getUnreadMessages(String userName) {
        return null;
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
