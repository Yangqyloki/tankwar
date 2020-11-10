package com.sap.tankwar.manager;

import com.sap.tankwar.utils.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageManager {
    //预加载四个方向的Tank,Bullet图片
    public static BufferedImage redTankU, redTankL, redTankD, redTankR;
    public static BufferedImage blueTankU, blueTankL, blueTankD, blueTankR;

    public static BufferedImage bulletU, bulletL, bulletD, bulletR;
    public static BufferedImage[] explodes = new BufferedImage[16];

    static {
        try {
            redTankU = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            redTankL = ImageUtil.rotateImage(redTankU, -90);
            redTankD = ImageUtil.rotateImage(redTankU, 180);
            redTankR = ImageUtil.rotateImage(redTankU, 90);
            blueTankU = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            blueTankL = ImageUtil.rotateImage(blueTankU, -90);
            blueTankD = ImageUtil.rotateImage(blueTankU, 180);
            blueTankR = ImageUtil.rotateImage(blueTankU, 90);
//			tankU = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
//			tankL = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
//			tankD = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
//			tankR = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/tankR.gif"));

            bulletU = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            bulletL = ImageUtil.rotateImage(bulletU, -90);
            bulletD = ImageUtil.rotateImage(bulletU, 180);
            bulletR = ImageUtil.rotateImage(bulletU, 90);
//			bulletU = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
//			bulletL = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/bulletL.gif"));
//			bulletD = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
//			bulletR = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));


            for (int i = 0; i < 16; i++) {
                explodes[i] = ImageIO.read(ImageManager.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
