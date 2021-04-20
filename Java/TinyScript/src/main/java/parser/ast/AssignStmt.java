package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class AssignStmt extends Stmt {

    public AssignStmt() {
        super(ASTNodeTypes.ASSIGN_STMT, "assign");
    }

    /**
     * 分配语句
     * @param parent 父节点
     * @param it     迭代器
     * @return       parse好的语句
     * @throws ParseException 如果不是标量或者变量
     */
    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {

        // 创建语法声明
        var stmt = new AssignStmt();

        // parse 会吃掉一个 token，所以得先保留
        var tmpToken = it.peek();
        var factor = Factor.parse(it);

        // 不是变量跟标量
        if (factor == null) {
            throw new ParseException(tmpToken);
        }

        // 左节点
        stmt.addChild(factor);

        var lexeme = it.nextMatch("=");
        var expr = Expr.parse(it);

        // 右节点
        stmt.addChild(expr);
        stmt.setLexeme(lexeme);

        return stmt;
    }

}
