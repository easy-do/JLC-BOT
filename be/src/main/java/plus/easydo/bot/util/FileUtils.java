package plus.easydo.bot.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yuzhanfeng
 * @Date 2024-03-30
 * @Description 文件相关自定义工具
 */
@Slf4j
public class FileUtils {

    private FileUtils() {
    }

    public static String getHomePath() {
        ApplicationHome applicationHome = new ApplicationHome(FileUtils.class);
        File homeFile = applicationHome.getSource();
        return homeFile.getParentFile().getPath();
    }

    public static String getCachePath() {
        boolean win = System.getProperty("os.name").toLowerCase().startsWith("win");
        String cachePath;
        if (win) {
            cachePath = getHomePath() + "/cache/dl/";
        } else {
            cachePath = "/data/cache/dl/";
        }
        if (FileUtil.exist(cachePath)) {
            FileUtil.mkdir(cachePath);
        }
        log.debug("获取到的缓存路径为:{}",cachePath);
        return cachePath;
    }

    public static String saveFileToCachePath(byte[] bytes, String fileName) {
        String path = getCachePath();
        String time = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.PURE_DATE_FORMATTER);
        File file = FileUtil.writeBytes(bytes, path + time + "/" + fileName);
        return file.getAbsolutePath();
    }

    public static String copyWcf() throws IOException {
        String homePath = getHomePath();
        log.info("获取到的home路径为:{}",homePath);
        if (!FileUtil.exist(homePath + "/win32-x86-64")) {
            FileUtil.mkdir(homePath + "/win32-x86-64");
        }

        ClassPathResource wcfPathResource = new ClassPathResource("/win32-x86-64/wcf.exe");
        BufferedOutputStream wcfOp = FileUtil.getOutputStream(homePath + "/win32-x86-64/wcf.exe");
        InputStream wcfIp = wcfPathResource.getInputStream();
        IoUtil.copy(wcfIp, wcfOp);
        IoUtil.close(wcfIp);
        IoUtil.close(wcfOp);

        ClassPathResource nngPathResource = new ClassPathResource("/win32-x86-64/nng.dll");
        BufferedOutputStream nngOp = FileUtil.getOutputStream(homePath + "/win32-x86-64/nng.dll");
        InputStream nngIp = nngPathResource.getInputStream();
        IoUtil.copy(nngIp, nngOp);
        IoUtil.close(nngIp);
        IoUtil.close(nngOp);

        ClassPathResource sdkPathResource = new ClassPathResource("/win32-x86-64/sdk.dll");
        BufferedOutputStream sdkOp = FileUtil.getOutputStream(homePath + "/win32-x86-64/sdk.dll");
        InputStream sdkIp = sdkPathResource.getInputStream();
        IoUtil.copy(sdkIp, sdkOp);
        IoUtil.close(sdkIp);
        IoUtil.close(sdkOp);

        ClassPathResource spyPathResource = new ClassPathResource("/win32-x86-64/spy.dll");
        BufferedOutputStream spyOp = FileUtil.getOutputStream(homePath + "/win32-x86-64/spy.dll");
        InputStream spyIp = spyPathResource.getInputStream();
        IoUtil.copy(spyIp, spyOp);
        IoUtil.close(spyIp);
        IoUtil.close(spyOp);

        ClassPathResource spyDebugPathResource = new ClassPathResource("/win32-x86-64/spy_debug.dll");
        BufferedOutputStream spyDebugOp = FileUtil.getOutputStream(homePath + "/win32-x86-64/spy_debug.dll");
        InputStream spyDebugIp = spyDebugPathResource.getInputStream();
        IoUtil.copy(spyDebugIp, spyDebugOp);
        IoUtil.close(spyDebugIp);
        IoUtil.close(spyDebugOp);

        return homePath + "/win32-x86-64/wcf.exe";
    }

    public static String copyJython() {
        String homePath = getHomePath();
        try {
            String os = System.getProperty("os.name");
            String installPath;
            String jythonPath;
            if (os.toLowerCase().startsWith("win")) {
                installPath = homePath + "/jython-installer-2.7.3.jar";
                jythonPath = homePath + "/jython2.7.3";
                log.info("当前系统为Windows,首次运行请使用 <jython-install.bat> 安装jython环境");
            }else {
                installPath = homePath + "/data/jython-installer-2.7.3.jar";
                jythonPath = "/data/jython2.7.3";
                log.info("docker环境下需手动执行命令<docker exec -it jlc-bot java -jar /data/jython-installer-2.7.3.jar>安装jython环境");
            }
            if (!FileUtil.exist(jythonPath)) {
                FileUtil.mkdir(jythonPath);
            }
            if (FileUtil.exist(installPath)) {
                return jythonPath;
            }
            ClassPathResource installResource = new ClassPathResource("/lib/jython-installer-2.7.3.jar");
            //将安装文件复制出来
            BufferedOutputStream tempPathOp = FileUtil.getOutputStream(installPath);
            InputStream zipIp = installResource.getInputStream();
            IoUtil.copy(zipIp, tempPathOp);
            IoUtil.close(zipIp);
            IoUtil.close(tempPathOp);
            log.info("已将Jython安装程序复制到路径:{}",installPath);
            return jythonPath;
        }catch (Exception e){
            log.error("复制jython文件失败:{}",e.getMessage());
        }
        return homePath;
    }

}
