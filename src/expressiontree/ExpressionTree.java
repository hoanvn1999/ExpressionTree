
package expressiontree;
//
/*
Author: Pham Le Hoan 
ID: 9634
Class: CMU-CS316MIS
*/


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;


//Tạo class Node
class Node
    {
            Node left;
            Node right;
            String data;
            
            //Hàm khởi tạo mặc định cho lớp Node
            Node() {
                    left = null;
                    right = null;
                    data = "";
            }
            
            //Hàm tạo tùy chỉnh cho lớp Node
            Node(String a) {
                    left = null;
                    right = null;
                    data = a;
            }
            
            Node(String a, Node l, Node r){
                left=l;
                right=r;
                data=a;
            }
    }   
    

public class ExpressionTree extends JFrame implements ActionListener {

    /**
     * @param args the command line arguments
     */   
    
    String iOrder;                                                              //Chuỗi trung tố
    String pOrder;                                                              //Chuỗi hậu tố
    String prOrder;                                                             //Chuỗi tiền tố
    String lNodes;                                                              //Chuỗi chứa các Node lá
    String nlNodes;                                                             //Chuỗi không chứa các Node lá
    
    Node root = null;                                                           //Khởi tạo root cho cây
    Node imageNode = null;                                                      //Global image root node for the image
    
    int numberOfNodes = 0;                                                      //mảng để lưu trữ tọa độ của các nút của cây
    static int counter = 0;
    
    boolean treePainted = false;                                                //biến boolean để chỉ ra rằng cây đã được vẽ
    boolean imagePainted = false;                                               //biến boolean để chỉ ra rằng hình ảnh đã được vẽ
    
    //nút cho các chức năng khác nhau của cây, thoát, đặt lại v.v..
    private JButton tree = new JButton("Tree");                                 
    private JButton result = new JButton("Result");
    private JButton postOrder = new JButton("Post Order");
    private JButton preOrder = new JButton("Pre Order");
    private JButton leafNodes = new JButton("Operand");
    private JButton nonLeafNodes = new JButton("Operators");
    private JButton image = new JButton("Image");
    private JButton author = new JButton("Authors");
    private JButton reset = new JButton("Reset");
    private JButton exit = new JButton("Exit");
    
    //trường văn bản cho các hàm cây khác nhau
    private JTextField f1 = new JTextField(15);
    private JTextField f2 = new JTextField(15);
    private JTextField f3 = new JTextField(15);
    private JTextField f4 = new JTextField(15);
    private JTextField f5 = new JTextField(15);
    private JTextField f6 = new JTextField(15);
    private JTextField f7 = new JTextField(15);
    private JTextField f8 = new JTextField(15);
    
    //Bảng điều khiển chứa các nút
    private JPanel buttonPanel = new JPanel();
    
    //Bảng điều khiển chứa các trường văn bản
    private JPanel fieldPanel = new JPanel();
    Font font = new Font("Verdana", Font.BOLD, 12);
    
    //Panel để hiển thị cây
    private Tree view = new Tree(); 
    
    //Panel để hiển thị hình ảnh cây
    private Tree imageView = new Tree();
    
