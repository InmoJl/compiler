
type Next<T> = LinkNode<T> | null

class LinkNode<T> {
    public data: T
    public next: Next<T>

    constructor(data: T, next: Next<T>) {
        this.data = data
        this.next = next
    }
}

class LinkedList<T> {

    private length: number
    private head: LinkNode<T> | null
    private tail: LinkNode<T> | null

    constructor() {

        this.head = null
        this.tail = null
        this.length = 0

    }

    push(data: T) {

        if (data === null) return

        if (this.head === null) {
            this.head = new LinkNode(data, null)
            this.tail = this.head
            this.length ++

            return
        }

        this.tail.next = new LinkNode(data, null)
        this.tail = this.tail.next

        this.length ++
    }

    /**
     * Pop up the last element in 'LinkedList'
     * @return {T | null} If length equal zero, return null, else return the element that pop up
     */
    pop() : T {
        if (this.length === 0) return null

        this.length --

        //
        if (this.head === this.tail) {
            this.head = null
            return this.tail.data
        }

        let tmpHead: LinkNode<T> = this.head
        while ((tmpHead.next !== this.tail) && (tmpHead = tmpHead.next));

        const value = tmpHead.next.data
        tmpHead.next = null
        this.tail = tmpHead

        return value
    }

    size() {
        return this.length
    }

}

export default LinkedList
