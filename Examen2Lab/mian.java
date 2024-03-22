/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Examen2Lab;

import java.io.IOException;

/**
 *
 * @author crist
 */
import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.FileNotFoundException;

public class mian {
    public static void main(String[] args) {
        String psnFilePath = JOptionPane.showInputDialog(null, "Ingresar nombre del archivo:");

        PSNUsers psnUsers = null;
        try {
            psnUsers = new PSNUsers(psnFilePath);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: archivo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        try {
            while (true) {
                String username = JOptionPane.showInputDialog(null, "Ingresar nombre de usuario que desea agregar (escribir 'FIN' para terminar):");
                if (username == null || username.equalsIgnoreCase("fin")) {
                    break;
                }
                psnUsers.addUser(username);
            }

            String usernameToDeactivate = JOptionPane.showInputDialog(null, "Ingresar el nombre de usuario que desea desactivar:");
            if (usernameToDeactivate != null) {
                psnUsers.deactivateUser(usernameToDeactivate);
            }

            String usernameForTrophy = JOptionPane.showInputDialog(null, "Ingresar el nombre de usuario al que desea agregar un trofeo:");
            if (usernameForTrophy != null) {
                String gameName = JOptionPane.showInputDialog(null, "Ingresar el nombre del juego:");
                String trophyName = JOptionPane.showInputDialog(null, "Ingresar el nombre del trofeo:");
                String trophyType = JOptionPane.showInputDialog(null, "Seleccionar el tipo de trofeo (PLATINO, ORO, PLATA, BRONCE):");
                Trophy type = Trophy.valueOf(trophyType.toUpperCase());
                psnUsers.addTrophieTo(usernameForTrophy, gameName, trophyName, type);
            }

            String usernameForInfo = JOptionPane.showInputDialog(null, "Ingresar el nombre del usuario que desea obtener información:");
            if (usernameForInfo != null) {
                psnUsers.playerInfo(usernameForInfo);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Tipo de trofeo invalido. Trofeos disponibles: PLATINO, ORO, PLATA, BRONCE.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

