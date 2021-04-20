const Stmt = require("./Stmt")
const ASTNodeTypes = require('./ASTNodeTypes')

class FunctionDeclareStmt extends Stmt {
    constructor(parent, type, label) {
        super(parent, ASTNodeTypes.FUNCTION_DECLARE_STMT, 'function')
    }
}

module.exports = FunctionDeclareStmt
