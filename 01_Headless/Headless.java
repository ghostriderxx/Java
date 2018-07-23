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
		// �����ߴ�Ϊ 200*30 �Ļ��� 
		BufferedImage image = new BufferedImage(200, 30, BufferedImage.TYPE_INT_RGB);
		
		// ��ȡ�����ͼ�ӿ�
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		// ������ɫ
		g.setColor(Color.ORANGE);
		
		// �������� �����壬�Ƿ�Ӵ֣�б�壬�����С��
		g.setFont(new Font("΢���ź�", Font.BOLD, 20));
		
		// �����ַ��� ���ַ�����xλ�ã�yλ�ã�
		g.drawString("Hello Webj2ee!", 18, 20);
		
		// ����ͼƬ���ļ�
		ImageIO.write(image, "JPEG", new FileOutputStream("webj2ee.jpg"));
	}
}
