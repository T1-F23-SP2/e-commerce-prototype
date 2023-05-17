package com.example.ecommerceprototype.dam.dam;

import com.azure.storage.blob.*;
import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Constants;
import com.example.ecommerceprototype.dam.constants.Type;
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
    private BlobContainerClient blobContainerClient = null;
    private BlobContainerClient blobContainerClientPI = null;
    private BlobContainerClient blobContainerClientPF = null;
    private BlobContainerClient blobContainerClientImages = null;
    private BlobContainerClient blobContainerClientFiles = null;


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

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(con).buildClient();

        blobContainerClient = blobServiceClient.getBlobContainerClient(Constants.AZURE_CONTAINER);

        blobContainerClientFiles = blobServiceClient.getBlobContainerClient(Constants.AZURE_CONTAINER_files);

        blobContainerClientImages = blobServiceClient.getBlobContainerClient(Constants.AZURE_CONTAINER_images);

        blobContainerClientPF = blobServiceClient.getBlobContainerClient(Constants.AZURE_CONTAINER_pf);

        blobContainerClientPI = blobServiceClient.getBlobContainerClient(Constants.AZURE_CONTAINER_pi);

    }

    public BlobContainerClient setContainer (Type type)
    {
        BlobContainerClient container = null;

        switch (type) {
            case PRODUCT_IMAGE:
                container = blobContainerClientPI;
                break;
            case PRODUCT_FILE:
                container = blobContainerClientPF;
                break;
            case IMAGE:
                container = blobContainerClientImages;
                break;
            case FILE:
                container = blobContainerClientFiles;
                break;
        }

        return container;
    }

    private String shortenURL (String url)
    {
        if (url.startsWith(Constants.AZURE_Start_URL))
        {
            url = url.substring(37);
            System.out.println(url);
        }
        return url;
    }


    public String uploadFile(Type type, Category category)
    {
        String file_path = Constants.DATA_PATH;
        String file_name = "test.jpg";
        String folder_name = category.getValue();

        BlobContainerClient containerClient = setContainer(type);

        BlobClient uploadBlobClient = containerClient.getBlobClient(folder_name + "/test3.jpg");
        uploadBlobClient.uploadFromFile(file_path+file_name);

        String url = uploadBlobClient.getBlobUrl();

        String newUrl = shortenURL(url);

        return newUrl;
    }

    public void deleteFile(int assetID_in)
    {
        String file_name = "nol";

    }
/*
    public Image downloadFile(String file_name)
    {


        BlobClient downloadBlobClient = .getBlobClient(file_name);

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

 */

}
