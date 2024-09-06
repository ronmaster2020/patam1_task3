package test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler {
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        try (Scanner in = new Scanner(new BufferedReader(new InputStreamReader(inFromclient)))) {
            in.useDelimiter("\n");
            if (in.hasNext()) {
                Scanner lineScanner = new Scanner(in.next());
                lineScanner.useDelimiter(",");

                char mode;
                if (lineScanner.hasNext())
                    mode = lineScanner.next().charAt(0);
                else
                    throw new IOException("No mode found");

                List<String> booksList = new ArrayList<>();
                while (lineScanner.hasNext()) {
                    String bookName = lineScanner.next();
                    booksList.add(bookName);
                }
                lineScanner.close();

                String[] books = booksList.toArray(new String[0]);

                PrintWriter out = new PrintWriter(outToClient);
                boolean result = false;
                if (mode == 'Q') {
                    result = DictionaryManager.get().query(books);
                } else if (mode == 'C') {
                    result = DictionaryManager.get().challenge(books);
                }
                out.println(result);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void close() {
    }
}
