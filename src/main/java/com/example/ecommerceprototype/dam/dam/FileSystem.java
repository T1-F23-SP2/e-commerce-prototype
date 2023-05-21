package com.example.ecommerceprototype.dam.dam;

import com.azure.core.credential.AzureSasCredential;
import com.azure.storage.blob.*;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Constants;
import com.example.ecommerceprototype.dam.constants.Type;

import java.io.File;


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


    public boolean watermarkpt1(String filename_in, String type_in, String category_in, String uuid_in) {
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

    public String watermarkpt2(String filename_in, String type_in, String category_in, String uuid_in, String oripath)
    {
        String file_name = filename_in.toLowerCase();
        String folder_name = category_in.toLowerCase();
        String uuid = uuid_in.toLowerCase();

        BlobContainerClient containerClient = setContainerString(type_in);
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
