package com.example.ecommerceprototype.dam.constants;

public class Constants {

    //DATABASE
    public static final String DB_URL = "jdbc:postgresql://damsem2.postgres.database.azure.com:5432/dam";
    public static final String DB_USER = "padmin";
    public static final String DB_PASSWORD = "Dam2.semester";
    public static final String DB_URL2 = "jdbc:postgresql://damsem2.postgres.database.azure.com:5432/test";



    //AZURE

    //endpoint/connecting string

    public static final String AZURE_SAS_Con = "BlobEndpoint=https://damsem2.blob.core.windows.net/;QueueEndpoint=https://damsem2.queue.core.windows.net/;FileEndpoint=https://damsem2.file.core.windows.net/;TableEndpoint=https://damsem2.table.core.windows.net/;SharedAccessSignature=sv=2021-12-02&ss=b&srt=co&sp=rwdlacytfx&se=2023-12-31T17:42:51Z&st=2023-05-02T08:42:51Z&spr=https&sig=WYfmpyhhTfrbvs0CWWXjHl%2BbJpQ1AEDCogr6HYOlgD8%3D";
    public static final String AZURE_CONTAINER = "test";
    public static final String AZURE_CONTAINER_files = "files";
    public static final String AZURE_CONTAINER_pi = "images";
    public static final String AZURE_CONTAINER_pf = "productfiles";
    public static final String AZURE_CONTAINER_images = "productimages";
    public static final String AZURE_Start_URL = "https://damsem2.blob.core.windows.net/";



    public static String DATA_PATH = "./data/";

}
