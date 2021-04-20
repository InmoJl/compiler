const Enum = require('../../common/Enum')

const ASTNodeTypes = {
    BLOCK: new Enum('BLOCK', 1),
    BINARY_EXPR: new Enum('BINARY_EXPR', 2),
    UNARY_EXPR: new Enum('UNARY_EXPR', 3),
    DECLARE: new Enum('DECLARE', 4),
    VARIABLE: new Enum('VARIABLE', 5),
    SCALAR: new Enum('SCALAR', 6),
    IF_STMT: new Enum('IF_STMT', 7),
    WHILE_STMT: new Enum('WHILE_STMT', 8),
    FOR_STMT: new Enum('FOR_STMT', 9),
    ASSIGN_STMT: new Enum('ASSIGN_STMT', 10),
    FUNCTION_DECLARE_STMT: new Enum('FUNCTION_DECLARE_STMT', 11)
}

module.exports = ASTNodeTypes

