package Examen2Lab;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class PSNUsers {
    private RandomAccessFile raf;
    private HashTable users;

    public PSNUsers(String psnFilePath) throws FileNotFoundException {
        try {
            raf = new RandomAccessFile(psnFilePath, "rw");
            reloadHashTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void reloadHashTable() throws IOException {
        users = new HashTable();
        raf.seek(0); 
        while (raf.getFilePointer() < raf.length()) {
            if (raf.getFilePointer() < raf.length()) {
                String username = raf.readUTF();
                long pos = raf.readLong();
                boolean isActive = raf.readBoolean();
                if (isActive) {
                    users.add(username, pos);
                }
            }
        }
    }
    private void writeUserToFile(String username, boolean isActive, int trophyCount) throws IOException {
        raf.seek(raf.length()); 
        raf.writeUTF(username);
        raf.writeLong(raf.getFilePointer()); 
        raf.writeBoolean(isActive);
        raf.writeInt(trophyCount);
        raf.writeInt(0); 
    }
    public void addUser(String username) throws IOException {
        if (users.search(username) != -1) {
            JOptionPane.showMessageDialog(null,"Usuario existente, favor seleccionar otro.");
            return;
        }
        long posBeforeAddingUser = raf.getFilePointer();
        writeUserToFile(username, true, 0); 
        users.add(username, posBeforeAddingUser);
    }
    public void deactivateUser(String username) throws IOException {
        long pos = users.search(username);
        if (pos != -1) {
            raf.seek(pos + 8); 
            if (raf.readBoolean()) {
                raf.seek(pos + 8);
                raf.writeBoolean(false);
                users.remove(username);
                JOptionPane.showMessageDialog(null,"Usuario: " + username + " desactivado.");
            } else {
                JOptionPane.showMessageDialog(null,"Usuario: " + username + " ya está desactivado.");
            }
        } else {
            JOptionPane.showMessageDialog(null,"Usuario: " + username + " no encontrado.");            
        }
    }
    public void addTrophieTo(String username, String trophyGame, String trophyName, Trophy type) throws IOException {
        long pos = users.search(username);
        if (pos != -1) {
            raf.seek(raf.length());
            raf.writeUTF(username);
            raf.writeUTF(type.name());
            raf.writeUTF(trophyGame);
            raf.writeUTF(trophyName);
            raf.writeUTF(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            long userPos = users.search(username);
            raf.seek(userPos + username.length() * 2 + 17); 
            int trophyCount = raf.readInt();
            raf.seek(userPos + username.length() * 2 + 21); 
            int trophyPoints = raf.readInt();
            raf.seek(userPos + username.length() * 2 + 17); 
            raf.writeInt(trophyCount + 1);
            raf.seek(userPos + username.length() * 2 + 21); 
            raf.writeInt(trophyPoints + type.points);
            JOptionPane.showMessageDialog(null, "Trofeo agregado exitosamente.\nUsuario: "+username);
        } else {
            JOptionPane.showMessageDialog(null,"Usuario "+username+" No ha sido encontrado.");            
        }
    }
    public void playerInfo(String username) throws IOException {
    long pos = users.search(username);
    if (pos != -1) {
        raf.seek(pos); 
        String usernameFromFile = raf.readUTF();
        boolean isActive = raf.readBoolean();
        int trophyCount = raf.readInt();
        int trophyPoints = raf.readInt();
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("Información del usuario:\n");
        userInfo.append("Username: ").append(usernameFromFile).append("\n");
        userInfo.append("Activo: ").append(isActive).append("\n");
        userInfo.append("Contador de trofeos: ").append(trophyCount).append("\n");
        userInfo.append("Acumulador de puntos por trofeos: ").append(trophyPoints).append("\n");
        StringBuilder trophiesInfo = new StringBuilder();
        trophiesInfo.append("Trofeos ganados:\n");
        long trophiesPos = pos + usernameFromFile.length() * 2 + 21;
        raf.seek(trophiesPos); 
        while (trophiesPos < raf.length()) {
            String fecha = raf.readUTF();
            String tipo = raf.readUTF();
            String juego = raf.readUTF();
            String descripcion = raf.readUTF();
            trophiesInfo.append(fecha).append(" - ").append(tipo).append(" - ").append(juego).append(" - ").append(descripcion).append("\n");
            trophiesPos = raf.getFilePointer();
        }
        JOptionPane.showMessageDialog(null, userInfo.toString() + trophiesInfo.toString(), "Información del jugador", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(null, "Usuario " + username + " no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}