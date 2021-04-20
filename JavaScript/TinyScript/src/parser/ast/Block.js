const Stmt = require("./Stmt")
const ASTNodeTypes = require('./ASTNodeTypes')

class Block extends Stmt {
    constructor(parent, type, label) {
        super(parent, ASTNodeTypes.BLOCK, 'block')
    }
}

module.exports = Block
