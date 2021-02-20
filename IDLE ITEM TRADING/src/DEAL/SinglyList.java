package DEAL;

public class SinglyList<T> extends Object
{
    public Node<T> head;                       //头指针，指向单链表的头结点
    
    //（1）构造方法
    public SinglyList()                                    //构造方法，构造空单链表
    {
        this.head = new Node<T>();                         //创建头结点，data和next值均为null
    }
    
    //构造单链表，由values数组提供元素，忽略其中空对象。采用尾插入，单链表元素次序与数组元素次序相同
    public SinglyList(T[] values)
    {
        this();                                            //创建空单链表，只有头结点
        Node<T> rear=this.head;                            //rear指向单链表最后一个结点
        for (int i=0; i<values.length; i++)                //若values.length==0，构造空链表
            if (values[i]!=null)
            {
                rear.next=new Node<T>(values[i],null);     //尾插入，创建结点链入rear结点之后
                rear = rear.next;                          //rear指向新的链尾结点
            }
    }

    public boolean isEmpty()                               //判断单链表是否空，O(1)
    {
        return this.head.next==null;
    }

    //（2）存取
    public T get(int i)                                    //返回第i个元素，0≤i<表长度。若i越界，返回null。O(n)
    {
        Node<T> p=this.head.next;
        for (int j=0; p!=null && j<i; j++)//遍历单链表，寻找第i个结点（p指向）
        	p = p.next;
        return (i>=0 && p!=null) ? p.data : null;          //若p指向第i个结点，返回其元素值
    }
   
      //设置第i个元素为x，0≤i<n。若i越界，抛出序号越界异常；若x==null，抛出空对象异常。O(n)
    public void set(int i, T x)
    {
        if (x==null)
            throw new NullPointerException("x==null");     //抛出空对象异常
        Node<T> p=this.head.next;
        for (int j=0; p!=null && j<i; j++)                 //遍历寻找第i个结点（p指向）
            p = p.next;
        if (i>=0 && p!=null)
            p.data = x;                                    //p指向第i个结点
        else throw new IndexOutOfBoundsException(i+"");    //抛出序号越界异常
    }     
    
    //返回单链表所有元素的描述字符串，形式为“(,)”。覆盖Object类的toString()方法，O(n)
 public String toString()
    {
        String str=this.getClass().getName()+"(";          //返回类名
        for (Node<T> p=this.head.next;  p!=null;  p=p.next)//p遍历单链表
        {   str += p.data.toString();
            if (p.next!=null) 
                str += ",";                                //不是最后一个结点时，加分隔符
        }
        return str+")";                                    //空表返回()
    }

    public int size()                                      //返回单链表长度，O(n)
    {
        int i=0; 
        for (Node<T> p=this.head.next;  p!=null;  p=p.next) //p遍历单链表
            i++;
        return i;
    }
    
    //（3）插入
    //插入x作为第i个元素，x!=null，返回插入结点。
    //对序号i采取容错措施，若i<0，插入x在最前；若i>n，插入x在最后。O(n)
    public Node<T> insert(int i, T x)
    {
        if (x==null)
            throw new NullPointerException("x==null");     //抛出空对象异常
        Node<T> front=this.head;                           //front指向头结点
        for (int j=0;  front.next!=null && j<i;  j++)      //寻找第i-1个或最后一个结点（front指向）
            front = front.next;
        front.next = new Node<T>(x, front.next);           //在front之后插入值为x结点，包括头插入、中间/尾插入
        return front.next;                                 //返回插入结点
    }
    public Node<T> insert(T x)                             //在单链表最后添加x对象，O(n)
    {
        return insert(Integer.MAX_VALUE, x);
       //调用insert(i,x)，用整数最大值指定插入在最后，遍历一次，i必须容错
    }
      
    //（4）删除
    public T remove(int i)    //删除第i个元素，0≤i<n，返回被删除元素；若i越界，返回null。O(n)
    {
        Node<T> front=this.head;                           //front指向头结点
        for (int j=0;  front.next!=null && j<i;  j++)      //遍历寻找第i-1结点（front指向）
            front = front.next;
        if (i>=0 && front.next!=null)                      //若front的后继结点存在，则删除之
        {
            T old = front.next.data;                       //获得待删除结点引用的对象
            front.next = front.next.next;                  //删除front的后继结点，包括头删除、中间/尾删除
                                                           //由Java虚拟机稍后释放结点占用存储单元
            return old;
        }
        return null;                                       //若i<0或i>表长
    }

    public void clear()                                    //删除单链表所有元素
    {
        this.head.next=null;                               //Java自动收回所有结点占用的存储空间
    }

    //（5）查找
    //功能及参数：返回首个与key相等元素结点，若查找不成功返回null
    //特殊情况：若key为空对象，Java将抛出空对象异常
    //算法及效率：顺序查找，O(n)
    public Node<T> search(T key) 
    {
        for (Node<T> p=this.head.next;  p!=null;  p=p.next)
            if (key.equals(p.data))                        //执行T类的equals(Object)方法，运行时多态
                return p;
        return null;
    }

    
    public T remove(T key)             //删除首个与key相等元素结点，返回被删除元素；查找不成功返回null。O(n)散列表用
    {
        Node<T> front=this.head, p=front.next;
        while (p!=null && !key.equals(p.data))             //顺序查找首次出现的与key相等元素
        {
            front = p;                                     //front指向p的前驱结点
            p=p.next;
        }
        if (p!=null)                                       //若查找成功，删除front的后继（p结点）
        {
            front.next = p.next;                           //包括头删除、中间/尾删除
            return p.data;
        }
        return null;
    }
     
}
