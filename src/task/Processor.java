package task;

import java.io.*;
import java.net.Socket;

/**
 * Processor of HTTP request.
 */
public class Processor {
    private final Socket socket;
    private final HttpRequest request;
    public String response = "";

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }


    public void process() throws IOException {
        System.out.println("Got request:");
        System.out.println(request.toString());
        System.out.flush();

        String s = request.toString();
        int pos;
        char c;
        String name = "";
        if(s.contains("create")) {

            for(pos = 12; s.charAt(pos) != ' '; ++pos) {
                c = s.charAt(pos);
                name = name + c;
            }

            File myObj = new File(name);
            if (myObj.createNewFile()) {
                String var10001 = this.response;
                this.response = var10001 + "File created: " + myObj.getName();
            } else {
                this.response = this.response + "File already exists.";
            }

        }
        else if(s.contains("delete")) {
            for(pos = 12; s.charAt(pos) != ' '; ++pos) {
                c = s.charAt(pos);
                name = name + c;
            }
            File obj = new File(name);
            if (obj.delete()) {
                String var10001 = this.response;
                this.response = var10001 + "File deleted: " + obj.getName();
            } else {
                this.response = this.response + "File successfully delete";
            }
        }
        else if(s.contains("write")){
            pos = 11;
            while(s.charAt(pos) != '/'){
                c = s.charAt(pos);
                name += c;
                pos++;
            }
//            FileWriter myWriter = new FileWriter(name);
//            myWriter.write("some text");
//            myWriter.close();
            PrintWriter writer = new PrintWriter("name", "UTF-8");
            writer.println("Friends is an American television sitcom created by David Crane and Marta Kauffman," +
                    " which aired on NBC from September 22, 1994, to May 6, 2004, lasting ten seasons.[1] With an ensemble cast starring Jennifer Aniston," +
                    " Courteney Cox, Lisa Kudrow, Matt LeBlanc, Matthew Perry and David Schwimmer, the show revolves around six friends in their 20s and " +
                    "30s who live in Manhattan, New York City. The series was produced by Bright/Kauffman/Crane Productions, in association with Warner Bros." +
                    " Television. The original executive producers were Kevin S. Bright, Kauffman, and Crane.\n" +
                    "Kauffman and Crane began developing Friends under the working title Insomnia Cafe between November and December 1993. " +
                    "They presented the idea to Bright, and together they pitched a seven-page treatment of the show to NBC. After several script" +
                    " rewrites and changes, including, title changes to Six of One[2] and Friends Like Us, the series was finally named Friends.[");
            writer.close();
            this.response += "Successfully wrote to the file.";
        }
        else if(s.contains("execute")) {
            for (pos = 13; s.charAt(pos) != ' '; ++pos) {
                c = s.charAt(pos);
                name = name + c;
            }
            String ch="e";
            int count=0;
            BufferedReader myReader = new BufferedReader(new FileReader(name));
            String strCurrentLine;
            while ((strCurrentLine = myReader.readLine()) != null) {
                String[] words = strCurrentLine.split("");
                for (String word : words) {
                    if (word.equals(ch))
                    {
                        count++;
                    }
                }
                this.response += strCurrentLine;
            }
            if(count!=0)
            {
                this.response = "The given character is present for "+count+ " Times in the file";
            }
            else
            {
                this.response = "The given character is not present in the file";
            }
            myReader.close();
        }



        PrintWriter output = new PrintWriter(socket.getOutputStream());

        // We are returning a simple web page now.
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();
        output.println("<html>");
        output.println("<head><title>Hello</title></head>");
        output.println("<body><p>" + this.response + "</p></body>");
        output.println("</html>");
        output.flush();
        socket.close();
        this.response = "";
    }
}
