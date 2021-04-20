const ASTNode = require("./ASTNode");

/**
 * Stmt (Statement 语句)
 */
class Stmt extends ASTNode {
    constructor(parent, type, label) {
        super(parent, type, label)
    }
}

module.exports = Stmt
