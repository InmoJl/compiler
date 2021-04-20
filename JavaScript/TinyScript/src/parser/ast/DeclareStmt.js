const Stmt = require("./Stmt")
const ASTNodeTypes = require('./ASTNodeTypes')

class DeclareStmt extends Stmt {
    constructor(parent, type, label) {
        super(parent, ASTNodeTypes.DECLARE, 'declare')
    }
}

module.exports = DeclareStmt
