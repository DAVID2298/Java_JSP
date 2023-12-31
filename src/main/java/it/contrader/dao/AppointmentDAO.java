package it.contrader.dao;

import it.contrader.utils.ConnectionSingleton;
import it.contrader.model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO implements DAO<Appointment>{

    private final String QUERY_ALLBYID = "SELECT * FROM prenotazione WHERE id_user=?";
    private final String QUERY_ALL = "SELECT * FROM prenotazione";
    private final String QUERY_CREATE = "INSERT INTO prenotazione (id_prenotazione, data, orario,costo,accettata, id_user,id_visita)"
            + " VALUES (?,?,?,?,?,?,?)";
    private final String QUERY_READ = "SELECT * FROM prenotazione WHERE id_prenotazione=?";
    private final String QUERY_UPDATE = "UPDATE prenotazione SET data=?," +
            "orario=?,costo=?, accettata=? WHERE id_prenotazione=?";
    private final String QUERY_DELETE = "DELETE FROM prenotazione WHERE id_prenotazione=?";


    public AppointmentDAO() {

    }

    public List<Appointment> getAllByID(int userId) {
        List<Appointment> appointmentList = new ArrayList<>();
        Connection connection = ConnectionSingleton.getInstance();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(QUERY_ALLBYID);
            preparedStatement.setLong(1,userId);
            ResultSet resultSet=preparedStatement.executeQuery();
            Appointment appointment;
            while (resultSet.next()) {
                int id_prenotazione = resultSet.getInt("id_prenotazione");
                String data = resultSet.getString("data");
                String orario = resultSet.getString("orario");
                double costo = resultSet.getDouble("costo");
                boolean accettata = resultSet.getBoolean("accettata");
                int id_visita = resultSet.getInt("id_visita");

                appointment = new Appointment(id_prenotazione, data, orario, costo,accettata, userId, id_visita);
                appointment.setId(id_prenotazione);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    @Override
    public List<Appointment> getAll() {

        List<Appointment> appointmentList = new ArrayList<>();
        Connection connection = ConnectionSingleton.getInstance();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_ALL);
            Appointment appointment;
            while (resultSet.next()) {
                int id = resultSet.getInt("id_prenotazione");
                String data = resultSet.getString("data");
                String orario = resultSet.getString("orario");
                double costo = resultSet.getDouble("costo");
                boolean accettata = resultSet.getBoolean("accettata");
                int userid = resultSet.getInt("id_user");
                int meid = resultSet.getInt("id_visita");
                appointment = new Appointment(data, orario, costo, accettata, userid, meid);
                appointment.setId(id);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }


    public boolean insert(Appointment appointmentToInsert) {
        Connection connection = ConnectionSingleton.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_CREATE);
            preparedStatement.setLong(1, appointmentToInsert.getId());
            preparedStatement.setString(2, appointmentToInsert.getDate());
            preparedStatement.setString(3, appointmentToInsert.getHour());
            preparedStatement.setDouble(4, appointmentToInsert.getCost());
            preparedStatement.setBoolean(5, appointmentToInsert.getDemand());
            preparedStatement.setLong(6, appointmentToInsert.getUser_id());
            preparedStatement.setLong(7, appointmentToInsert.getId_ME());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public Appointment read(int id_prenotazione) {
        Connection connection = ConnectionSingleton.getInstance();
        try {


            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_READ);
            preparedStatement.setInt(1, id_prenotazione);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String data, orario;
            double costo;
            int user_id, medical_booking_id;
            boolean accettata;

            data = resultSet.getString("data");
            orario = resultSet.getString("orario");
            costo = resultSet.getDouble("costo");
            accettata = resultSet.getBoolean("accettata");
            user_id = resultSet.getInt("id_user");
            medical_booking_id = resultSet.getInt("id_visita");
            Appointment appointment = new Appointment(id_prenotazione, data, orario, costo,accettata, user_id, medical_booking_id);
            appointment.setId(resultSet.getInt("id_prenotazione"));

            return appointment;
        } catch (SQLException e) {
            return null;
        }

    }

    public boolean update(Appointment appointmentToUpdate) {
        Connection connection = ConnectionSingleton.getInstance();

        if (appointmentToUpdate.getId() == 0)
            return false;

        Appointment appointmentRead = read(appointmentToUpdate.getId());
        if (!appointmentRead.equals(appointmentToUpdate)) {
            try {
                if (appointmentToUpdate.getDate() == null || appointmentToUpdate.getDate().equals("")) {
                    appointmentToUpdate.setDate(appointmentRead.getDate());
                }

                if (appointmentToUpdate.getHour() == null || appointmentToUpdate.getHour().equals("")) {
                    appointmentToUpdate.setHour(appointmentRead.getHour());
                }


                if (appointmentToUpdate.getCost() == 0) {
                    appointmentToUpdate.setCost(appointmentRead.getCost());
                }

                PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
                preparedStatement.setString(1, appointmentToUpdate.getDate());
                preparedStatement.setString(2, appointmentToUpdate.getHour());
                preparedStatement.setDouble(3, appointmentToUpdate.getCost());
                preparedStatement.setBoolean(4, appointmentToUpdate.getDemand());
                preparedStatement.setInt(5, appointmentToUpdate.getId());
                int a = preparedStatement.executeUpdate();
                if (a > 0)
                    return true;
                else
                    return false;

            } catch (SQLException e) {
                return false;
            }
        }

        return false;

    }

    public boolean delete(int id_appointment) {
        Connection connection = ConnectionSingleton.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setLong(1, id_appointment);
            int n = preparedStatement.executeUpdate();
            if (n != 0)
                return true;

        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}