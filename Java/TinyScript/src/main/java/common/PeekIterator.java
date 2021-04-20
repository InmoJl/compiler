package common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

public class PeekIterator<T> implements Iterator<T> {

    private final Iterator<T> it;

    // 缓存流的 n 个元素
    private final LinkedList<T> queueCache = new LinkedList<>();
    // 缓存放回到队列的元素
    private final LinkedList<T> stackPutBacks = new LinkedList<>();
    private final static int CACHE_SIZE = 10;
    private T _endToken = null;

    public PeekIterator(Stream<T> stream) {
        this.it = stream.iterator();
    }

    public PeekIterator(Stream<T> stream, T endToken) {
        this.it = stream.iterator();
        this._endToken = endToken;
    }

    public T peek() {

        // 放回队列是否有值
        if (stackPutBacks.size() > 0) {
            return stackPutBacks.getFirst();
        }

        // 放回队列没有值，并且缓存队列也没有值
        if (!it.hasNext()) {
            return this._endToken;
        }

        // 来到这说明放回队列为空，并且 stream 有值，则读取放到缓存队列
        T val = this.next();
        // 缓存队列增加了，放回队列的值也要增加
        this.putBack();

        return val;
    }


    // cache[a b c d e f]
    // 假如 queueCache: A -> B -> C -> D
    public void putBack() {
        // 边界条件
        if(queueCache.size() > 0) {
            this.stackPutBacks.push(this.queueCache.pollLast());
        }
    }

    @Override
    public boolean hasNext() {
        return _endToken != null || stackPutBacks.size() > 0 || it.hasNext();
    }

    @Override
    public T next() {

        T val = null;

        if (this.stackPutBacks.size() > 0) {
            val = this.stackPutBacks.pop();
        } else {

            if (!it.hasNext()) {
                T tmp = this._endToken;
                this._endToken = null;
                return tmp;
            }

            val = it.next();
        }

        // 如果队列长度大于容量，则弹出最先进来的（先进先出）
        while (queueCache.size() > CACHE_SIZE - 1) {
            queueCache.poll();
        }

        queueCache.add(val);

        return val;
    }

}
