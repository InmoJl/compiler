package lexer;

import common.AlphabetHelper;
import common.PeekIterator;

public class Token {
    TokenType _type;
    String _value;

    public Token(TokenType type, String value) {
        this._type = type;
        this._value = value;
    }

    public TokenType getType() {
        return this._type;
    }

    public String getValue() { return this._value; }

    @Override
    public String toString() {
        return String.format("type: $%s, value %s", this._type, this._value);
    }

    public boolean isVariable() {
        return this._type == TokenType.VARIABLE;
    }

    // 值类型
    public boolean isScalar() {
        return this._type == TokenType.INTEGER ||
                this._type == TokenType.FLOAT ||
                this._type == TokenType.STRING ||
                this._type == TokenType.BOOLEAN;
    }

    public boolean isNumber() {
        return this._type == TokenType.INTEGER || this._type == TokenType.FLOAT;
    }

    public boolean isOperator() {
        return this._type == TokenType.OPERATOR;
    }


    /**
     * 提取变量或者关键字
     * @param it
     * @return {Token}
     */
    public static Token makeVarOrKeyword(PeekIterator<Character> it) {

        String s = "";

        // 一直读，直到不是字符
        while (it.hasNext())
        {
            // 前瞻一个字符
            var lookahead = it.peek();

            // 如果是一个字符
            if (AlphabetHelper.isLiteral(lookahead)) {
                s += lookahead;
            }
            else {
                break;
            }

            it.next();
        }

        // 对读取出来的字符串进行比较

        // 如果是关键词 or 变量
        if (Keywords.isKeyword(s)) {
            return new Token(TokenType.KEYWORD, s);
        }

        // 如果是布尔值，特殊对待
        if (s.equals("true") || s.equals("false")) {
            return new Token(TokenType.BOOLEAN, s);
        }

        // 变量
        return new Token(TokenType.VARIABLE, s);
    }

    public static Token makeString(PeekIterator<Character> it) throws LexicalException {
        String s = "";
        int state = 0;

        // 状态机
        while (it.hasNext()) {

            char c = it.next();

            switch (state) {
                case 0:
                    state = c == '\"' ? 1 : 2;
                    s += c;
                    break;
                case 1:
                    if (c == '"') {
                        return new Token(TokenType.STRING, s + c);
                    } else {
                        s += c;
                    }
                    break;
                case 2:
                    if (c == '\'') {
                        return new Token(TokenType.STRING, s + c);
                    } else {
                        s += c;
                    }
                    break;
            }
        }

        throw new LexicalException("Unexpected error");
    }

    public static Token makeOp(PeekIterator<Character> it) throws LexicalException {
        int state = 0;

        while (it.hasNext()) {

            char lookahead = it.next();

            switch (state) {
                case 0:
                    switch (lookahead) {
                        case '+':
                            state = 1;
                            break;
                        case '-':
                            state = 2;
                            break;
                        case '*':
                            state = 3;
                            break;
                        case '/':
                            state = 4;
                            break;
                        case '>':
                            state = 5;
                            break;
                        case '<':
                            state = 6;
                            break;
                        case '=':
                            state = 7;
                            break;
                        case '!':
                            state = 8;
                            break;
                        case '&':
                            state = 9;
                            break;
                        case '|':
                            state = 10;
                            break;
                        case '^':
                            state = 11;
                            break;
                        case '%':
                            state = 12;
                            break;
                        case ',':
                            return new Token(TokenType.OPERATOR, ",");
                        case ';':
                            return new Token(TokenType.OPERATOR, ";");
                    }
                    break;
                case 1:
                    if (lookahead == '+') {
                        return new Token(TokenType.OPERATOR, "++");
                    } else if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "+=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "+");
                    }
                case 2:
                    if (lookahead == '-') {
                        return new Token(TokenType.OPERATOR, "--");
                    } else if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "-=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "-");
                    }
                case 3:
                    if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "*=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "*");
                    }
                case 4:
                    if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "/=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "/");
                    }
                case 5:
                    if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, ">=");
                    } else if (lookahead == '>') {
                        return new Token(TokenType.OPERATOR, ">>");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, ">");
                    }
                case 6:
                    if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "<=");
                    } else if (lookahead == '<') {
                        return new Token(TokenType.OPERATOR, "<<");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "<");
                    }
                case 7:
                    if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "==");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "=");
                    }
                case 8:
                    if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "!=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "!");
                    }
                case 9:
                    if (lookahead == '&') {
                        return new Token(TokenType.OPERATOR, "&&");
                    } else if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "&=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "&");
                    }
                case 10:
                    if (lookahead == '|') {
                        return new Token(TokenType.OPERATOR, "||");
                    } else if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "|=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "|");
                    }
                case 11:
                    if (lookahead == '^') {
                        return new Token(TokenType.OPERATOR, "^^");
                    } else if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "^=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "^");
                    }
                case 12:
                    if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "%=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "%");
                    }
            }
        }

        throw new LexicalException("Unexpected error");
    }

    public static Token makeNumber(PeekIterator<Character> it) throws LexicalException {

        String s = "";
        int state = 0;

        while (it.hasNext()) {
            char lookahead = it.peek();

            switch (state) {
                case 0:
                    if (lookahead == '0') {
                        state = 1;
                    } else if (AlphabetHelper.isNumber(lookahead)) {
                        state = 2;
                    } else if (lookahead == '+' || lookahead == '-') {
                        state = 3;
                    } else if (lookahead == '.') {
                        state = 5;
                    }
                    break;
                case 1:
                    if (lookahead == '0') {
                        state = 1;
                    } else if (AlphabetHelper.isNumber(lookahead)) {
                        state = 2;
                    } else if (lookahead == '.') {
                        state = 4;
                    } else {
                        return new Token(TokenType.INTEGER, s);
                    }
                    break;
                case 2:
                    if (AlphabetHelper.isNumber(lookahead)) {
                        state = 2;
                    } else if (lookahead == '.') {
                        state = 4;
                    } else {
                        return new Token(TokenType.INTEGER, s);
                    }
                    break;
                case 3:
                    if (AlphabetHelper.isNumber(lookahead)) {
                        state = 2;
                    } else if (lookahead == '.') {
                        state = 5;
                    } else {
                        throw new LexicalException(lookahead);
                    }
                    break;
                case 4:
                    if (lookahead == '.') {
                        throw new LexicalException(lookahead);
                    } else if (AlphabetHelper.isNumber(lookahead)) {
                        state = 20;
                    } else {
                        return new Token(TokenType.FLOAT, s);
                    }
                    break;
                case 5:
                    if (AlphabetHelper.isNumber(lookahead)) {
                        state = 20;
                    } else {
                        throw new LexicalException(lookahead);
                    }
                    break;
                case 20:
                    if (AlphabetHelper.isNumber(lookahead)) {
                        state = 20;
                    } else if (lookahead == '.') {
                        throw new LexicalException(lookahead);
                    } else {
                        return new Token(TokenType.FLOAT, s);
                    }
                    break;
            }   // end switch

            it.next();
            s += lookahead;

        }   // end while

        throw new LexicalException("Unexpected error");
    }
}

