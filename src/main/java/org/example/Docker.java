package org.example;

import org.testng.Assert;
import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class Docker {

    public static void deleteLogs(String file)
    {
        try {
            Files.deleteIfExists(
                    Paths.get("src/main/resources/"+file+".txt"));
        } catch (NoSuchFileException e) {
            System.out.println(
                    "No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
    }

    public static void startYamlFile() throws IOException, InterruptedException {

        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "src/main/resources/dockerUp.bat");
        File dir = new File("src/main/resources");
        pb.directory(dir);
        Process p = pb.start();
        Assert.assertTrue(Docker.checkServerCreated().booleanValue(),"Server not created");
    }

    private static Boolean checkServerCreated() throws IOException, InterruptedException {
        String file = "src/main/resources/server-logs.txt";

        int counter =6;
        while(counter!=0)
        {
            Thread.sleep(5000);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String currentLine = bufferedReader.readLine();

            while(currentLine!=null)
            {
                if(currentLine.contains("selenium-grid-hub entered RUNNING state")) {
                    ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "src/main/resources/dockerScaleChrome.bat");
                    File dir = new File("src/main/resources");
                    pb.directory(dir);
                    pb.start();

                    Thread.sleep(3000);
                    bufferedReader.close();
                    return true;
                }currentLine = bufferedReader.readLine();
            }
            counter=counter-1;

        }

        return false;
    }

    public static Boolean checkServerStopped() throws IOException, InterruptedException {
        String file = "src/main/resources/server-logs.txt";

        int counter =6;
        while(counter!=0)
        {
            Thread.sleep(5000);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String currentLine = bufferedReader.readLine();

            while(currentLine!=null)
            {
                if(currentLine.contains("Shutdown complete")) {
                    ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "src/main/resources/dockerScaleChrome.bat");
                    File dir = new File("src/main/resources");
                    pb.directory(dir);
                    pb.start();

                    Thread.sleep(3000);
                    bufferedReader.close();
                    return true;
                }currentLine = bufferedReader.readLine();
            }
            counter=counter-1;

        }

        return false;
    }

    public static void stopDocker() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "src/main/resources/dockerDown.bat");
        File dir = new File("src/main/resources");
        pb.directory(dir);
        pb.start();
    }

}
