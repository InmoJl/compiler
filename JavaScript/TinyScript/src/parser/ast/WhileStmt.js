const Stmt = require("./Stmt")
const ASTNodeTypes = require('./ASTNodeTypes')

class WhileStmt extends Stmt {
    constructor(parent) {
        super(parent, ASTNodeTypes.WHILE_STMT, 'while')
    }
}

module.exports = WhileStmt
