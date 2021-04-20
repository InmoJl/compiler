const PeekIterator      = require('../common/PeekIterator')
const Token             = require('./Token')
const TokenType         = require('./TokenType')
const AlphabetHelper    = require('./AlphabetHelper')
const LexicalException  = require('./LexicalException')

class Lexer {

    analyse(source) {

        const tokens = []
        const it = new PeekIterator(source, '\0')

        while (it.hasNext()) {

            let c = it.next()

            if (c === '\0') break

            const lookahead = it.peek()

            if (c === ' ' || c === '\n' || c === '\r') continue

            if (c === '/') {

                if (lookahead === '/') {
                    while (it.hasNext() && (c = it.next()) !== '\n');
                    continue
                } else if (lookahead === '*') {
                    let vali = false

                    while (it.hasNext()) {
                        const p = it.next()

                        if (p === '*' && it.peek() === '/') {
                            vali = true
                            it.next()
                            break
                        }
                    }

                    if (!vali) {
                        throw new LexicalException('Comments not matched')
                    }

                    continue
                }
            }

            if (c === '{' || c === '}' || c === '(' || c === ')') {
                tokens.push(new Token(TokenType.BRACKET, c))
                continue
            }

            if (c === '"' || c === "'") {
                it.putBack()
                tokens.push(Token.makeString(it))
                continue
            }

            if (AlphabetHelper.isLetter(c)) {
                it.putBack()
                tokens.push(Token.makeVarOrKeyword(it))
                continue
            }

            if (AlphabetHelper.isNumber(c)) {
                it.putBack()
                tokens.push(Token.makeNumber(it))
                continue
            }

            if ((c === '+' || c === '-' || c === '.') && AlphabetHelper.isNumber(lookahead)) {

                const lastToken = tokens[tokens.length - 1] || null

                // 前面没有值 或者前面不是一个变量
                if (lastToken == null || !lastToken.isValue()) {
                    it.putBack()
                    tokens.push(Token.makeNumber(it))
                    continue
                }
            }

            if (AlphabetHelper.isOperator(c)) {
                it.putBack()
                tokens.push(Token.makeOp(it))
                continue
            }

            throw LexicalException.fromChar(c)

        } // end while

        return tokens
    }
}

module.exports = Lexer
