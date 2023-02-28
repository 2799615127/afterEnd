package com.xiaoming.text;

/***
 * 二叉树的遍历流程  
 * 		根节点先绘画left第一个子树=>绘画left第二个子树=>绘画left第三个叶节点，
 * 		再返回到left第二子树执行right第三叶节点，left第二子树再返回到left第一子树执行第二right子树
 * 		，第二right子树=>绘画第二right中的left第三个叶节点，再返回到第二right子树=>绘画第二right中的right第三个叶节点
 *      再返回第二right子树，再返回到根节点执行第一right子树。
 *      
 *      
 *      要领: 第一left:先执行第一left子树，再把所有left子树和节点执行完，再把所有right子树和节点执行完
 *       第一right:再执行第一right子树，再把所有left子树和节点执行完，再把所有right子树和节点执行完
 * @author 86155
 *
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
      //先需要创建一颗二叉树
      BinaryTree binaryTree=new BinaryTree();
      //创建需要的结点
        HeroNode root=new HeroNode(1,"宋江");
        HeroNode node2=new HeroNode(2,"吴用");
        HeroNode node3=new HeroNode(3,"卢俊义");
        HeroNode node4=new HeroNode(4,"林冲");
        HeroNode node5=new HeroNode(5,"武松");
        HeroNode node6=new HeroNode(6,"鲁智深");
        HeroNode node7=new HeroNode(7,"杨志");
 
        //说明，我们先手动创建该二叉树，后面我们学习递归的方式创建而常数
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);
        node3.setRight(node7);
 
        binaryTree.setRoot(root);
 
        //测试：
        System.out.println("前序遍历");//1,2,4,5,3,6,7
        binaryTree.preOrder();
 
        System.out.println("中序遍历");//4,2,5,1,6,3,7
        binaryTree.infixOrder();
        System.out.println("后序遍历");//4,5,2,6,7,3,1
        binaryTree.postOrder();
    }
}
 
//定义二叉树
class BinaryTree{
    private HeroNode root;//根节点
 
    public void setRoot(HeroNode root){
        this.root=root;
    }
    //前序遍历
    public void preOrder(){
        if(this.root!=null){
            this.root.preOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }
    //后序遍历
    public void infixOrder(){
        if(this.root!=null){
            this.root.infixOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }
    //后序遍历
    public void postOrder(){
        if(this.root!=null){
            this.root.postOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }
 
}
 
//先创建HeroNode结点
class HeroNode{
    private int no;
    private String name;
    private HeroNode left;//默认为null
    private HeroNode right;//默认为null
 
    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }
 
    public int getNo() {
        return no;
    }
 
    public void setNo(int no) {
        this.no = no;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public HeroNode getLeft() {
        return left;
    }
 
    public void setLeft(HeroNode left) {
        this.left = left;
    }
 
    public HeroNode getRight() {
        return right;
    }
 
    public void setRight(HeroNode right) {
        this.right = right;
    }
 
    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + "]";
    }
 
    //编写前序遍历的方法
    public void preOrder(){
        System.out.println(this);//先输出父结点(谁调用这个方法，谁就是this)
        //递归向左子树前序遍历
        if(this.left!=null){
            this.left.preOrder();
        }
        //递归向右子树前序遍历
        if(this.right!=null){
            this.right.preOrder();
        }
    }
    //编写中序遍历的方法
    public void infixOrder(){
        //递归向左子树中序遍历
        if(this.left!=null){
            this.left.infixOrder();
        }
        //输出父结点
        System.out.println(this);
        //递归向右子树中序遍历
        if(this.right!=null){
            this.right.infixOrder();
        }
    }
    //编写后序遍历的方法
    public void postOrder(){
        //递归向左子树后序遍历
        if(this.left!=null){
            this.left.postOrder();
        }
        //递归向右子树后序遍历
        if(this.right!=null){
            this.right.postOrder();
        }
        //输出父结点
        System.out.println(this);
    }
}