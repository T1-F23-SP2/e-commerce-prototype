package com.example.ecommerceprototype.dam.dam;

import com.azure.storage.blob.*;
import com.example.ecommerceprototype.dam.constants.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.IntBuffer;

public class FileSystem {
    private static FileSystem instance;
    private BlobServiceClient blobServiceClient = null;
    private BlobContainerClient blobContainerClient = null;

    FileSystem() {
        initializeAzureBlob();
    }

    public static FileSystem getInstance() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    private void initializeAzureBlob() {
        String con = Constants.AZURE_SAS_Con;

        blobServiceClient = new BlobServiceClientBuilder().connectionString(con).buildClient();

        blobContainerClient = blobServiceClient.getBlobContainerClient(Constants.AZURE_CONTAINER);
    }

    public String uploadFile()
    {
        String file_path = Constants.DATA_PATH;
        String file_name = "test.jpg";

        BlobClient uploadBlobClient = blobContainerClient.getBlobClient("test.jpg");
        uploadBlobClient.uploadFromFile(file_path + file_name);

        String url = uploadBlobClient.getBlobUrl();

        return url;
    }

    public void deleteFile(int assetID_in)
    {
        String file_name = "nol";
        BlobClient deleteBlobClient = blobContainerClient.getBlobClient(file_name);
        deleteBlobClient.delete();
    }

    public Image downloadFile(String file_name)
    {
        BlobClient downloadBlobClient = blobContainerClient.getBlobClient(file_name);

        String blob_url = downloadBlobClient.getBlobUrl();

        try {
            URL url = new URL(blob_url);
            BufferedImage BuffImage = ImageIO.read(url);

            Image img = BuffImgToImg(BuffImage);

            return img;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Image BuffImgToImg(BufferedImage img)
    {
        //converting to a good type.
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        newImg.createGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);

        //converting the BufferedImage to an IntBuffer.
        int[] type_int_agrb = ((DataBufferInt) newImg.getRaster().getDataBuffer()).getData();
        IntBuffer buffer = IntBuffer.wrap(type_int_agrb);

        //converting the IntBuffer to an Image.
        PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer(newImg.getWidth(), newImg.getHeight(), buffer, pixelFormat);
        return new WritableImage(pixelBuffer);
    }

}
