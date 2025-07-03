/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.util;

/**
 *
 * @author Zailyn Tencio
 */
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class BackupManager {

    public final String backupDir;

    public BackupManager(String backupDir) {
        this.backupDir = backupDir;
        File dir = new File(backupDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("No se pudo crear carpeta backup: " + backupDir);
        }
    }

    public void backupFile(String rutaOriginal) throws IOException {
        File orig = new File(rutaOriginal);
        if (!orig.exists()) {
            return;
        }
        String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String name = orig.getName().replaceFirst("\\.xml$", "") + "_" + ts + ".xml";
        Files.copy(orig.toPath(), Paths.get(backupDir, name), StandardCopyOption.REPLACE_EXISTING);
    }

    public File seleccionarBackup(String criterio) throws IOException {
        File[] files = new File(backupDir)
                .listFiles((d, n) -> n.contains(criterio));
        if (files == null || files.length == 0) {
            throw new java.io.FileNotFoundException("No hay backup para criterio: " + criterio);
        }
        return Arrays.stream(files)
                .sorted(Comparator.comparing(File::getName).reversed())
                .findFirst().orElseThrow();
    }

    public void restaurarBackup(String rutaBackup, String rutaOriginal) throws IOException {
        Files.copy(Paths.get(rutaBackup), Paths.get(rutaOriginal), StandardCopyOption.REPLACE_EXISTING);
    }
}
