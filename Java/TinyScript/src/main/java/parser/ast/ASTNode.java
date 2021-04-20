package parser.ast;

import lexer.Token;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {

    /* 树 */
    protected ArrayList<ASTNode> children = new ArrayList<>();
    protected ASTNode parent;

    /* 关键信息 */
    protected Token lexeme; // 词法单元
    protected String label; // 备注(标签)
    protected ASTNodeTypes type; // 类型

    public ASTNode() {}

    public ASTNode(ASTNode _parent) {
        this.parent = _parent;
    }

    public ASTNode(ASTNode _parent, ASTNodeTypes _type, String _label) {
        this.parent = _parent;
        this.type = _type;
        this.label = _label;
    }

    public ASTNode(ASTNodeTypes _type, String _label) {
        this.type = _type;
        this.label = _label;
    }

    /**
     * 获取子节点
     * @param index 获取的 children 下标
     * @return {ASTNode}
     */
    public ASTNode getChild(int index) {
        return this.children.get(index);
    }

    /**
     * 添加子节点
     * @param node 抽象语法树节点
     */
    public void addChild(ASTNode node) {
        this.children.add(node);
    }

    /**
     * 获取语法单元
     * @return {Token}
     */
    public Token getLexeme() {
        return this.lexeme;
    }

    /**
     * 获取节点树
     * @return {List<ASTNode>}
     */
    public List<ASTNode> getChildren() {
        return this.children;
    }

    public ASTNodeTypes getType() {
        return this.type;
    }

    public void setType(ASTNodeTypes type) {
        this.type = type;
    }

    public void setLexeme(Token token) {
        this.lexeme = token;
    }

    public void setLabel(String s) {
        this.label = s;
    }

    public void print(int indent) {
        if(indent == 0) {
            System.out.println("print:" + this);
        }

        System.out.println(StringUtils.leftPad(" ", indent *2) + label);
        for(var child : children) {
            child.print(indent + 1);
        }
    }

}
