package parser.util;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import parser.ast.ASTNode;
import parser.ast.Factor;

import java.util.ArrayList;

public class ParserUtils {
    public static String toPostfixExpression(ASTNode node) {

//        String leftStr = "";
//        String rightStr = "";
//
//        switch (node.getType()) {
//            case BINARY_EXPR:
//                leftStr = toPostfixExpression(node.getChild(0));
//                rightStr = toPostfixExpression(node.getChild(1));
//                return leftStr + " " + rightStr + " " + node.getLexeme().getValue();
//            case VARIABLE:
//            case SCALAR:
//                return node.getLexeme().getValue();
//        }
//
//        throw new NotImplementedException("Not impl.");

        // 如果 node 是 Factor 的实例
        if(node instanceof Factor) {
            // 直接返回
            return node.getLexeme().getValue();
        }

        // 创建个泛型数组
        var parts = new ArrayList<String>();

        // 把所有 child 添加进 parts
        for(var child : node.getChildren()) {
            parts.add(toPostfixExpression(child));
        }

        var lexemeStr = node.getLexeme() != null ? node.getLexeme().getValue() : "";

        if(lexemeStr.length() > 0) {
            return StringUtils.join(parts, " ") + " " + lexemeStr;
        } else {
            return StringUtils.join(parts, " ");
        }
    }
}
