/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author JAVJ
 */
public class FileManager implements Serializable {

    private BufferedReader bufferedReader;
    private FileReader fileReader;
    private FileWriter fileWriter;
    private File file;
    private String strLine;

    public FileManager() {
    }

    public FileManager(File file) {
        this.file = file;
    }

    /**
     * Devuelve todos los arhivos que contiene un directorio. En caso de que el
     * strUrlFolder que se recibe no sea un folder o no exista devolvera un
     * null.
     *
     * @param strUrlFolder
     * @return
     */
    public String[] getFilesOfFloder(String strUrlFolder) {
        file = new File(strUrlFolder);
        if (file.exists() && file.isDirectory()) {
            return file.list();
        }
        return null;
    }

    /**
     * Devuelve true si la ruta pertenece a un archivo. Devuelve false si es
     * otra cosa
     *
     * @return
     */
    public boolean isFile(String strUrlFile) {
        file = new File(strUrlFile);
        return file.isFile();
    }

    /**
     * Recibe la ruta de un archivo y un string que se escribira en este. En
     * caso de no existir el archivo, se crea, y en caso de existir, se escribe
     * al final de este.
     *
     * @return
     */
    public boolean writeInFile(String strText, String strUrl) {
        boolean boReturn = false;

        file = new File(strUrl);
        String strAux = "";

        try {
            if (file.exists()) {
            } else {
                if (file.createNewFile()) {
                    System.out.println("El fichero se ha creado correctamente");
                } else {
                    System.out.println("No ha podido ser creado el fichero");
                }
            }
            strAux += strText;
            fileWriter = new FileWriter(file);
            fileWriter.write(strAux);
            fileWriter.close();
            boReturn = true;
        } catch (Exception ex) {
            System.out.println("ex = " + ex);
        }







        return boReturn;
    }

    public boolean copyFileTo(String Line, String strUrl) {
//prepara archivo para escritura
        try {
            this.file = new File(strUrl);
            fileWriter = new FileWriter(this.file);

        } catch (IOException ex) {
            return false;
        }

        try {
            fileWriter.write(Line);

        } catch (IOException ex) {
            return false;
        }

        try {
            this.fileWriter.close();

        } catch (IOException ex) {
            return false;
        }

        return true;
    }

    /*
     * Cierra todos los archivos de entrada y de salida
     */
    private void closeFiles() throws IOException {
        this.bufferedReader.close();
        this.fileReader.close();
        this.fileWriter.close();
    }

    /*
     * Elimina un Archivo fisicamente
     */
    public boolean deleteFile(String urlFile) {
        this.file = new File(urlFile);
        return this.file.delete();
    }
}
