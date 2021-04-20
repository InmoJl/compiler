const ASTNode = require("./ASTNode")
const ASTNodeType = require('./ASTNodeTypes')
const table = require('../util/PriorityTable')
const Variable = require('./Variable')
const Scalar = require('./Scalar')

class Expr extends ASTNode {

    constructor(parent) {
        super(parent)
    }

    static fromToken(parent, type, token) {
        const expr = new Expr(parent)
        expr.label = token.getValue()
        expr.lexeme = token
        expr.type = type

        return expr
    }

    /*
        left: E(k) -> E(k) op(k) E(k + 1) | E(k + 1)
        right:
            E(k) -> E(k + 1) E_(k)
                合并上面表达式: const expr = new Expr();
                              expr.left = E(k + 1);
                              expr.right = E_(k).getChild(0)
                ==> combine (合并)
            E_(k) -> op(k) E(k + 1) E_(k) | ε
                op(k) E(k + 1) E_(k) 能找到值，不会走到 ε，反之亦然
                ==> race（竞争）

         终止条件 (F: Factor, U: Unary)
            E(t) -> F E_(t) | U E_(t)
     */

    /**
     *
     * @param {*} parent
     * @param {*} it
     */
    static parseExpr(parent, it) {
        return Expr.E(parent, it, 0)
    }

    static E(parent, it, k) { 
        // 不是最后一级
        if (k < table.length - 1) {
            return Expr.combine(
                parent, it,
                () => Expr.E(parent, it, k + 1),
                () => Expr.E_(parent, it, k)
            )
        } else {
            return Expr.race(
                it,
                () => {
                    return Expr.combine(
                        parent, it,
                        () => Expr.U(parent, it),
                        () => Expr.E_(parent, it, k)
                    )
                },
                () => {
                    return Expr.combine(
                        parent, it,
                        () => Expr.F(parent, it),
                        () => Expr.E_(parent, it, k)
                    )
                }
            )
        }
    }

    /**
     * @description E_(k) -> op(k) E(k + 1) E_(k) | ε
     * @param parent
     * @param it
     * @param k
     * @return {Expr}
     */
    static E_(parent, it, k) {
        const token = it.peek()
        const value = token.getValue()

        if (table[k].indexOf(value) !== -1) {
            it.nextMatchOfValue(value)
            const expr = Expr.fromToken(parent, ASTNodeType.BINARY_EXPR, token)
            expr.addChild(
                Expr.combine(
                    parent, it,
                    () => Expr.E(parent, it, k + 1),
                    () => Expr.E_(parent, it, k)
                )
            )
            return expr
        }

        return null
    }

    static U(parent, it) {
        const token = it.peek()
        const value = token.getValue()

        if (value === '(') {
            it.nextMatchOfValue('(')
            const expr = Expr.parseExpr(parent, it)
            it.nextMatchOfValue(')')
            
            return expr
        } else if (value === '++' || value === '--' || value === "!") {
            const t = it.peek()
            it.nextMatchOfValue(value)
            const expr = Expr.fromToken(parent, ASTNodeType.UNARY_EXPR, t)
            expr.addChild(Expr.parseExpr(expr, it))

            return expr
        }

        return null
    }

    static F(parent, it) {
        const token = it.peek()

        if (token.isVariable()) {
            return new Variable(parent, it)
        } else {
            return new Scalar(parent, it)
        }
    }

    static combine(parent, it, fnA, fnB) {

        if (!it.hasNext()) return null

        const a = fnA()
        if (a === null) {
            return it.hasNext() ? fnB() : null
        }

        const b = it.hasNext() ? fnB() : null
        if (b === null) return a

        const expr = Expr.fromToken(parent, ASTNodeType.BINARY_EXPR, b.lexeme)
        expr.addChild(a)
        expr.addChild(b.getChild(0))

        return expr
    }

    static race(it, fnA, fnB) {

        if (!it.hasNext()) return null

        const a = fnA()
        if (a === null) {
            return fnB()
        }

        return a
    }
}

module.exports = Expr
