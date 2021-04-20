const { assert } = require('chai')
const Lexer = require('../lexer/Lexer')
const arrayToGenerator = require('../common/arrayToGenerator')
const PeekTokenIterator = require('../parser/util/PeekTokenIterator')
const Expr = require('../parser/ast/Expr')
const ParserUtils = require('../parser/util/ParserUtils')

function createExpr(str) {

    const gen = arrayToGenerator([...str])
    const lexer = new Lexer()
    const tokens = lexer.analyse(gen)
    const tokenIt = new PeekTokenIterator(arrayToGenerator(tokens))

    return Expr.parseExpr(null, tokenIt)
}

describe('ParseExpression', () => {

    it('simple', () => {

        const expr1 = createExpr('1+1+1')
        const expr2 = createExpr('1+2*3')
        const expr3 = createExpr('1*2+3')

        assert.equal(ParserUtils.toPostfixExpression(expr1), '1 1 1 + +')
        assert.equal(ParserUtils.toPostfixExpression(expr2), '1 2 3 * +')
        assert.equal(ParserUtils.toPostfixExpression(expr3), '1 2 * 3 +')

    })

    it('simple2', () => {

        const expr = createExpr('"1" == ""')
        assert.equal(ParserUtils.toPostfixExpression(expr), '"1" "" ==')

    })

    it('complex', () => {

        const expr1 = createExpr('10 * (7 + 4)')
        const expr2 = createExpr('(1*2!=7)==3!=4*5+6')

        assert.equal(ParserUtils.toPostfixExpression(expr1), '10 7 4 + *')
        assert.equal(ParserUtils.toPostfixExpression(expr2), '1 2 * 7 != 3 4 5 * 6 + != ==')

    })

})
