package parser.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 优先级表 二元表达式
public class PriorityTable {

    private List<List<String>> table = new ArrayList<>();

    public PriorityTable() {

        this.table.add(Arrays.asList("&", "|", "^"));
        this.table.add(Arrays.asList("==", "!=", ">", "<", ">=", "<="));
        this.table.add(Arrays.asList("+", "-"));
        this.table.add(Arrays.asList("*", "/"));
        this.table.add(Arrays.asList("<<", ">>"));

    }

    public int size() {
        return this.table.size();
    }

    public List<String> get(int level) {
        return this.table.get(level);
    }

}
