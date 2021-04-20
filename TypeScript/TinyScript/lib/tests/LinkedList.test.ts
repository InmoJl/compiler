import LinkedList from '../LinkedList/index'
import { assert } from 'chai'

describe('Test_LinkedList', () => {
    it('Test push and pop', () => {

        const linkedList = new LinkedList<string>()
        linkedList.push('a')
        linkedList.push('b')
        linkedList.push('c')
        linkedList.push('d')
        linkedList.push('e')
        assert.equal(linkedList.size(), 5)
        assert.equal(linkedList.pop(), 'e')
        assert.equal(linkedList.pop(), 'd')
        assert.equal(linkedList.pop(), 'c')
        assert.equal(linkedList.pop(), 'b')
        assert.equal(linkedList.pop(), 'a')
        assert.equal(linkedList.pop(), null)

        linkedList.push('ee')
        assert.equal(linkedList.pop(), 'ee')
        assert.equal(linkedList.size(), 0)

        linkedList.push(null)
        assert.equal(linkedList.size(), 0)

    })
})


