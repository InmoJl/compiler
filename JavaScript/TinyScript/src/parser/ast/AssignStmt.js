const Stmt = require("./Stmt")
const ASTNodeTypes = require('./ASTNodeTypes')

class AssignStmt extends Stmt {
    constructor(parent, type, label) {
        super(parent, ASTNodeTypes.ASSIGN_STMT, 'assign')
    }
}

module.exports = AssignStmt
