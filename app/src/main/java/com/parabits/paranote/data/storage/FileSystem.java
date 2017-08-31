package com.parabits.paranote.data.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Klasa odpowiedzialna za wykonywanie działań na plikach takich jak zapisywanie i usuwanie.
 */

public class FileSystem {

    /**
     * Zapisuje dane w okreslonym miejscu w pamięci.
     * @param data dane, które mają zostać zapisane.
     * @param path ściezka, gdzie ma zostać utworzony plik
     * @return uchwyt pliku który został utworzony
     * @throws IOException
     */
    public static File savedFile(byte[] data, String path) throws IOException
    {
        File file = new File(path);
        if(!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(data);
        outputStream.close();
        return file;
    }

    public static File getFile(String path)
    {
        return new File(path);
    }

    /**
     * Usuwa plik lub katalog o podanej ścieżce.
     * @param path ścieżka do pliku który ma zostać usunięty
     * @return powodzenie operacji usuwania pliku. W przypadku nie znalezienia pliku zostanie zwrócona wartość false
     */
    public static boolean deleteFile(String path)
    {
        File file = new File(path);
        if(file.exists())
        {
            if(file.isFile()) //jeżeli ściezka wskazuje na plik usuwamy go
            {
                return file.delete();
            } else { // jeżeli ścieżka wskazuje na katalog usuwamy katalog za pomocą funkcji deleteDirectory()
                return deleteDirectory(path);
            }
        }
        return false;
    }

    public static boolean deleteDirectory(String path)
    {
        File directory = new File(path);
        deleteDirectory(directory);
        //TODO tutaj pomyśleć jak zwrócić odpowiednią wartość
        return true;
    }

    //TODO tutaj można pomyśleć jak to zrobić bez korzystania z rekurencji

    /**
     * Usuwa katalog o podanej ścieżce wraz z jego zawartością. Najpierw rekurencyjnie przechodzimy do po katalogu i usuwanmy wszystkie pliku
     * które się w nich znajdują. Później usuwamy puste katalogi.
     * @param file uchwyt do pliku lub katalogu króry ma zostać usunięty
     */
    private static void deleteDirectory(File file)
    {
        if(file.exists())
        {
            if(file.isDirectory())
            {
                for(File fileEntry : file.listFiles())
                {
                    deleteDirectory(fileEntry);
                }
                file.delete();
            } else {
                file.delete();
            }
        }
    }

    /**
     * Sprawdza czy plik o podanej ściezce istnieje
     * @param path ścieżka do pliku
     * @return rezultat sprawdzenia istnienia pliku
     */
    public static boolean exists(String path)
    {
        File file = new File(path);
        return file.exists();
    }

    /**
     * Sprawdza czy katalog zawiera jakieś pliku.
     * @param path ścieżka do katalogu
     * @return rezultat sprawdzenia czy katalog zawiera pliku
     */
    public static boolean isDirectoryEmpty(String path)
    {
        //TODO można zrobić jakieś kody rezultatu dzięki czemu można zaznaczyć, że to nie jest katalog
        File directory = new File(path);
        if(directory.exists() && directory.isDirectory())
        {
            return directory.listFiles().length > 0;
        }
        return false;
    }

    /**
     * Zwraca rozmiar pliku o podanej ścieżce. Rozmiar podany jest w bajtach.
     * @param path ścieżka do pliku
     * @return rozmiar pliku
     */
    public static long getFileSize(String path)
    {
        File file = new File(path);
        if(file.exists())
        {
            if(file.isFile())
            {
                return getFileSize(file);
            } else {
                return getDirectorySize(file);
            }
        }
        return -1; //oznacza, że plik lub katalog nie istnieje
    }

    private static long getFileSize(File file)
    {
        return file.length();
    }

    private static long getDirectorySize(File directory)
    {
        long size = 0;
        for(File fileEntry : directory.listFiles())
        {
            //TODO zrobić tutaj kolejne zagłębienia
            if(fileEntry.isFile())
            {
                size += fileEntry.length();
            }
        }
        return size;
    }




}
