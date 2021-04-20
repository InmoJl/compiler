const ASTNodeType = require('../ast/ASTNodeTypes')

class ParserUtils {
    static toPostfixExpression(astNode) {

        switch (astNode.type) {
            case ASTNodeType.BINARY_EXPR:
                const leftStr = ParserUtils.toPostfixExpression(astNode.getChild(0))
                const rightStr = ParserUtils.toPostfixExpression(astNode.getChild(1))
                return `${leftStr} ${rightStr} ${astNode.lexeme.getValue()}`
            case ASTNodeType.VARIABLE:
            case ASTNodeType.SCALAR:
                return astNode.lexeme.getValue()
        }

        throw 'Not impl.'
    }
}

module.exports = ParserUtils
