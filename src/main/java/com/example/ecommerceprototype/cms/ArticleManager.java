package com.example.ecommerceprototype.cms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ArticleManager implements IArticle{
    private static ArticleManager instance;
    private ArticleManager() {} //Zero-arg constructor
    static protected ArticleManager getInstance() {
        if (instance == null)
            instance = new ArticleManager();
        return instance;
    }

    @Override
    public ArrayList<File> getArticleFiles() {
        ArrayList<File> result = new ArrayList<>();
        File infile = new File("src/main/resources/com/example/ecommerceprototype/cms/Articles");
        if (!infile.exists())
            return result;

        File[] allFiles = infile.listFiles();
        result.addAll(Arrays.asList(allFiles));
        return result;
    }
    @Override
    public int getArticleCount() { //Counts specifically text files in a directory
        File infile = new File("src/main/resources/com/example/ecommerceprototype/cms/Articles");
        if (!infile.exists())
            return 0;

        File[] allFiles = infile.listFiles();

        int count = 0;
        for (File f : allFiles) {
            if (f.getName().endsWith(".txt"))
                count++;
        }

        return count;
    }
    @Override
    public ArrayList<String> getArticleNames() {
        ArrayList<String> results = new ArrayList<>();
        File infile = new File("src/main/resources/com/example/ecommerceprototype/cms/Articles");
        if (!infile.exists())
            return results;

        File[] allFiles = infile.listFiles();

        for (File f : allFiles) {
            try (Scanner sc = new Scanner(f)) {
                results.add(sc.nextLine());
            }
            catch (FileNotFoundException fnfe) {
                System.out.println(fnfe.getMessage());
            }
        }
        return results;
    }

    @Override
    public File getArticle(String articleName) {
        ArrayList<File> articles = getArticleFiles();
        for (File file: articles){
            try (Scanner sc = new Scanner(file)){
                if (articleName.equals(sc.nextLine())){
                    return file;
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
