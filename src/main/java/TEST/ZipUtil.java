package TEST;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class ZipUtil {
 
    private ZipUtil() {
        // empty
    }
 
    /**
     * 压缩文件
     * 
     * @param filePath
     *            待压缩的文件路径
     * @return 压缩后的文件
     */
    public static File zip(String filePath) {
        File zip = null;
        File file = new File(filePath);
        if (file.exists()) {
            zip = new File("e:","file.zip");//(zip保存路径,zip文件名称)
            if (zip.exists()) {
                zip.delete(); // 删除旧的文件
            }
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(zip);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                // 添加对应的文件Entry
                addEntry("/", file, zos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtil.closeQuietly(zos, fos);
            }
        }
        return zip;
    }
 
    /**
     * 扫描添加文件Entry
     * 
     * @param base
     *            基路径
     * 
     * @param file
     *            源文件
     * @param zos
     *            Zip文件输出流
     * @throws IOException
     */
    private static void addEntry(String base, File file, ZipOutputStream zos)
            throws IOException {
        // 按目录分级，形如：/aaa/bbb.txt
    	String entry = base + file.getName();
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            byte[] buffer = new byte[1024 * 10];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis, buffer.length);
            int read = 0;
            zos.putNextEntry(new ZipEntry(file));
            while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                zos.write(buffer, 0, read);
            }
            zos.closeEntry();
        } finally {
            IOUtil.closeQuietly(bis, fis);
        }
    }
 
    /**
     * 解压文件
     * 
     * @param filePath
     *            压缩文件路径
     */
    public static void unzip(String filePath) {
        File source = new File(filePath);
        if (source.exists()) {
            ZipInputStream zis = null;
            BufferedOutputStream bos = null;
            try {
                zis = new ZipInputStream(new FileInputStream(source));
                ZipEntry entry = null;
                while ((entry = zis.getNextEntry()) != null
                        && !entry.isDirectory()) {
                    File target = new File(source.getParent(), entry.getName());
                    if (!target.getParentFile().exists()) {
                        // 创建文件父目录
                        target.getParentFile().mkdirs();
                    }
                    // 写入文件
                    bos = new BufferedOutputStream(new FileOutputStream(target));
                    int read = 0;
                    byte[] buffer = new byte[1024 * 10];
                    while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                    bos.flush();
                }
                zis.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtil.closeQuietly(zis, bos);
            }
        }
    }
    /** 
     * 压缩单个文件 
     *  
     * @param srcfile 
     */  
    public void zipFile(File srcfile, ZipOutputStream out, String basedir) {  
        if (!srcfile.exists())  
            return;  
  
        byte[] buf = new byte[1024];  
        FileInputStream in = null;  
  
        try {  
            int len;  
            in = new FileInputStream(srcfile);  
            out.putNextEntry(new ZipEntry(basedir + srcfile.getName()));  
  
            while ((len = in.read(buf)) > 0) {  
                out.write(buf, 0, len);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null)  
                    out.closeEntry();  
                if (in != null)  
                    in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    public void a(){
    	
    	zipFile(new File("e:\\file\\image.png"),out,"e:");
    }
    public static void main(String[] args) {
        String targetPath = "E:\\file";
        File file = ZipUtil.zip(targetPath);
        System.out.println(file);
        //ZipUtil.unzip("E:\\file\\Win7壁纸.zip");
    }
}