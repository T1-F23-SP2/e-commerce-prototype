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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;


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
        return switch (type) {
            case PRODUCT_IMAGE -> blobContainerClientPI;
            case PRODUCT_FILE -> blobContainerClientPF;
            case IMAGE -> blobContainerClientImages;
            case FILE -> blobContainerClientFiles;
        };
    }

    public BlobContainerClient setContainerString (String type)
    {
        return switch (type) {
            case "PRODUCT IMAGE" -> blobContainerClientPI;
            case "PRODUCT FILE" -> blobContainerClientPF;
            case "IMAGE" -> blobContainerClientImages;
            case "FILE" -> blobContainerClientFiles;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
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


    public String uploadFile(String filename_in, Type type_in, Category category_in, String uuid_in, String oripath)
    {
        String file_name = filename_in.toLowerCase();
        String folder_name = category_in.getValue().toLowerCase();
        String uuid = uuid_in.toLowerCase();

        BlobContainerClient containerClient = setContainer(type_in);
        BlobClient uploadBlobClient;

       if (uuid_in.isBlank()) {
           uploadBlobClient = containerClient.getBlobClient(folder_name + "/" + file_name);
       } else {
           uploadBlobClient = containerClient.getBlobClient(folder_name + "/" + uuid + "/" + file_name);
       }

        uploadBlobClient.uploadFromFile(oripath);

        String url = uploadBlobClient.getBlobUrl();

        return shortenURL(url);
    }

    public boolean deleteFile(String filename_in, String type_in, String category_in, String uuid_in)
    {
        String file_name = filename_in.toLowerCase();
        String folder_name = category_in.toLowerCase();
        String uuid = uuid_in.toLowerCase();

        BlobContainerClient containerClient = setContainerString(type_in.toUpperCase());

        BlobClient deleteBlobClient;

        if (uuid_in.isBlank()) {
            deleteBlobClient = containerClient.getBlobClient(folder_name + "/" + file_name);
        } else {
            deleteBlobClient = containerClient.getBlobClient(folder_name + "/" + uuid + "/" + file_name);
        }

        return deleteBlobClient.deleteIfExists();
    }


    public void downloadFile(String filename_in, String type_in, String category_in, String uuid_in)
    {
        String file_name = filename_in.toLowerCase();
        String folder_name = category_in.toLowerCase();
        String uuid = uuid_in.toLowerCase();

        BlobContainerClient containerClient = setContainerString(type_in.toUpperCase());

        BlobClient downloadBlobClient;

        if (uuid_in.isBlank()) {
            downloadBlobClient = containerClient.getBlobClient(folder_name + "/" + file_name);
        } else {
            downloadBlobClient = containerClient.getBlobClient(folder_name + "/" + uuid + "/" + file_name);
        }

        String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads"+File.separator + file_name;
        downloadBlobClient.downloadToFile(downloadsPath);
    }


    public boolean watermark(String filename_in, String type_in, String category_in, String uuid_in) {
        String file_name = filename_in.toLowerCase();
        String folder_name = category_in.toLowerCase();
        String uuid = uuid_in.toLowerCase();

        BlobContainerClient containerClient = setContainerString(type_in.toUpperCase());

        BlobClient downloadBlobClient;

        if (uuid_in.isBlank()) {
            downloadBlobClient = containerClient.getBlobClient(folder_name + "/" + file_name);
        } else {
            downloadBlobClient = containerClient.getBlobClient(folder_name + "/" + uuid + "/" + file_name);
        }

        String download = "./data/"+ file_name;
        downloadBlobClient.downloadToFile(download);

        return true;
    }

    public Image downloadImageFromURL(String URL){
        try {
            String storageurl = Constants.AZURE_Start_URL;
            URL url = new URL(storageurl+URL);
            BufferedImage BuffImage = ImageIO.read(url);

            return BuffImgToImg(BuffImage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadFileFromURL(String desiredDownloadPath_in, String url_in, String uuid_in)
    {
        try {
            URL url = new URL(url_in);

            String filename = extractName(url_in, uuid_in);

            String path = desiredDownloadPath_in+uuid_in;

            Files.createDirectories(Paths.get(path));

            File myFile = new File(path+"/"+filename);


            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(myFile);
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

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


    public String extractName(String url_in, String uuid_in)
    {
        String url = url_in;
        String extractionPart = "%2F"+uuid_in+"%2F";
        int lettersToSubtract = extractionPart.length();

        String formatString = url.substring(url.lastIndexOf("%2F"+uuid_in+"%2F") + lettersToSubtract);
        return formatString;
    }




}
