package TEST;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageTest {
	@Test
public void aa() throws Exception{
		BufferedImage buffImg = ImageIO.read(new File("D:\\temp\\1.jpg"));
		// 各上下行路况图路径
        // 获取层图
        BufferedImage waterImg = ImageIO.read(new File("D:\\temp\\4.jpg"));
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
        int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
        int waterImgHeight = waterImg.getHeight();// 获取层图的高度
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
        // 绘制
        g2d.drawImage(waterImg, 0, 0, waterImgWidth, waterImgHeight, null);
        g2d.dispose();// 释放图形上下文使用的系统资源
        String strlktlj ="D:\\temp\\5.jpg";
     try {
         ImageIO.write(buffImg,"JPEG", new File(strlktlj));
         
     } catch (IOException e1) {
         e1.printStackTrace();
     }
}
}
