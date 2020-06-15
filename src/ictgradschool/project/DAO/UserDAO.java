package ictgradschool.project.DAO;

import ictgradschool.project.model.User;
import ictgradschool.project.model.UserData;
import ictgradschool.project.util.DBConnectionUtils;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static UserData getUserDataFromUserName(String userName) throws IOException, SQLException {
        try (Connection connection = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE userName=?;");) {
                ps.setString(1, userName);
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (!resultSet.next())
                        return null;
                    return new UserData(
                            resultSet.getString("userName"),
                            resultSet.getString("passwordHash"),
                            resultSet.getString("salt"),
                            resultSet.getInt("iteration")
                    );
                }
            }
        }
    }

    public static int insertUser(UserData user) throws IOException, SQLException {
        String defaultAvatarName = "default/guest.png";
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO user VALUES (NULL, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.getUserName());
                ps.setString(2, defaultAvatarName);
                ps.setString(3, user.getPasswordHash());
                ps.setString(4, user.getSalt());
                ps.setInt(5, user.getIteration());

                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    rs.next();
                    return rs.getInt(1);
                }
            }
        }
    }

    public static List<User> getAllUsers() throws IOException, SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (Statement statement = conn.createStatement()) {
                try (ResultSet rs = statement.executeQuery("SELECT * FROM user")) {
                    while (rs.next()) {
                        users.add(new User(
                                rs.getInt("id"),
                                rs.getString("userName"),
                                rs.getString("nickname"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getObject("dateOfBirth", LocalDate.class),
                                rs.getString("email"),
                                rs.getString("signature"),
                                rs.getString("description"),
                                rs.getString("avatar")
                        ));
                    }
                }
            }
        }
        return users;
    }

    public static User getUserFromId(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM user WHERE id = ?")) {
            ps.setInt(1, id);
            return assembleUser(ps);
        }
    }

    public static User getUserFromCommentId(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT u.id as id, c.id as commentId, userName, nickname, firstName, lastName, dateOfBirth, email, signature, description, avatar, passwordHash, salt, iteration \n" +
                        " FROM user u join comment c on u.id = c.user WHERE c.id = ?")) {
            ps.setInt(1, id);
            return assembleUser(ps);
        }
    }

    private static User assembleUser(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return new User(
                        rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getString("nickname"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getObject("dateOfBirth", LocalDate.class),
                        rs.getString("email"),
                        rs.getString("signature"),
                        rs.getString("description"),
                        rs.getString("avatar")
                );
            else return null;
        }
    }

    public static User getUserFromId(int id) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
           return getUserFromId(conn, id);
        }
    }

    public static User getUserFromUserName(String userName) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM user WHERE userName = ?")) {
                ps.setString(1, userName);
                return assembleUser(ps);
            }
        }
    }

    public static List<User> getUsersByFollowee(int followeeId) {
        return null;
    }

    public static List<User> getUsersByFollower(int followerId) {
        return null;
    }

    public static void saveProfile(User user) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE user SET userName = ?, nickname = ?, firstName = ?, " +
                            "lastName = ?, email = ?, dateOfBirth = ?, signature = ? , description = ?, avatar = ? " +
                            "WHERE id = ? ")) {
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getNickname());
                ps.setString(3, user.getFirstName());
                ps.setString(4, user.getLastName());
                ps.setString(5, user.getEmail());
                ps.setDate(6, Date.valueOf(user.getDateOfBirth()));
                ps.setString(7, user.getSignature());
                ps.setString(8, user.getDescription());
                ps.setString(9, user.getAvatar());
                ps.setInt(10, user.getId());

                ps.executeUpdate();
            }
        }
    }

    public static boolean deleteUserByUserName(String userName) throws IOException, SQLException{
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM user WHERE userName = ?")) {
                ps.setString(1, userName);
                return ps.execute();
            }
        }
    }

}
