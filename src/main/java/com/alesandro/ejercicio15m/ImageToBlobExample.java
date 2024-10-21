package com.alesandro.ejercicio15m;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageToBlobExample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load an image from file
        Image image = new Image(new File("path/to/your/image.png").toURI().toString());

        // Convert the image to byte array
        byte[] imageBytes = imageToByteArray(image);

        // Save the byte array to the database as a BLOB
        Connection conn = getConnection();
        saveImageToDatabase(conn, imageBytes);

        // Display the image in a JavaFX application (optional)
        ImageView imageView = new ImageView(image);
        Scene scene = new Scene(imageView, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image to BLOB Example");
        primaryStage.show();
    }

    // Converts Image to byte array (PNG format)
    public static byte[] imageToByteArray(Image image) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);  // Use "jpg" for JPEG
        return byteArrayOutputStream.toByteArray();
    }

    // Saves byte array (image data) to the database as BLOB
    public static void saveImageToDatabase(Connection conn, byte[] imageBytes) throws SQLException {
        String sql = "INSERT INTO images (image_column) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setBytes(1, imageBytes);
        stmt.executeUpdate();
        stmt.close();
    }

    // Establishes a database connection
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String username = "your_username";
        String password = "your_password";
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
