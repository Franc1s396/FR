package DEAL;

public class Node<T> // 单链表结点类，T指定结点的元素类型
{
	public T data; // 数据域，存储数据元素
	public Node<T> next; // 地址域，引用后继结点

	public Node(T data, Node<T> next) // 构造结点，data指定数据元素，next指定后继结点
	{
		this.data = data; // T对象引用赋值
		this.next = next; // Node<T>对象引用赋值
	}

	public Node() {
		this(null, null);
	}

	public String toString() // 返回结点数据域的描述字符串
	{
		return this.data.toString();
	}

	public boolean equals(Object obj) // 比较两个结点值是否相等，覆盖Object类的equals(obj)方法
	{
		return obj == this || obj instanceof Node<?> && this.data.equals(((Node<T>) obj).data);
	}
}
