package com.example.galacticMessengerClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.galacticMessengerClient.Console.ConsoleUser;

import java.util.regex.*;
import java.util.*;
import java.io.IOException;
import java.net.*;

@SpringBootApplication
public class GalacticMessengerClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(GalacticMessengerClientApplication.class, args);
        try {
            if (checkArgs(args) && findIPv4(args[0]) && findPort(args[0], Integer.parseInt(args[1]))) {
                ConsoleUser consoleUser = new ConsoleUser(args);
                consoleUser.displayLaunchInstruction();
                consoleUser.ConsoleUseGalacticMessenger();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean findIPv4(String ipToSearch) throws SocketException {
        String ip = null;
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while (interfaces.hasMoreElements() && ip == null) {
            NetworkInterface ni = interfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();

            while (inetAddresses.hasMoreElements() && ip == null) {
                InetAddress address = inetAddresses.nextElement();

                if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                    String temp = address.toString();
                    String currentIp = temp.replace("/", "");

                    if (currentIp.equals(ipToSearch)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean findPort(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 10_000 /* 10s */);
            return socket.isConnected();
        } 
        catch (Exception e) {
            System.out.println("\nPort inexistant ou fermé.\n");
            System.exit(0);
        } 

        return false;
    }

    private static boolean checkArgs(String[] args) {
        if (args.length == 2) {
            // regex basé sur le format de port
            String portPattern = "^[0-9]{4}$";
            // vérification de la validité de l'adresse ip par rapport au numéro de port
            Pattern pattern = Pattern.compile(portPattern);
            Matcher matcherPort = pattern.matcher(args[1]);
            // vérifie l'ip et le port
            if (checkIpFormat(args[0]) && matcherPort.matches()) {
                return true;
            } else {
                System.out.println("\nAdresse et numero de port invalides !\n\n");
                System.exit(0);
            }
        } else {
            System.out.printf("\nNombre d'arguments invalides : 2 attendus, %d recus\n\n", args.length);
            System.exit(0);
        }
        return false;
    }

    private static boolean checkIpFormat(String ip) {
        // si l'ip contient bien des points
        if (ip.contains(".")) {
            // sépare les octets
            String[] ipArray = ip.split("\\.");
            // s'il y a bien 4 octets
            if (ipArray.length == 4) {
                // pour chaque octet
                for (String octet : ipArray) {
                    // pour chaque char de l'octet
                    for (char o : octet.toCharArray()) {
                        // si le char n'est pas un nombre
                        if (!Character.isDigit(o)) {
                            return false;
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
