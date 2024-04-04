package plus.easydo.bot.util;

import cn.hutool.core.io.FileUtil;

/**
 * @author laoyu
 * @version 1.0
 * @description mysql转h2
 * @date 2024/4/4
 */

public class SqlConvertUtils {


    public static void main(String[] args) {
        String path = "";
        String[] rawSQL = new String(FileUtil.readBytes(path)).split("\\n");
        StringBuilder builder = new StringBuilder();

        for(String line : rawSQL) {
            if(line.contains("CHARACTER SET utf8 COLLATE utf8_general_ci")) {
                line = line.replaceAll("CHARACTER SET utf8 COLLATE utf8_general_ci", "");
            } else if(line.contains("INDEX")) {
                continue;
            } else if(line.contains("IF NOT EXISTS")) {
                line = line.replaceAll("IF NOT EXISTS", "");
            }else if(line.contains("SET FOREIGN_KEY_CHECKS = 0;")) {
                line = line.replaceAll("SET FOREIGN_KEY_CHECKS = 0;", "");
            }else if(line.contains("SET NAMES utf8mb4;")) {
                line = line.replaceAll("SET NAMES utf8mb4;", "");
            }else if(line.contains("SET FOREIGN_KEY_CHECKS = 1;")) {
                line = line.replaceAll("SET FOREIGN_KEY_CHECKS = 1;", "");
            } else if(line.contains("--")) {
                continue;
            } else if(line.contains("ENGINE")) {
                line = line.replaceAll("\\).*ENGINE.*(?=)", ");");
            }else if (line.contains("USING BTREE")){
                line = line.replaceAll("USING BTREE*","");
            }

            line = line.replace("`", "");
            builder.append(line).append("\n");
        }
        FileUtil.writeUtf8String(builder.toString(),path+"jlc_bot_h2.sql");
    }

}
