package com.example.ecommerceprototype.pim;

import java.io.File;
import java.nio.file.FileSystem;

/*
    This is a dummy class, which has been created for the sole purpose of providing a way,
    to retrieve resources located in 'src/main/resources/com/example/ecommerceprototype/pim/' folder,
    by using 'PIMResourceRoot.class.getResource(...)' or 'PIMResourceRoot.class.getResourceStream(...)'.
    By having a persistent class for this purpose only, allows to always and consistently have access to resources,
    regardless of any changes in the rest of the package structure.
 */
public class PIMResourceRoot {
    public static String getPathString(String filename) {
        return "src/main/resources/" + PIMResourceRoot.class.getPackage().getName().replace('.', File.separatorChar) +  File.separatorChar + filename;
    }

    public static String getPathString() {
        return getPathString("");
    }
}