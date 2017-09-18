package TEST;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

public class UnZip {
	public void uuu(String strUrl ) {
		try {
			final int BUFFER = 2048;
			BufferedOutputStream dest = null;
			URL url = new URL(strUrl);    
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
            conn.setRequestMethod("GET");    
            conn.setConnectTimeout(5 * 1000);    
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据    
			CheckedInputStream checksum = new CheckedInputStream(inStream, new Adler32());
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(checksum));
			ZipEntry entry=zis.getNextEntry();
			while ((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " + entry);
				int count;
				byte data[] = new byte[BUFFER];
				// write the files to the disk
				FileOutputStream fos = new FileOutputStream(entry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
			zis.close();
			System.out.println("Checksum: " + checksum.getChecksum().getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void a() throws Exception{
		
//		String exceltemplatepath = request.getSession().getServletContext().getRealPath("95ExcelTemplate");
		
		writeImageToDisk(getImageFromNetByUrl("http://127.0.0.1:8081/Mat/image/1.jpg"),"zhangsan1.jpg");
		writeImageToDisk(getImageFromNetByUrl("http://127.0.0.1:8081/Mat/image/2.jpg"),"zhangsan2.jpg");
		writeImageToDisk(getImageFromNetByUrl("http://127.0.0.1:8081/Mat/image/3.jpg"),"zhangsan3.jpg");
		writeImageToDisk(getImageFromNetByUrl("http://127.0.0.1:8081/Mat/image/4.jpg"),"zhangsan4.jpg");
		zipDIR("e:\\file","e:\\123.zip");
	}
	
	//////////////////////////////////////image download local desk
	public void writeImageToDisk(byte[] img, String fileName){    
        try {    
            File file = new File("e:\\file\\" + fileName);    
            FileOutputStream fops = new FileOutputStream(file);    
            fops.write(img);    
            fops.flush();    
            fops.close();    
            System.out.println("图片已经写入到D盘");    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }    
	public byte[] getImageFromNetByUrl(String strUrl){    
        try {    
            URL url = new URL(strUrl);    
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
            conn.setRequestMethod("GET");    
            conn.setConnectTimeout(5 * 1000);    
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据    
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据    
            return btImg;   
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return null;    
    }    
	public byte[] readInputStream(InputStream inStream) throws Exception{    
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        while( (len=inStream.read(buffer)) != -1 ){    
            outStream.write(buffer, 0, len);    
        }    
        inStream.close();    
        return outStream.toByteArray();    
    }    
	/////////////////////////////////////////////
	
	
	/**
	 * 压缩文件夹
	 * @param sourceDIR 文件夹名称（包含路径）
	 * @param targetZipFile 生成zip文件名
	 * @author liuxiangwei
	 */
	public void zipDIR(String sourceDIR, String targetZipFile) {
	  try {
		File zip=new File(targetZipFile);
		if (zip.exists()) {
              zip.delete(); // 删除旧的文件
        }
	    FileOutputStream target = new FileOutputStream(targetZipFile);
	    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(target));
	    int BUFFER_SIZE = 1024;
	    byte buff[] = new byte[BUFFER_SIZE];
	    File dir = new File(sourceDIR);
	    if (!dir.isDirectory()) {
	      throw new IllegalArgumentException(sourceDIR+" is not a directory!");
	    }
	    File files[] = dir.listFiles();
	    for (int i = 0; i < files.length; i++) {
	      FileInputStream fi = new FileInputStream(files[i]);
	      BufferedInputStream origin = new BufferedInputStream(fi);
	      ZipEntry entry = new ZipEntry(files[i].getName());
	      out.putNextEntry(entry);
	      int count;
	      while ((count = origin.read(buff)) != -1) {
	        out.write(buff, 0, count);
	      }
	      origin.close();
	    }
	    out.close();
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	}
	
	
	
	
	
	
	
	
	
	
	public void cc(String strUrl) throws Exception{
		byte[] buffer = new byte[1024];
		String strZipName = "Demo.zip";
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));
//		File[] file1 = {new File("result.txt"),new File("source.txt")};
//		for(int i=0;i<file1.length;i++) {
//			FileInputStream fis = new FileInputStream(file1[i]);
		URL url = new URL(strUrl);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
        conn.setRequestMethod("GET");    
        conn.setConnectTimeout(5 * 1000);    
        InputStream inStream = conn.getInputStream();//通过输入流获取图片数据    
        FileInputStream fis = new FileInputStream(strUrl);
			out.putNextEntry(new ZipEntry(strUrl));
			int len;
			//读入需要下载的文件的内容，打包到zip文件
			while((len = fis.read(buffer))>0) {
			out.write(buffer,0,len);
			}
			out.closeEntry();
			fis.close();
//		}
		out.close();
		System.out.println("生成Demo.zip成功");
	}
	
	
	public void deleteAllFilesOfDir(File path) {  
	    if (!path.exists())  
	        return;  
	    if (path.isFile()) {  
	        path.delete();  
	        return;  
	    }  
	    File[] files = path.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        deleteAllFilesOfDir(files[i]);  
	    }  
	    path.delete();  
	}  
	
	
}
