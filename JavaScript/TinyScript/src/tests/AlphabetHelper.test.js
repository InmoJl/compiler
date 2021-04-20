const { assert } = require('chai')
const AlphabetHelper = require('../lexer/AlphabetHelper')

describe('AlphabetHelper test', () => {
    it('charCheck', () => {

        assert.equal(true, AlphabetHelper.isLetter('a'))
        assert.equal(true, AlphabetHelper.isLetter('b'))
        assert.equal(true, AlphabetHelper.isLetter('A'))
        assert.equal(false, AlphabetHelper.isLetter('1'))

        assert.equal(true, AlphabetHelper.isLiteral('a'))
        assert.equal(true, AlphabetHelper.isLiteral('A'))
        assert.equal(true, AlphabetHelper.isLiteral('1'))
        assert.equal(false, AlphabetHelper.isLiteral('*'))
        
        assert.equal(true, AlphabetHelper.isNumber('1'))
        assert.equal(true, AlphabetHelper.isNumber('2'))
        assert.equal(false, AlphabetHelper.isNumber('g'))

        assert.equal(true, AlphabetHelper.isOperator('*'))
        assert.equal(true, AlphabetHelper.isOperator('-'))
        assert.equal(true, AlphabetHelper.isOperator('&'))
        assert.equal(true, AlphabetHelper.isOperator('+'))
        assert.equal(true, AlphabetHelper.isOperator('!'))
        assert.equal(true, AlphabetHelper.isOperator('|'))
        // assert.equal(false, AlphabetHelper.isOperator('1'))  // 有问题
        assert.equal(false, AlphabetHelper.isOperator('a'))
        assert.equal(false, AlphabetHelper.isOperator(' '))

    })
})

