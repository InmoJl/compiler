const Expr = require('./ast/Expr')
const Scalar = require('./ast/Scalar')
const ASTNodeType = require('./ast/ASTNodeTypes')

class SimpleParser {

    /**
     * 转抽象语法树
     * @param {PeekTokenIterator | PeekIterator} it
     * @return {Expr | Scalar}
     */
    static parse(it) {

        // Expr -> digit + Expr | digit

        const expr = new Expr(null)
        const scalar = new Scalar(expr, it)

        if (!it.hasNext()) {
            return scalar
        }

        expr.addChild(scalar)
        const op = it.nextMatchOfValue('+')
        expr.label = '+'
        expr.type = ASTNodeType.BINARY_EXPR
        expr.lexeme = op
        expr.addChild(SimpleParser.parse(it))

        return expr
    }

}

module.exports = SimpleParser
