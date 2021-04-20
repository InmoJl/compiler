
const LinkedList = require('linkedlist')

const CACHE_SIZE = 10
class PeekIterator {

    constructor(it, endToken = null) {
        this.it = it

        this.stackPutBacks = new LinkedList()
        this.queueCache = new LinkedList()

        this.endToken = endToken
    }

    peek() {

        if (this.stackPutBacks.length > 0) {
            return this.stackPutBacks.tail
        }

        const value = this.next()
        this.putBack()

        return value
    }

    putBack() {
        // 从缓存中取值
        if (this.queueCache.length > 0) {
            this.stackPutBacks.push(this.queueCache.pop())
        }
    }

    hasNext() {
        return this.endToken || !!this.peek()
    }

    next() {

        let value = null

        if (this.stackPutBacks.length > 0) {
            value = this.stackPutBacks.pop()
        } else {
            value = this.it.next().value

            if (value === undefined) {
                const tmp = this.endToken
                this.endToken = null
                value = tmp
            }
        }

        while (this.queueCache.length > CACHE_SIZE - 1) {
            this.queueCache.shift()
        }

        this.queueCache.push(value)

        return value
    }

}

module.exports = PeekIterator
