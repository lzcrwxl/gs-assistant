package com.fbee.modules.core.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import com.fbee.modules.core.Log;
import com.fbee.modules.core.bean.CaptchaCode;

/**
 * 生成干扰线的图像验证码
 *
 * @ClassName: GenerateCaptcha
 * @Description: TODO
 * @author 贺章鹏
 * @date 2016年12月9日 下午2:54:50
 *
 */
public class GenerateCaptcha {

	public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";// 放到session中的key
	private Random random = new Random();
	private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";// 随机产生的字符串
	private String randString1 = "23456789ABCDEFGHJKMNPQRSTUVWXYZ";// 随机产生的字符串
	private String randResetString = "0123456789";// 随机产生的数字
	private int width = 100;// 图片宽
	private int height = 26;// 图片高
	// private int lineSize = 40;// 干扰线数量
	private int lineSize = 1;// 干扰线数量
	private int def_randNum = 5;// 默认：随机产生字符数量
	private long def_liveTime = 5 * 60000;// 默认：图形验证码生存时间
	private int def_randNum1 = 4;// 默认：随机产生字符数量

	/*
	 * 获得字体
	 */
	private Font getFont() {
		return new Font("Fixedsys", Font.CENTER_BASELINE, 23);
	}

	/*
	 * 获得颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * 生成随机图片
	 */
	public String getRandcodeImageIO(HttpServletRequest request, HttpServletResponse response, Integer randNum,
			Long liveTime) {
		HttpSession session = SessionUtils.getSession(request);
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drowLine(g);
		}
		// 绘制随机字符
		if (randNum == null) {
			randNum = def_randNum;
		}
		if (liveTime == null) {
			liveTime = def_liveTime;
		}
		String randomString = "";
		for (int i = 1; i <= randNum; i++) {
			randomString = drowString(g, randomString, i);
		}
		session.removeAttribute(RANDOMCODEKEY);

		Log.info(new StringBuilder().append("get captcha code:").append(randomString));

		CaptchaCode capCode = new CaptchaCode(randomString, new Date().getTime(), liveTime);

