package TEST;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class adf {
		public static void deleteAllFilesOfDir(String path){
			File file=new File(path);
			deleteAllFiles(file);
			file.mkdir();
		}
		public static void deleteAllFiles(File path) {  
		    if (!path.exists())  
		        return;  
		    if (path.isFile()) {  
		        path.delete();  
		        return;  
		    }  
		    File[] files = path.listFiles();  
		    for (int i = 0; i < files.length; i++) {  
		        deleteAllFiles(files[i]);  
		    }  
		    path.delete();  
		}  
		@Test
		public void aa(){
			String aa="{\"hphm\":\"\",\"hplxid\":\"\",\"wfdm\":\"\",\"cjdz\":\"\"}";
			Map map = (Map)JSON.parse(aa);
			System.out.println(map);
		}
		
		
		
		
}
