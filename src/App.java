import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.JSch;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Session session = null;
        ChannelExec channel = null;
        Scanner leer = new Scanner(System.in);

        try {
            System.out.print("Introduce el usuario por defecto username");
            String username = leer.nextLine();

            System.out.print("Introduce la ip del servidor: por defecto=> 127.0.0.1 ");
            String ip = leer.nextLine();

            System.out.print("Introduce el puerto de la máquina: ");
            int port = leer.nextInt();

            session = new JSch().getSession(username, ip, port);

            System.out.print("Introduce la contraseña del usuario: por defecto => 12345678 ");
            String contrasena = leer.nextLine();

            System.out.println("A continuación se conectará con la máquina espere mientras se establece la sonexión");
            session.setPassword(contrasena);
            session.connect();

            int bucle = 0;

            while (bucle == 1){

                channel = (ChannelExec) session.openChannel("exec");

                System.out.println("Introduce el nombre del archivo a buscar ");
                String nombreArchivo = leer.nextLine();

                System.out.println("Buscando archivo espere... ");
                channel.setCommand("cat /var/log/" + nombreArchivo + ".log");

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                channel.setOutputStream(baos);

                channel.connect();

                String respuesta = new String(baos.toByteArray());

                if ( respuesta == ""){
                    System.out.println("No se encuentra el archivo seleccionado ");
                } else {
                    System.out.println("Archivo encontrado introduce 1 para salir y 2 para continuar");
                    bucle = leer.nextInt();
                }
            }

            System.out.println("Programa finalizado....");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null) session.disconnect();
            if (channel != null) channel.disconnect();
        }

    }
}