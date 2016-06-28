package com.zz91.util.file;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zz91.util.lang.StringUtils;

/**
 * 水印工具，目前方法只有在图片上增加文字的方法，其他方法以后添加 2013-11-28
 * 
 * @author kongsj
 *
 */

public class PicMarkUtils {
	/** 图片格式：JPG等 */
	private static final String PICTRUE_FORMATE_JPG = "jpg";
	private static final String PICTRUE_FORMATE_GIF = "gif";
	private static final String PICTRUE_FORMATE_PNG = "png";
	private static final String PICTRUE_FORMATE_JPEG = "jpeg";

	/**
	 * 添加文字水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param color
	 *            字体颜色
	 * @param x
	 *            水印文字距离目标图片左侧的偏移量 x=0, 在正中间 x=-1 在最左 x=1 在最右 其他值 标示在中轴左右附近偏移
	 * @param y
	 *            水印文字距离目标图片上侧的偏移量 y=0, 在正中间 y=-1 在最上 y=1 在最下 其他值 标示在中轴上下附近偏移
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public static Boolean pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize,
			Color color, int x, int y, float alpha) {
		do {
			try {
				// 判断图片格式是否正确
				String[] fileFix = targetImg.split("\\.");
				if (fileFix.length != 2) {
					break;
				}
				String format = "";
				if (PICTRUE_FORMATE_JPG.equals(fileFix[1].toLowerCase())) {
					format = PICTRUE_FORMATE_JPG;
				}
				if (PICTRUE_FORMATE_GIF.equals(fileFix[1].toLowerCase())) {
					format = PICTRUE_FORMATE_GIF;
				}
				if (PICTRUE_FORMATE_PNG.equals(fileFix[1].toLowerCase())) {
					format = PICTRUE_FORMATE_PNG;
				}
				if (PICTRUE_FORMATE_JPEG.equals(fileFix[1].toLowerCase())) {
					format = PICTRUE_FORMATE_JPEG;
				}

				if (StringUtils.isEmpty(format)) {
					break;
				}

				File file = new File(targetImg);
				Image image = ImageIO.read(file);
				int width = image.getWidth(null);
				int height = image.getHeight(null);
				BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = bufferedImage.createGraphics();
				g.drawImage(image, 0, 0, width, height, null);

				// 判断图片是否太小，不够文字现实
				if (pressText.length() * fontSize > width) {
					if (StringUtils.isContainCNChar(pressText)) {
						fontSize = width / pressText.length();
					} else {
						fontSize = width / pressText.length() * 2;
					}
					x = 0;
				}

				g.setFont(new Font(fontName, fontStyle, fontSize));
				g.setColor(color);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

				int width_1 = fontSize * getLength(pressText);
				int height_1 = fontSize;
				int widthDiff = width - width_1;
				int heightDiff = height - height_1;

				if (x == 0) {
					x = widthDiff / 2;
				} else if (x == -1) {
					x = 0;
				} else if (x == 1) {
					x = widthDiff - pressText.length() - fontSize * 4;
				} else {
					x = widthDiff / 2 + x;
				}

				if (y == 0) {
					y = heightDiff / 2;
				} else if (y == -1) {
					y = fontSize;
				} else if (y == 1) {
					y = heightDiff;
				} else {
					y = heightDiff / 2 + y;
				}

				int xResult = x;
				// -pressText.length()*3 >0 ?x-pressText.length()*3:widthDiff;
				int yResult = y;
				// + height_1-2 > 0 ? y + height_1-2:heightDiff;

				g.drawString(pressText, xResult, yResult);
				g.dispose();
				ImageIO.write(bufferedImage, format, file);
				return true;
			} catch (Exception e) {
				break;
			}
		} while (false);
		return false;
	}

	public static Boolean pressTextRightButtom(String targetImg, String pressText, int fontSize, Color color) {
		do {
			try {
				// 判断图片格式是否正确
				String[] fileFix = targetImg.split("\\.");
				if (fileFix.length != 2) {
					break;
				}
				String format = "";
				if (PICTRUE_FORMATE_JPG.equals(fileFix[1].toLowerCase())) {
					format = PICTRUE_FORMATE_JPG;
				}
				if (PICTRUE_FORMATE_GIF.equals(fileFix[1].toLowerCase())) {
					format = PICTRUE_FORMATE_GIF;
				}
				if (PICTRUE_FORMATE_PNG.equals(fileFix[1].toLowerCase())) {
					format = PICTRUE_FORMATE_PNG;
				}
				if (PICTRUE_FORMATE_JPEG.equals(fileFix[1].toLowerCase())) {
					format = PICTRUE_FORMATE_JPEG;
				}

				if (StringUtils.isEmpty(format)) {
					break;
				}

				File file = new File(targetImg);
				Image image = ImageIO.read(file);
				int width = image.getWidth(null);
				int height = image.getHeight(null);
				BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = bufferedImage.createGraphics();
				g.drawImage(image, 0, 0, width, height, null);

				if (width < 640) {
					fontSize = width / 10 - 2;
				}
				if (width > 1024) {
					fontSize = 100;
				}

				g.setFont(new Font("DejaVu Sans Condensed Bold", Font.BOLD, fontSize));
				// g.setFont(dynamicFontPt);
				g.setColor(color);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));

				int width_1 = fontSize * getLength(pressText);
//				int height_1 = fontSize;
				int widthDiff = width - width_1 - 40;
				int heightDiff = height - 10;

				if (width < 640) {
					widthDiff = 0;
				}
				if (height <= 600) {
					if (height<=200) {
						heightDiff = height-height/50;
					}else if(height>500){
						heightDiff = height - height/50;
					}else{
						heightDiff = height - height/30;
					}
				} else if (height > 1000) {
					heightDiff = height - height/40;
				} else {
					heightDiff = height - 20;
				}

				int xResult = widthDiff;
				// -pressText.length()*3 >0 ?x-pressText.length()*3:widthDiff;
				int yResult = heightDiff;
				// + height_1-2 > 0 ? y + height_1-2:heightDiff;

				g.drawString(pressText, xResult, yResult);
				g.dispose();
				ImageIO.write(bufferedImage, format, file);
				return true;
			} catch (Exception e) {
				break;
			}
		} while (false);
		return false;
	}

	public static int getLength(String text) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		return (length % 2 == 0) ? length / 2 : length / 2 + 1;
	}

	public final static void pressImage(String targetImg, String waterImg, int x, int y, float alpha) {
		try {
			File file = new File(targetImg);
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);

			Image waterImage = ImageIO.read(new File(waterImg)); // 水印文件
			int width_1 = waterImage.getWidth(null);
			int height_1 = waterImage.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}
			g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
			g.dispose();
			ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Color color = new Color(128, 128, 128);
		// Color white = new Color(0, 0, 0);
		// Color red = new Color(255, 0, 0);
		// PicMarkUtils.pressText("/usr/data/resources/products/2013/11/29/e5415856-551d-4aa4-8a79-dd970444cadd.jpg","400-444-444-1201","宋体",Font.BOLD,36,red,0,30,(float)
		// 1.0);
		// PicMarkUtils.pressText("/usr/data/resources/products/2013/11/29/e5415856-551d-4aa4-8a79-dd970444cadd.jpg","北京公司","宋体",Font.BOLD,36,white,0,0,(float)
		// 0.4);
		// PicMarkUtils.pressText("/usr/data/resources/products/2013/11/29/e5415856-551d-4aa4-8a79-dd970444cadd.jpg","个体经营（栾依伟）","宋体",Font.BOLD,48,red,0,0,(float)
		// 1.0);
		// PicMarkUtils.pressText("/usr/data/resources/products/2013/11/29/e5415856-551d-4aa4-8a79-dd970444cadd.jpg","http://xianwei.zz91.com","宋体",Font.BOLD,24,white,1,1,(float)
		// 1.0);
		PicMarkUtils.pressTextRightButtom("/home/sj/100393939931_1.jpg", "http://www.zz91.com", 60, Color.blue);
//		PicMarkUtils.pressImage("/home/sj/100474748414_0.jpg", "/home/sj/123.jpg", -1, 50, 1);
	}
}
