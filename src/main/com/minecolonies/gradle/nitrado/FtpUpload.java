package com.minecolonies.gradle.nitrado;

import org.apache.commons.net.ftp.FTPClient;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Task used to upload an FTP File.
 */
public class FtpUpload extends DefaultTask
{

    @Input
    String host;

    @Input
    Integer port;

    @Input
    String user;

    @Input
    String password;

    @Input
    String filePath;

    @Input
    String targetPath;

    private File getFile()
    {
        return getProject().file(filePath);
    }

    @TaskAction
    void run() throws IOException
    {
        FTPClient client = new FTPClient();

        client.connect(host, port);

        System.out.println("Connected to host.");
        System.out.println(client.getReplyString());

        client.login(user, password);

        System.out.println("Successfully logged in.");
        System.out.println("Uploading: " + filePath);
        System.out.println("To target: " + targetPath);

        client.deleteFile(targetPath);
        if (!client.storeFile(targetPath, new FileInputStream(getFile())))
        {
            client.logout();
            client.disconnect();
            System.out.println("Upload failed!");
        }

        System.out.println("Upload complete.");

        client.logout();
        client.disconnect();

        System.out.println("Disconnected. Upload successfully.");
    }
}
