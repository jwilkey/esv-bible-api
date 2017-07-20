package com.tentkeep.esv.bible.controllers;

import org.h2.tools.Script;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/database")
public class DatabaseController {
    @RequestMapping(value = "/backup", produces="application/zip")
    @ResponseBody
    public void backup(HttpServletResponse response) throws SQLException, IOException {
        String backupFile = "h2.backup.zip";
        String tempOutputFilenName = "out.zip";
        Script.main("-url", "jdbc:h2:~/bible;DB_CLOSE_ON_EXIT=FALSE",  "-user", "sa", "-script", tempOutputFilenName, "-options", "compression", "zip");
        File f = new File(tempOutputFilenName);
        ZipFile zipFile = new ZipFile(tempOutputFilenName);
        final ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
        for(Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
            ZipEntry entryIn = (ZipEntry) e.nextElement();
            zos.putNextEntry(new ZipEntry(entryIn.getName()));

            InputStream is = zipFile.getInputStream(entryIn);
            byte[] firstBytes = "DROP ALL OBJECTS".getBytes();
            zos.write(firstBytes);
            byte[] buf = new byte[1024];
            int len;
            while ((len = (is.read(buf))) > 0) {
                zos.write(buf, 0, (len < buf.length) ? len : buf.length);
            }
            zos.closeEntry();
        }
        zos.close();
        f.delete();
    }
}