		session.setAttribute(RANDOMCODEKEY, capCode);
		g.dispose();
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());// 将内存中的图片通过流动形式输出到客户端
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成随机图片
	 */
	public CaptchaCode getRandcodeBase64(HttpServletRequest request, HttpServletResponse response, Integer randNum,
			Long liveTime) {
		HttpSession session = SessionUtils.getSession(request);
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drowLine(g);
		}
		// 绘制随机字符
		if (randNum == null) {
			randNum = def_randNum;
		}
		if (liveTime == null) {
			liveTime = def_liveTime;
		}
		String randomString = "";
		for (int i = 1; i <= randNum; i++) {
			randomString = drowString(g, randomString, i);
		}
		session.removeAttribute(RANDOMCODEKEY);

		Log.info(new StringBuilder().append("get captcha code:").append(randomString));

		CaptchaCode capCode = new CaptchaCode(randomString, new Date().getTime(), liveTime);

		session.setAttribute(RANDOMCODEKEY, capCode);
		g.dispose();
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "jpeg", out);
			byte[] imageBytes = out.toByteArray();
			capCode.setImage(Base64.encodeBase64String(imageBytes));
			return capCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成随机4位图片
	 */
	public String getRandFourcodeBase64(HttpServletRequest request, HttpServletResponse response, Integer randNum,
			Long liveTime) {
		HttpSession session = SessionUtils.getSession(request);
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 20));
		// 绘制随机字符
		if (randNum == null) {
			randNum = def_randNum1;
		}
		if (liveTime == null) {
			liveTime = def_liveTime;
		}
		String randomString1 = "";
		for (int i = 1; i <= randNum; i++) {
			// randomString1 = drowFourString(g, randomString1, i);
			g.setFont(getFont());
			g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
			String rand = String.valueOf(getFourRandomString(random.nextInt(randString1.length())));
			randomString1 += rand;
			g.translate(random.nextInt(3), random.nextInt(3));
			g.drawString(rand, 13 * i, 16);
		}
		// 绘制曲线
		for (int i = 0; i < 1; i++) {
//			int x = random.nextInt(width);
//			int y = random.nextInt(height);
			int x1 = 80 + random.nextInt(width);
			int y1 = 20 + random.nextInt(height);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3.0f));
			g.setColor(new Color(random.nextInt(240), random.nextInt(255), random.nextInt(240)));
			g.drawArc(5, 5, x1, y1, 20, 160);
		}

		// 设置干扰线
		// for (int i = 0; i < 1; i++) {
		// int xs = random.nextInt(width);
		// int ys = random.nextInt(height);
		//// int xe = xs + random.nextInt(width);
		//// int ye = ys + random.nextInt(height);
		// Graphics2D g2 = (Graphics2D)g;
		// g2.setStroke(new BasicStroke(4.0f));
		// g.setColor(getRandColor(1, 255));
		// g.drawLine(xs, ys, 90, 5);
		// }
		// 添加噪点
		for (int i = 0; i < 200; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			image.setRGB(x, y, random.nextInt(255));
		}

		session.removeAttribute(RANDOMCODEKEY);

		Log.info(new StringBuilder().append("get captcha code:").append(randomString1));

		CaptchaCode capCode = new CaptchaCode(randomString1, new Date().getTime(), liveTime);

		session.setAttribute(RANDOMCODEKEY, capCode);
		g.dispose();
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "jpeg", out);
			byte[] imageBytes = out.toByteArray();
			return Base64.encodeBase64String(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取4位随机数字
	 *
	 */
	public String getRandResetcodeBase64(HttpServletRequest request, HttpServletResponse response, Integer randNum,
			Long liveTime) {
		HttpSession session = SessionUtils.getSession(request);
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(getRandColor(110, 133));
		// Graphics2D g2 = (Graphics2D)g;
		// g2.setBackground(Color.red);
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drowLine(g);
		}
		// 绘制随机字符
		if (randNum == null) {
			randNum = def_randNum;
		}
		if (liveTime == null) {
			liveTime = def_liveTime;
		}
		String randomResetString = "";
		for (int i = 1; i <= randNum; i++) {
			randomResetString = drowNum(g, randomResetString, i);
		}
		session.removeAttribute(RANDOMCODEKEY);

		Log.info(new StringBuilder().append("get captcha code:").append(randomResetString));

		CaptchaCode capCode = new CaptchaCode(randomResetString, new Date().getTime(), liveTime);
		session.setAttribute(RANDOMCODEKEY, capCode);
		g.dispose();
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "jpeg", out);
			byte[] imageBytes = out.toByteArray();
			return Base64.encodeBase64String(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 绘制字符串
	 */
	private String drowString(Graphics g, String randomString, int i) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
		randomString += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 13 * i, 16);
		return randomString;
	}

	/*
	 * 绘制4位字符串
	 */
	private String drowFourString(Graphics g, String randomString, int i) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		String rand = String.valueOf(getFourRandomString(random.nextInt(randString1.length())));
		randomString += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 13 * i, 16);
		return randomString;
	}

	/**
	 * 绘制4位数字
	 */
	private String drowNum(Graphics g, String randomString, int i) {
		g.setFont(getFont());
		// g.setColor(new Color(random.nextInt(101), random.nextInt(111),
		// random.nextInt(121)));
		g.setColor(Color.RED);
		String rand = String.valueOf(getRandomResetString(random.nextInt(randResetString.length())));
		randomString += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 13 * i, 16);
		return randomString;
	}
	/*
	 * 绘制干扰线
	 */

	private void drowLine(Graphics g) {
		// int x = random.nextInt(width);
		// int y = random.nextInt(height);
		// int x = 10;
		// int y = 10;
		// int xl = 80;
		// int yl = 5;
		// g.drawLine(x, y, x + xl, y + yl);
		// 使用绘制矩形，将矩形填充颜色，也可以得到划线效果
		// Graphics2D g2 = (Graphics2D)g;
		// g.setColor(Color.black);
		// g.fillRect(x, y, xl, yl);
		// g2.setBackground(Color.BLACK);
		// g2.fill3DRect(x, y, xl, yl, true);
		Stroke s = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(s);
		Line2D line = new Line2D.Double(30, 50, 100, 200);
		g2.draw(line);
	}

	/*
	 * 绘制噪点
	 */
	private void CreateRandomPoint(int width, int height, int many, Graphics g, int alpha) { // 随机产生干扰点
		for (int i = 0; i < many; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			// g.setColor(getColor(alpha));
			g.drawOval(x, y, random.nextInt(3), random.nextInt(3));
		}
	}

	/*
	 * 获取随机的字符
	 */
	public String getRandomString(int num) {
		return String.valueOf(randString.charAt(num));
	}

	/*
	 * 获取随机4位的字符
	 */
	public String getFourRandomString(int num) {
		return String.valueOf(randString1.charAt(num));
	}

	/*
	 * 获取4位随机的数字
	 */
	public String getRandomResetString(int num) {
		return String.valueOf(randResetString.charAt(num));
	}
}