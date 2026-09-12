import java.io.IOException;
import java.net.*;

public class LanSpammer {
    // Адрес и порт для мультикаст-рассылки
    private static final String MULTICAST_ADDRESS = "224.0.2.60";
    private static final int MULTICAST_PORT = 4445;

    public static void main(String[] args) throws IOException, InterruptedException {
        // Используем MulticastSocket вместо DatagramSocket
        MulticastSocket socket = new MulticastSocket();
        // Устанавливаем время жизни (TTL) пакета
        socket.setTimeToLive(2);

        // Адрес для отправки
        InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);

        // --- КОНФИГУРАЦИЯ СПАМА ---
        int numberOfFakeServers = 100; // Количество фейковых серверов
        String baseMotd = "FakeServer"; // Базовое имя
        int startPort = 25565; // Стартовый порт
        // -------------------------

        System.out.println("Начинаем рассылку фейковых LAN-серверов...");

        // Бесконечный цикл спама
        while (true) {
            for (int i = 0; i < numberOfFakeServers; i++) {
                // Генерируем уникальные MOTD и порт для каждого фейкового сервера
                String motd = baseMotd + "_" + i;
                int fakePort = startPort + i;
                // В AD можно указать любой IP, но клиент все равно будет пытаться 
                // подключиться к отправителю пакета. Поэтому лучше указать свой IP.
                String addressPort = "127.0.0.1:" + fakePort;

                // Формируем строку объявления
                String announcement = String.format("[MOTD]%s[/MOTD][AD]%s[/AD]", motd, addressPort);

                // Кодируем в байты для отправки
                byte[] buffer = announcement.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, MULTICAST_PORT);

                // Отправляем пакет
                socket.send(packet);
            }
            System.out.println("Отправлено " + numberOfFakeServers + " фейковых объявлений.");

            // Ждем 1.5 секунды, как реальные сервера
            Thread.sleep(1500);
        }
    }
}