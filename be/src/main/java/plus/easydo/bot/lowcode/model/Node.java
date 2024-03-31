package plus.easydo.bot.lowcode.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2024-03-04
 * @Description 节点对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    //节点id
    @JsonProperty("id")
    private String id;
    //节点类型编号 edg 是连线 其他是自定义节点
    @JsonProperty("shape")
    private String shape;
    //显示名
    @JsonProperty("label")
    private String label;
    @JsonProperty("position")
    private Map<String, Integer> position;
    @JsonProperty("size")
    private Map<String, Integer> size;
    @JsonProperty("view")
    private String view;
    @JsonProperty("color")
    private String color;
    @JsonProperty("ports")
    private Port ports;
    @JsonProperty("zIndex")
    private Integer zIndex;
    // 如果是连线不为空
    @JsonProperty("source")
    private EdgInfo source;
    // 如果是连线不为空
    @JsonProperty("target")
    private EdgInfo target;

    @Data
    @NoArgsConstructor
    public static class EdgInfo {
        @JsonProperty("cell")
        private String cell;
        @JsonProperty("port")
        private String port;
    }

    @Data
    @NoArgsConstructor
    public static class Port {
        @JsonProperty("groups")
        private String groups;
        @JsonProperty("items")
        private List<Item> items;

        @Data
        @NoArgsConstructor
        public static class Item {
            @JsonProperty("group")
            private String group;
            @JsonProperty("id")
            private String id;
        }
    }


}
