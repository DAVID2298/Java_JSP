package it.contrader.dao;

import it.contrader.model.UserRegistry;
import it.contrader.utils.ConnectionSingleton;
import it.contrader.utils.UserSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRegistryDAO implements DAO<UserRegistry> {

    private final String QUERY_ALL= "SELECT * FROM user_anagrafico";
    private final String QUERY_CREATE= "INSERT INTO user_anagrafico (nome, cognome, indirizzo, data_di_nascita,user_id) VALUES (?,?,?,?,?)";
    private final String QUERY_READ= "SELECT * FROM user_anagrafico WHERE user_id=?";
    private final String QUERY_UPDATE= "UPDATE user_anagrafico SET nome=?, cognome=?, indirizzo=?, data_di_nascita=? WHERE User_id=?";
    private final String QUERY_DELETE= "DELETE from user WHERE id=?";
    public UserRegistryDAO() {
    }

    @Override
    public List<UserRegistry> getAll() {
        List<UserRegistry> userRegistries=new ArrayList<>();


        Connection connection = ConnectionSingleton.getInstance();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_ALL);
            UserRegistry userRegistry;
            while (resultSet.next()) {
                int id = resultSet.getInt("id_anagrafica");
                String name = resultSet.getString("nome");
                String surname = resultSet.getString("cognome");
                String address = resultSet.getString("indirizzo");
                String date_birthday=resultSet.getString("data_di_nascita");
                int user_id = resultSet.getInt("user_id");

                userRegistry= new UserRegistry(id,name,surname,address,date_birthday,user_id);
                userRegistry.setUserId(user_id);
                userRegistry.setId(id);
                userRegistries.add(userRegistry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRegistries;
    }

    @Override
    public UserRegistry read(int uRegistry) {
        Connection connection= ConnectionSingleton.getInstance();
        try{

            PreparedStatement preparedStatement= connection.prepareStatement(QUERY_READ);
            preparedStatement.setLong(1,uRegistry);
            ResultSet resultSet= preparedStatement.executeQuery();
            resultSet.next();
            String name, surname, address,date_of_birth;
            int userId;
            name= resultSet.getString("nome");
            surname= resultSet.getString("cognome");
            address= resultSet.getString("indirizzo");
            date_of_birth=resultSet.getString("Data_Di_Nascita");
            userId=resultSet.getInt("User_Id");
            UserRegistry userRegistry= new UserRegistry(name,surname,address,date_of_birth);
            userRegistry.setId(resultSet.getInt("id_anagrafica"));
            userRegistry.setUserId(userId);
            return userRegistry;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean insert(UserRegistry userRegistry) {
        Connection connection= ConnectionSingleton.getInstance();
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(QUERY_CREATE);
            preparedStatement.setString(1, userRegistry.getName());
            preparedStatement.setString(2, userRegistry.getSurname());
            preparedStatement.setString(3, userRegistry.getAddress());
            preparedStatement.setString(4, userRegistry.getBirthDate());
            preparedStatement.setInt(5, userRegistry.getUserId());
            preparedStatement.execute();
            return true;

        }catch(SQLException e){
            return false;
        }
    }

    @Override
    public boolean update(UserRegistry userRegistryToUpdate) {
        Connection connection= ConnectionSingleton.getInstance();
        if(userRegistryToUpdate.getId()==0){
            return false;
        }
        UserRegistry userRegistryRead = read(userRegistryToUpdate.getUserId());
        if (!userRegistryRead.equals(userRegistryToUpdate)){
            try{
                if (userRegistryToUpdate.getName()==null||userRegistryToUpdate.getName().equals("")){
                    userRegistryToUpdate.setName(userRegistryRead.getName());
                }
                if (userRegistryToUpdate.getSurname()==null||userRegistryToUpdate.getSurname().equals("")){
                    userRegistryRead.setSurname(userRegistryRead.getSurname());
                }

                if (userRegistryToUpdate.getAddress()==null||userRegistryToUpdate.getAddress().equals("")){
                    userRegistryToUpdate.setAddress(userRegistryRead.getAddress());
                }
                if (userRegistryToUpdate.getBirthDate()==null||userRegistryToUpdate.getBirthDate().equals("")){
                    userRegistryToUpdate.setBirthDate(userRegistryRead.getBirthDate());
                }


                PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);

                preparedStatement.setString(1, userRegistryToUpdate.getName());
                preparedStatement.setString(2, userRegistryToUpdate.getSurname());
                preparedStatement.setString(3, userRegistryToUpdate.getAddress());
                preparedStatement.setString(4, userRegistryToUpdate.getBirthDate());
                preparedStatement.setInt(5,userRegistryToUpdate.getUserId());
                int a= preparedStatement.executeUpdate();
                if (a > 0)
                    return true;
                else
                    return false;

            }catch (SQLException e){
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionSingleton.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            int n = preparedStatement.executeUpdate();
            if (n != 0)
                return true;

        } catch (SQLException e) {
        }
        return false;
    }
}
