package com.charryteam.charryproject.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xiabaikui on 2015/12/10.
 */
public class ExternalStorageHelper {
    /**1.判断sdcard状态
     * @return
     */
    public static boolean isSDCardMounted() {
        // step1 获取外部存储设备的状态
        String state = Environment.getExternalStorageState();
        // step2:如果state的值media_mounted，表示可用的
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 拿到手机路径app歌曲存储路径
     * @param context
     * @return
     */
    public static String getDirectroy(Context context) {

        String path = null;
        String state = Environment.getExternalStorageState();
        File file = null;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Log.i("tag","=======sdcard");
            file = new File(Environment.getExternalStorageDirectory(), Constants.FILE_NAME);
        } else {
            Log.i("tag","=======手机缓存路径");
            file = new File(Environment.getDownloadCacheDirectory(), Constants.FILE_NAME);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        path = file.getAbsolutePath();
        return path;
    }

    // 2.提供外部存储的根目录
    /**
     * 此方法用于获取根目录,getExternalStorageDirectory()表示获取外部存储的根目录.10-22 08:19:33.482:
     * I/tag(17945): ===外部存储的根目录：/mnt/sdcard/
     *
     *
     * @return
     */
    public static String getExternalStorageRootPath() {
        if (isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    // 3.向外部存储的公共目录下存储数据
    /**
     * 向外部存储的公共目录下存储数据:比如在Downloads目录下存储一个myfile.txt
     *
     * @param type
     *            :公共目录的名字，由系统提供好的常量值
     * @param fileName
     *            ：即将存储的文件的名字
     * @param data
     *            ：要存储的数据
     * @return：是否存储成功
     */
    public static boolean writeToExternalStoragePublicDir(String type, String fileName, byte[] data) {
        // step1:判断外部存储设备是否可用
        if (isSDCardMounted()) {
            // step2:判断指定的公共目录是否存在
            File parentFile = Environment
                    .getExternalStoragePublicDirectory(type);
            if (parentFile.exists()) {
                Log.i("tag", "===公用目录的路径：" + parentFile);
                // step3：创建文件
                File file = new File(parentFile, fileName);
                Log.i("tag", "===file:" + file);
                // step4:打开输出流，用于写数据
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    // step5:写数据
                    fos.write(data);
                    return true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // step6:关闭流
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }

    // 4.从外部存储的公共目录下获取数据
    /**
     *
     * @param type
     *            ：公共目录的类型
     * @param fileName
     *            ：文件名字
     * @return：读取到的数据
     */
    public static byte[] readFromExternalStoragePublicDir(String type, String fileName) {
        // step1:判断sd卡是否可用
        if (isSDCardMounted()) {
            // step2:判断指定的目录是否存在
            File parentFile = Environment
                    .getExternalStoragePublicDirectory(type);
            if (parentFile.exists()) {
                // step3:创建文件，开始读取
                File file = new File(parentFile, fileName);
                FileInputStream fis = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    fis = new FileInputStream(file);
                    byte[] buf = new byte[1024];
                    int len = 0;
                    while ((len = fis.read(buf)) != -1) {
                        baos.write(buf, 0, len);
                    }
                    // 读取完毕，返回数据
                    return baos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    // 5.向外部存储的公共目录的自定义目录下存储数据
    /**
     * 此方法用于实现，将数据保存到外部存储的自定义目录下
     *
     * @param dir
     *            ：自定义文件夹的名称
     * @param fileName
     *            ：文件的名字
     * @param data
     *            ：要存储的数据
     * @return：是否存储成功
     */
    public static boolean writeToExternalStorageCustomDir(String dir, String fileName, byte[] data) {
        // step1:判断sdka是否可用
        if (isSDCardMounted()) {
            // step2：创建自定义的文件夹
            File parentFile = new File(getExternalStorageRootPath()
                    + File.separator + dir);// 创建自定义目录
            if (parentFile != null) {
                // 如果自定义目录不存在，就创建
                parentFile.mkdirs();
            }
            // step3:创建文件
            File file = new File(parentFile, fileName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data);
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    // 6.从自定义目录读取数据
    /**
     * 从自定义目录读取文件
     *
     * 目录：/mnt/sdcard/xxx/xxx.txt
     *
     * @param dir
     *            ：自定义目录的名字
     * @param fileName
     *            ：文件的名字
     * @return：读取到的数据
     */
    public static byte[] readFromExternalStorageCustomDir(String dir,String fileName) {
        if (isSDCardMounted()) {
            // 创建自定义目录：就是根目录加文件夹的名字
            File parentFile = new File(getExternalStorageRootPath(), dir);
            if (parentFile.exists()) {
                File file = new File(parentFile, fileName);
                FileInputStream fis = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    fis = new FileInputStream(file);
                    byte[] buf = new byte[1024];
                    int len = 0;
                    while ((len = fis.read(buf)) != -1) {
                        baos.write(buf, 0, len);
                    }
                    return baos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    // 7.向外部存储的私有目录写数据：被当前的应用能够访问。
    /**
     * 向外部存储的私有目录下写数据:又细分为files,cache
     *
     * @param context
     *            ：上下文环境，因为私有目录属性当前的程序，需要用上下文环境获取
     * @param type
     *            ：私有目录下的目录
     * @param fileName
     *            ：文件名字
     * @param data
     *            ：要存储的数据
     * @return：是否存储成功
     *
     *                外部存储的私有目录：
     *
     *                如果指定了type类型： ===外部存储的私有目录：/mnt/sdcard/Android/data/com.qf.
     *                day14_04_externalstoragedemo/files/Pictures
     *
     *                /mnt/sdcard/Android/data/应用程序包名/files/类型/xx.txt
     *
     *
     *                如果没有指定type类型===file:/mnt/sdcard/Android/data/com.qf.
     *                day14_04_externalstoragedemo/files/
     *
     *                /mnt/sdcard/Android/data/应用程序包名/files/xx.txt
     */
    public static boolean writeToExternalStoragePrivateDir(Context context,String type, String fileName, byte[] data) {
        // step1:判断sd卡是否挂载
        if (isSDCardMounted()) {
            // step2:获取外部存储的私有目录，
            File parentFile = context.getExternalFilesDir(type);
            Log.i("tag", "===外部存储的私有目录：" + parentFile);
            File file = new File(parentFile, fileName);
            Log.i("tag", "===file:" + file);
            // 获取输出流，保存数据
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data);
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    // 8.从外部存储的私有目录下读取数据
    /**
     *
     * @param context
     * @param type
     * @param fileName
     * @return
     */
    public static byte[] readFromExternalStoragePrivateDir(Context context,String type, String fileName) {
        if (isSDCardMounted()) {
            File parentFile = context.getExternalFilesDir(type);
            File file = new File(parentFile, fileName);
            FileInputStream fis = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                fis = new FileInputStream(file);
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = fis.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                return baos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    // 9.获取真个外部存储设备的空间大小
    public static long getTotalSize() {
        int totalSize = 0;
        StatFs statFs = new StatFs(getExternalStorageRootPath()); // 此类用于统计一些系统文件的信息
        if (isSDCardMounted()) {
            // 版本大于18：
            if (Build.VERSION.SDK_INT >= 18) {// 如果版本高于18
                // totalSize = statFs.getToTalBytes();
            } else {
                // 通过控件的块数*每块的大小
                totalSize = statFs.getBlockCount() * statFs.getBlockSize();
            }
        }
        return totalSize / 1024 / 1024;// b-->kb-->mb
    }

    // 10.获取可用空间大小
    public static long getAviableSize() {
        int aviableSize = 0;
        StatFs statFs = new StatFs(getExternalStorageRootPath());
        if (isSDCardMounted()) {
            if (Build.VERSION.SDK_INT >= 18) {
                // aviableSize = statFs.getAvailableBytes();
            } else {
                aviableSize = statFs.getAvailableBlocks()
                        * statFs.getBlockSize();
            }
        }
        return aviableSize / 1024 / 1024;
    }
}
