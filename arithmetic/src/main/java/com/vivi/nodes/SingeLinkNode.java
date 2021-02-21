package nodes;


 class Nodes{
    int data;
    Nodes next;
}
public class SingeLinkNode {


    public static void main(String[] args) {
        Nodes nodes = new Nodes();
        nodes.data=1;
        nodes.next=new Nodes();
        nodes.next.data=2;
        nodes.next.next=new Nodes();
        nodes.next.next.data=3;

        Nodes s = resvers(nodes);

        while (s !=null) {
            System.out.println(s.data);
            s=s.next;
        }
    }

    public static Nodes resvers(Nodes nodeList){
        Nodes head =new Nodes();
        if (nodeList == null){
            System.exit(0);
        }
        //当前节点
        Nodes pre = null;
        //临时变量
        Nodes next = null;
        while (nodeList.next!=null) {
            //一次循环   //next = 1节点.next（2节点）     //2次循环 next = 2结点.next 3结点
            next = nodeList.next;
            //1节点.next=null                        //2结点.next=pre 1结点 完成2->1
            nodeList.next = pre;
            //pre=1结点                               //pre =2结点
            pre = nodeList;
            //node = 2结点
            nodeList = next;                         //node =3 结点
        }
        return pre;
//            while(nodeList.next!=null){
//                resvers(nodeList.next);
//                head.next=nodeList;
//
//            }

    }
}
