package webj2ee;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Headless extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 创建尺寸为 200*30 的画板 
		BufferedImage image = new BufferedImage(200, 30, BufferedImage.TYPE_INT_RGB);
		
		// 获取画板绘图接口
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		// 设置颜色
		g.setColor(Color.ORANGE);
		
		// 设置字体 （字体，是否加粗／斜体，字体大小）
		g.setFont(new Font("微软雅黑", Font.BOLD, 20));
		
		// 绘制字符串 （字符串，x位置，y位置）
		g.drawString("Hello Webj2ee!", 18, 20);
		
		// 保存图片到文件
		ImageIO.write(image, "JPEG", new FileOutputStream("webj2ee.jpg"));
	}
}
