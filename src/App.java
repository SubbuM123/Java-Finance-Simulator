import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.util.*;

public class App {
    // public static void main(String[] args) {
    //     System.out.println("Hi");
    //     // Account d = new Account(200, "Test", 5.5);
    //     // d.Deposit(10000);
    //     // d.Withdraw(1);
    //     //System.out.println(d.name + d.balance);
    //     Bank b = new Bank(3);
    //     b.NewCD(500, "First", 5, 18);
    //     b.Deposit(500, "Savings");
    //     //b.TransferFundsAcc(300, "Savings", "Checking");
    //     //b.TransferFundsCD(500, "First", "Checking");
    //     System.out.println(b.Total() + " initial");

    //     b.Simulate(20);
    //     b.Simulate(10);
    //     b.Simulate(20);
    // }
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new StaticFileHandler());
        server.createContext("/multiply", new MultiplyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server running at http://localhost:8080/");
    }

    // function: multiplies by 12
    public static String projection(int[] arr) {
        int n = arr[14];

        // {"checkB", "saveB", "saveA",
        //             "CD1B", "CD1A", "CD1M",
        //             "CD2B", "CD2A", "CD2M",
        //             "CD3B", "CD3A", "CD3M",  
        //             "mmB", "mmA", "n"};

        Bank b = new Bank(arr[2]);

        b.Deposit(arr[1], "savings");
        b.Deposit(arr[0], "checking");

        b.NewCD(arr[3], "CD1", arr[4], arr[5]);
        b.NewCD(arr[6], "CD2", arr[7], arr[8]);
        b.NewCD(arr[9], "CD3", arr[10], arr[11]);

        b.NewCD(arr[12], "mm", arr[13], 100000);

        double total = b.Simulate(n);

        return "Result: " + total;
    }

    static class MultiplyHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                        .lines().collect(Collectors.joining("\n"));
    
                // Very naive JSON parsing: {"checkB":"5","checkC":"10","checkD":"15"}
                body = body.replaceAll("[{}\"]", ""); // remove { } and quotes
                String[] pairs = body.split(",");
    
                Map<String, String> data = new HashMap<>();
                for (String pair : pairs) {
                    String[] kv = pair.split(":");
                    if (kv.length == 2) {
                        data.put(kv[0].trim(), kv[1].trim());
                    }
                }

                int[] arr = new int[15];
                String[] keys = {"checkB", "saveB", "saveA",
                    "CD1B", "CD1A", "CD1M",
                    "CD2B", "CD2A", "CD2M",
                    "CD3B", "CD3A", "CD3M",  
                    "mmB", "mmA", "n"};

                int c = 0;
                for (String k : keys) {
                    arr[c] = Integer.parseInt(data.getOrDefault(k, "0")); 
                    c++;
                }
    
                // int checkB = Integer.parseInt(data.getOrDefault("checkB", "0"));
                // int checkC = Integer.parseInt(data.getOrDefault("checkC", "0"));
                // int checkD = Integer.parseInt(data.getOrDefault("checkD", "0"));
    
                String result = projection(arr);
                System.out.println("multiplyBy12 result: " + result);
    
                exchange.getResponseHeaders().add("Content-Type", "text/plain");
                exchange.sendResponseHeaders(200, result.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(result.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

}
