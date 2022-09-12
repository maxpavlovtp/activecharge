package com.km220.utils;

import com.km220.dao.job.ChargingJobState;
import com.km220.model.ChargingJob;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLDatabaseConnection {

    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://localhost:5432/km220";
    static final String USER = "km220";
    static final String PASS = "Nopassword1";

    public static List<ChargingJob> pollJobs() {
        List<ChargingJob> jobs = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (connection == null) {
            System.out.println("Failed to make connection to database");
        }

        try {
            assert connection != null;
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to make create statement");
            e.printStackTrace();
        }

        try {
            assert statement != null;
            ResultSet sqlStations = statement.executeQuery("SELECT * FROM station;");
            Map<Integer, String> hm = new HashMap<Integer, String>();

            while (sqlStations.next()) {
                int stationNumber = sqlStations.getInt("number");
                String id = sqlStations.getString("id");

                hm.put(stationNumber, id);
            }

            for (Map.Entry<Integer, String> entry : hm.entrySet()) {
                int stationNumber = entry.getKey();
                System.out.println("key =" + stationNumber);
                String id = entry.getValue();
                System.out.println("value =" + id);

                //get last job
                ResultSet resSqlJobs = statement.executeQuery("SELECT MAX(number) FROM charging_job WHERE station_id = '" + id + "';");

                resSqlJobs.next();
                String lastJobNumber = resSqlJobs.getString(1);

                // no last job
                if (lastJobNumber == null) {
                    ChargingJob job = new ChargingJob(Integer.toString(stationNumber), 0, 0);
                        job.setState(ChargingJobState.DONE);

                    jobs.add(job);
                    continue;
                }

                resSqlJobs = statement.executeQuery("SELECT * FROM charging_job WHERE number = '" + lastJobNumber + "';");

                resSqlJobs.next();
                String state = resSqlJobs.getString("state");
                Time created_on = resSqlJobs.getTime("created_on");
                int period_sec = resSqlJobs.getInt("period_sec");


                ChargingJob job = new ChargingJob(Integer.toString(stationNumber), created_on.getTime(), period_sec);
                if ("DONE".equals(state)) {
                    job.setState(ChargingJobState.DONE);
                } else {
                    job.setState(ChargingJobState.IN_PROGRESS);
                }

                jobs.add(job);

            }
        }
        catch (SQLException e) {
            System.out.println("Failed to query database");
            e.printStackTrace();
        }

        return jobs;
    }

}
