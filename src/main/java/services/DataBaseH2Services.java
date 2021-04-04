package services;

import org.h2.tools.Server;

import java.sql.SQLException;

public class DataBaseH2Services {

    private static Server server;



    public static void startDb() {
        try {
            server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-ifNotExists").start();
            System.out.println("[-] START DATABASE OK!");

            String status = Server.createWebServer("-trace", "-webPort", "9090").start().getStatus();
            System.out.println("Status Web: "+status);
        } catch (SQLException te) {
            System.out.println("Problem with DataBase: "+te.getMessage());
        }


    }


    public static void stopDb(){
        server.shutdown();
    }


}
