const { assert } = require('chai')
const arrayToGenerator = require('../common/arrayToGenerator')
const Token = require('../lexer/Token')
const TokenType = require('../lexer/TokenType')
const PeekIterator = require('../common/PeekIterator')

describe('Token test', () => {

    function assertToken(token, value, type) {
        assert.equal(token.getValue(), value)
        assert.equal(token.getType(), type)
    }

    it('test_varOrKeyword', () => {

        const it1 = new PeekIterator(arrayToGenerator([..."if abc"]))
        const it2 = new PeekIterator(arrayToGenerator([..."true abc"]))

        const token1 = Token.makeVarOrKeyword(it1)
        const token2 = Token.makeVarOrKeyword(it2)
        it1.next()
        const token3 = Token.makeVarOrKeyword(it1)

        assertToken(token1, 'if', TokenType.KEYWORD)
        assertToken(token2, 'true', TokenType.BOOLEAN)
        assertToken(token3, 'abc', TokenType.VARIABLE)
    })

    it('test_string', () => {

        const tests = ["'123'", '"123"']

        for (let test of tests) {
            const it = new PeekIterator(arrayToGenerator([...test]))
            const token = Token.makeString(it)

            assertToken(token, test, TokenType.STRING)
        }
    })

    it('test_operator', () => {

        const tests = [
            ['+ xxx', '+'],
            ['++xxx', '++'],
            ['/=xxx', '/='],
            ['*=xxx', '*='],
            ['==xxx', '=='],
            ['&= xxx', '&='],
            ['^= xxx', '^='],
            ['% xxx', '%']
        ]

        for (let test of tests) {
            const [input, expected] = test
            const it = new PeekIterator(arrayToGenerator([...input]))
            const token = Token.makeOp(it)
            assertToken(token, expected, TokenType.OPERATOR)
        }
    })

    it('test_number', () => {

        const tests = [
            '000 xxx',
            '0.123 xxx',
            '+0 xxx',
            '-0 xxx',
            '.333 xxx',
            '.5 xxx',
            '135.123 xxx',
            '-100 xxx',
            '+1000 xxx',
            '-124.235 xxx',
            '-123.123*456'
        ]

        for (let test of tests) {
            const it = new PeekIterator(arrayToGenerator([...test]))
            const token = Token.makeNumber(it)
            const [expected] = test.split(/[ *]/)
            const type = test.indexOf('.') === -1 ? TokenType.INTEGER : TokenType.FLOAT
            assertToken(token, expected, type)
        }
    })
})
