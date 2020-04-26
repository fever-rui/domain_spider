import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadUtils{
    /**
     * 下载图片工具
     *
     * @param urlString
     *            图片链接地址
     * @param filename
     *            图片的文件名字
     * @param savePath
     *            图片保存的路径
     * @throws Exception
     */
    public static void download(String urlString, String filename, String savePath) throws Exception {
        OutputStream os = null;
        InputStream is = null;
        BufferedImage image = null ;
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            // 设置请求头
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
            // 设置请求超时为5s
            con.setConnectTimeout(5 * 1000);

            // 输入流
            is = con.getInputStream();

            byte[]b = new byte[1024];
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            int length = 0;
            while((length=is.read(b))!=-1){
                bout.write(b, 0, length);
            }
            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            image = ImageIO.read(bin);
            paint(image);

//            // 1K的数据缓冲
//            byte[] bs = new byte[1024];
//            // 读取到的数据长度
//            int len;
//            // 输出的文件流
//            File sf = new File(savePath);
//            if (!sf.exists()) {
//                sf.mkdirs();
//            }
//            os = new FileOutputStream(sf.getPath() + "\\" + filename);
//            // 开始读取
//            while ((len = is.read(bs)) != -1) {
//                os.write(bs, 0, len);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
        }

    }

    public static void paint(BufferedImage image){
        ImageIcon icon = new ImageIcon(image);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(200, 300);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) throws Exception {
        download("http://www.beian.gov.cn/common/image.jsp?t=2", null, null);
    }

}
