const ASTNode = require("./ASTNode")
const TokenType = require('../../lexer/TokenType')
const ASTNodeType = require('../ast/ASTNodeTypes')

class Factor extends ASTNode {
    constructor(parent, it) {
        super(parent)

        const token = it.next()
        const type = token.getType()
        
        if (type === TokenType.VARIABLE) {
            this.type = ASTNodeType.VARIABLE
        } else {
            this.type = ASTNodeType.SCALAR
        }

        this.label = token.getValue()
        this.lexeme = token

    }
}

module.exports = Factor