    public ExpressionTree() {
        //Xây dựng để tạo GUI của lớp
        setTitle("Binary Tree");
        setBackground(Color.white);
        
        //Thêm khung nhìn cây vào khung, thiết lập đường viền và kích thước
        add(view);
        view.setBorder(new TitledBorder("Tree View"));
        view.setPreferredSize(new Dimension(400,400));
        view.setVisible(true);
        view.setBackground(Color.white);
        
        //Thêm bảng hình ảnh cây vào khung, thiết lập đường viền và kích thước
        add(imageView);
        imageView.setBorder(new TitledBorder("Tree Image View"));
        imageView.setPreferredSize(new Dimension(400,400));
        imageView.setVisible(true);
        imageView.setBackground(Color.white);
        
        //Thêm các bảng chứa các nút và các trường văn bản
        add(buttonPanel);
        add(fieldPanel);
        
        //Đặt bố cục cho bảng điều khiển nút và bảng điều khiển trường
        buttonPanel.setLayout(new GridLayout(9,1));
        fieldPanel.setLayout(new GridLayout(9,1));
        
        //Bố cục lưới cho toàn bộ khung
        setLayout(new GridLayout(2,2));
        
        //Đặt bảng màu cho các nút và thêm các trường, nút
        tree.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(tree);
        fieldPanel.add(f1);
        result.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(result);
        fieldPanel.add(f2);
        postOrder.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(postOrder);
        fieldPanel.add(f3);
        preOrder.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(preOrder);
        fieldPanel.add(f4);
        leafNodes.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(leafNodes);
        fieldPanel.add(f5);
        nonLeafNodes.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(nonLeafNodes);
        fieldPanel.add(f6);
        image.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(image);
        fieldPanel.add(f7);
        author.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(author);
        fieldPanel.add(f8);
        buttonPanel.add(reset);
        reset.setBackground(Color.black);
        reset.setForeground(Color.white);
        fieldPanel.add(exit);
        exit.setBackground(Color.black);
        exit.setForeground(Color.white);
        
        //Thêm hành động Trình nghe cho tất cả các nút
        tree.addActionListener(this);
        result.addActionListener(this);
        postOrder.addActionListener(this);
        preOrder.addActionListener(this);
        image.addActionListener(this);
        author.addActionListener(this);
        leafNodes.addActionListener(this);
        nonLeafNodes.addActionListener(this);
        reset.addActionListener(this);
        exit.addActionListener(this);
        
        //Tất cả các nút ngoài Tree, Reset và Exit đều bị tắt theo mặc định
        result.setEnabled(false);
        postOrder.setEnabled(false);
        preOrder.setEnabled(false);
        leafNodes.setEnabled(false);
        nonLeafNodes.setEnabled(false);
        image.setEnabled(false);
        f2.setEditable(false);
        f3.setEditable(false);
        f4.setEditable(false);
        f5.setEditable(false);
        f6.setEditable(false);
        f7.setEditable(false);
        f8.setEditable(false);
        
        //Kết thúc
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public static void main(String[] args) {
        new ExpressionTree();        
    }
    
    
    //TẠO CÂY (Hoan Glad)
    //Kiểm tra toán tử
    private static boolean isOperator(char c) {
        if (c == '^' || c == '*' || c == '/' || c == '+' || c == '-')
            return true;
        return false;
    }
    
    //Kiểm tra độ ưu tiên của toán tử
    private static int priority(char c) {
        if (c == '-' || c == '+') return 1;
        else if (c == '*' || c == '/') return 2;
        else if (c == '^') return 3;
        else return 0;
    }

    //Kiểm tra dấu ngoặc
    private static boolean checkBracket(String str) {
        Stack<Character> st = new Stack<Character>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(')
                st.push(str.charAt(i));
            if (str.charAt(i) == ')')
                if (st.isEmpty())
                    return false;
                else
                    st.pop();
        }
        if (st.isEmpty())
            return true;
        return false;
    }

    //Kiểm tra biểu thức nhập vào đúng hay sai
    private static boolean checkExpression(String str) {
        Stack<Integer> st = new Stack<>();
        if (isOperator(str.charAt(0))) return false;
        String s1 = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isOperator(c)) {
                int t1 = st.pop();
                int t2 = st.pop();
                if (c == '/' && t1 == 0)
                    return false;
                else
                    st.push(1);
            } else if (c == ' ') {
                if (s1 != "") {
                    int x = Integer.parseInt(s1);
                    s1 = "";
                    st.push(x);
                }
            } else {
                s1 = s1 + c;
            }
        }
        int x = st.pop();
        if (st.isEmpty())
            return true;
        return false;
    }
    
    //Duyệt chuỗi nhập vào theo định dạng chuẩn (dùng để định dạng lại biểu thức chứa toán hạng có nhiều chữ số)
    private static String processString(String str){
        String s_out="";
        for (int i=0; i<str.length(); i++){
            if(str.charAt(i)!=' '){
                s_out = s_out + str.charAt(i);
            }
        }
        return s_out;
    }
    
    //Chuyển chuỗi infix sang postfix
    private static String convertInfixToPostfix(String s){
        String in = processString(s), tmp="", out="";
        Stack <Character> st = new Stack<Character>();
        for(int i=0; i<in.length(); i++){
            if(Character.isDigit(in.charAt(i))||Character.isAlphabetic(in.charAt(i)))
                tmp=tmp+in.charAt(i);
            else{
                if(tmp!=""){
                    out = out + tmp + " ";
                    tmp="";
                }
                if(in.charAt(i)=='(')
                    st.push(in.charAt(i));
                else if (in.charAt(i)== ')'){
                    char c;
                    do{
                        c = st.peek();
                        if (c != '(') out = out + st.peek() + " ";
                        st.pop();
                    }while (c!='(');
                }
                else{
                    while (!st.isEmpty()&&priority(st.peek())>=priority(in.charAt(i))){
                        out = out + st.pop() + " ";
                    }
                    st.push(in.charAt(i));
                }
            }
        }
        if(tmp!="")
            out = out + tmp + " ";
        while (!st.isEmpty()){
            out = out + st.pop() + " ";
        }
        s = out;
        return s;
    }
    
    //Tạo cây
    public void createTree(String str){
        Stack<Node> st = new Stack<Node>();
        if(checkBracket(str)) {
            str = convertInfixToPostfix(str);
            if(checkExpression(str)) {
                for (int i = 0; i < str.length(); i++) {
                    if (Character.isAlphabetic(str.charAt(i)) || Character.isDigit(str.charAt(i))) {
                        String tmp = "";
                        for (; (Character.isAlphabetic(str.charAt(i)) || Character.isDigit(str.charAt(i)) || str.charAt(i) == '.'); i++)
                            tmp = tmp + str.charAt(i);
                        Node n = new Node(tmp);
                        st.push(n);
                    } else if (isOperator(str.charAt(i))) {
                        Node r = st.pop();
                        Node l = st.pop();
                        int a = 0;
                        if(str.charAt(i)=='^')
                            a = 3000;
                        else if(str.charAt(i)=='*')
                            a = 2000;
                        else if(str.charAt(i)=='/')
                            a = 2001;
                        else if(str.charAt(i)=='+')
                            a = 1000;
                        else
                            a = 1001;
                        Node operator = new Node(str.charAt(i)+"", l, r);
                        st.push(operator);
                    }
                }
                root = st.pop();
            }
            else JOptionPane.showMessageDialog(null, "Wrong input");
        }
        else JOptionPane.showMessageDialog(null, "Wrong input");
    }
    
   //Tính toán giá trị của cây biểu thức
    private static double calculateTree(Node t){
        double x = 0;
        if(isLeaf(t))
            x = Double.parseDouble(t.data);
        else{
            double l = (double) calculateTree(t.left);
            double r = (double) calculateTree(t.right);
            switch (t.data)
            {
                case "+": x = l + r; break;
                case "-": x = l - r; break;
                case "*": x = l * r; break;
                case "/": x = l / r; break;
                case "^": x = Math.pow(l,r); break;
            }
        }
        return x;
    }
    public double calculateTree(){
        return calculateTree(root);
    }
    
    
    
 /*   private static Node insert(Node node, int data)   {
        //Hàm chèn dữ liệu đã cho vào cây theo thuộc tính Binary Tree
        if (node == null)
            node = new Node(data);
        else
        {
            if (data <= node.data)
                node.left = insert(node.left, data);
            else
                node.right = insert(node.right, data);
        }
        return node;
    } */    
    
    public  void leafNodes(Node node) {
        //Chức năng tính toán các nút lá trong cây nhị phân
        if(node != null && node.left == null && node.right == null) {
            lNodes += node.data + " ";
        }
        if(node.right != null) {
            leafNodes(node.right);
        }
        if(node.left != null) {
            leafNodes(node.left);
        }
    }
    
    public static boolean isLeaf(Node node) {
        //Chức năng xác định nếu nút được truyền là một lá
        if(node != null && node.left == null && node.right == null && node != null) {
            return true;
        }
        return false;
    }
    public void nonLeafNodes(Node node) {
        //Hàm tính toán các nút không phải lá trong cây nhị phân
        if(!isLeaf(node)) {
            nlNodes += node.data + " ";
            if(node.left != null)
                nonLeafNodes(node.left);
            if(node.right != null)
                nonLeafNodes(node.right);
        }
    }
    public void InOrder(Node root)  {
        //Function to perform the inorder traversal of a binary tree
        if(root != null)
        {   InOrder(root.left);
            iOrder += root.data + " ";
            InOrder(root.right);
        }
    }
    
    public void PreOrder(Node root)  {
        //Function to perform the preorder traversal of a binary tree
        if(root != null)
        {           
            prOrder += root.data + " ";
            PreOrder(root.left);
            PreOrder(root.right);
        }
    }
    
    public void PostOrder(Node root)  {
        //Function to perform the postoder traversal of a binary tree
        if(root != null)
        {
            PostOrder(root.left);
            PostOrder(root.right);
            pOrder += root.data + " ";
        }        
    }
    private static void Image(Node node) {
        //Chức năng tạo ảnh của cây đã qua (qua gốc)
        if (node != null) { 
        Image(node.left); 
        Image(node.right);
        Node temp = node.left; 
        node.left = node.right; 
        node.right = temp; 
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       //Trình xử lý tác vụ 
       if(e.getSource() == tree) {
            String str = f1.getText();
            try {/*
                //Dữ liệu nhập được phân tách bằng "," và được phân tích cú pháp
                //Hoan Glad close 4 lines
                String[] treeSplit = str.split(",");
                numberOfNodes = treeSplit.length;
                
                //Đã tạo nút gốc
                Node temp = new Node(Integer.parseInt(treeSplit[0]));
                f2.setText("");
                f3.setText("");
                f4.setText("");
                f5.setText("");
                f6.setText("");
                f7.setText("");
                f8.setText("");
                
                //Lặp lại thông qua đầu vào phân chia và thêm các nút vào cây
                //Hoan Glad close 4 lines
                for(int i=1; i<treeSplit.length;i++) {
                    temp = insert(temp,Integer.parseInt(treeSplit[i]));            
                }
                
                //Đặt root thành temp
                root = temp;*/
                createTree(str);
                imageNode = null;
                
                //Vẽ cây trên bảng điều khiển
                view.paintTree();
                
                //Chỉ ra rằng cây đã được sơn
                treePainted = true;
                imagePainted = false;
                
                //Bật các nút chức năng cây nhị phân
                result.setEnabled(true);
                postOrder.setEnabled(true);
                preOrder.setEnabled(true);
                leafNodes.setEnabled(true);
                nonLeafNodes.setEnabled(true);
                image.setEnabled(true);
                author.setEnabled(true);
            } catch (Exception exception) { 
                JOptionPane.showMessageDialog(null, "Wrong input");
            f1.setText("");}
       }
       else if(e.getSource() == result) {
           //Perform inorder traversal and display it
           iOrder ="";
           iOrder = Double.toString(calculateTree(root));
           f2.setText(iOrder);
       }
       else if(e.getSource() == postOrder) {
           //Perform postorder traversal and display it
           pOrder = "";
           PostOrder(root);
           f3.setText(pOrder);
       }
       else if(e.getSource() == preOrder) {
           //Perform preorder traversal and display it
           prOrder = "";
           PreOrder(root);
           f4.setText(prOrder);
       }
       else if(e.getSource() == leafNodes) {
           //calculate leaf nodes and display them
           lNodes = "";
           leafNodes(root);
           f5.setText(lNodes);
       }
       else if(e.getSource() == nonLeafNodes) {
           //calculate nonleafnodes and display them
           nlNodes = "";
           nonLeafNodes(root);
           f6.setText(nlNodes);
       }
       else if(e.getSource() == image) {
            String str = f1.getText();
            String[] treeSplit = str.split(",");
            
            //Tạo hình ảnh cây nhị phân
            /*Node temp = new Node(Integer.parseInt(treeSplit[0]));
            for(int i=1; i<treeSplit.length;i++) {
                temp= insert(temp,Integer.parseInt(treeSplit[i]));            
            }*/
            createTree(str);
            Node temp = root;
            f7.setText("Tree Image displayed");
            
            //Tính toán hình ảnh
            Image(temp);
            imageNode = temp;
            
            //Hiển thị hình ảnh cây nhị phân
            imageView.paintImage();
            
            //Hình ảnh hiển thị đã được hiển thị
            imagePainted = true;
       }
       else if(e.getSource() == author) {
           /*if(f8.getText() != null) {
               try {
                //Parse search value
                int x = Integer.parseInt(f8.getText()); 
                view.paintTree();
                
                //Search parsed value in the tree
                if(searchTree(root,f8.getText())) {
                    //if value is found, inform user
                    f8.setFont(font);
                    f8.setForeground(Color.green);
                    f8.setText(x + " is in the Tree");
                }
                else {
                    //if value is not found, inform user
                    f8.setFont(font);
                    f8.setForeground(Color.red);
                    f8.setText(x + " is not in the Tree");
                }
               } catch(Exception ex) { JOptionPane.showMessageDialog(null, "Incorrect input format"); }
           }*/
           f8.setText("          Phạm Lê Hoàn          Phạm Khả Minh Trí          Trần Phú Quý");
       }
       else if(e.getSource() == reset) {
           //Reset the program
           dispose();
           new ExpressionTree();
       }
       else if(e.getSource() == exit) {
           //Exit from the program
           dispose();
       }
     }
    
class Tree extends JPanel {   
    private int circleRadius = 15;                                              //node radius
    private int verticalSeperation = 30;                                        //Khoảng cách dọc giữa hai nút trong một cây nhị phân

       
    protected void paintTree() {
      Graphics g = getGraphics();
      if(root != null) {                
            displayTree(g, root, getWidth()/2, 35, getWidth()/4);        
      }
    }
    
    protected void paintImage() {
        Graphics g = getGraphics();
        if(imageNode != null) {              
            displayTree(g, imageNode, getWidth()/2, 35, getWidth()/4);        
      }
    }
        
    
    private void displayTree(Graphics g, Node node, int x, int y, int horizatalSeperation) {
      //Chức năng hiển thị một cây con với gốc là x, y        
      g.setColor(Color.CYAN);
      g.fillOval(x - circleRadius, y - circleRadius, 2 * circleRadius, 2 * circleRadius);
      
      
     
     
      //Viết giá trị dữ liệu trên nút
      g.setColor(Color.black);
 /*     if(node.data == 3000)
          g.drawString("^", x - 6, y + 4);
      else if(node.data == 2000)
          g.drawString("*", x - 6, y + 4);
      else if(node.data == 2001)
          g.drawString("/", x - 6, y + 4);
      else if(node.data == 1000)
          g.drawString("+", x - 6, y + 4);
      else if(node.data == 2001)
          g.drawString("-", x - 6, y + 4);
      else*/  g.drawString(node.data + "", x - 6, y + 4);            
      
      if (node.left != null) {
        //sử dụng drawLine để vẽ đường thẳng đến nút bên trái
          if(!treePainted) {
          try {              
              Thread.sleep(500);
          } catch (InterruptedException ex) {
              Logger.getLogger(ExpressionTree.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
          
        //draw line
        drawLineBetween2Circles(g, x - horizatalSeperation, y + verticalSeperation, x, y);     
        
        //đệ quy vẽ subtree trái giảm khoảng trống ngang và dọc
        displayTree(g, node.left, x - horizatalSeperation, y + verticalSeperation, horizatalSeperation/2);        
      }          
      if (node.right != null) {
          //sử dụng drawLine để vẽ đường thẳng đến nút bên phải
          if(!treePainted) {
          try {
          
              Thread.sleep(500);
          } catch (InterruptedException ex) {
              Logger.getLogger(ExpressionTree.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
          
        //draw line
        drawLineBetween2Circles(g, x + horizatalSeperation, y + verticalSeperation, x, y);         
        //recursively draw the right subtree
        //decrease the horizontal and vertical gaps
        displayTree(g, node.right, x + horizatalSeperation, y + verticalSeperation, horizatalSeperation/2);          
      }
    }        
    
    private void drawLineBetween2Circles(Graphics g, int x1, int y1, int x2, int y2) {
        //Function to draw a line between two circles centered at x1,y1 and x2,y2
        
        //Computing adjusted coordinates
        double d = Math.sqrt(verticalSeperation * verticalSeperation + (x2 - x1) * (x2 - x1));
        int xAdjusted = (int)(x1 - circleRadius * (x1 - x2) / d);
        int yAdjusted = (int)(y1 - circleRadius * (y1 - y2) / d);
        int xAdjusted1 = (int)(x2 + circleRadius * (x1 - x2) / d);
        int yAdjusted1 = (int)(y2 + circleRadius * (y1 - y2) / d);
        
        //Draw line between adjusted coordinates
        g.drawLine(xAdjusted, yAdjusted, xAdjusted1, yAdjusted1);
    }    
  }
}


