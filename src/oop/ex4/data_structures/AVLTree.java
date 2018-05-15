package oop.ex4.data_structures;


/**
 * A class representing an AVL tree.
 */
public class AvlTree {
    Node root;

    public boolean add(int newValue){
        Node father = getLocation(newValue);
        if(father != null){
            Node newNode = new Node(father, newValue);
            if (newValue > father.getValue()){
                father.setRightSon(newNode);
            }
            else{
                father.setLeftSon(newNode);
            }
            fixTree(newNode);
            return true;
        }
        else if (father == null && root == null){   //The tree is empty!
            root = new Node(newValue);
            return true;
        }
        return false; //newValue is contained in the tree.
    }


    private void fixTree(Node newNode){
        Node grandchild = newNode;
        Node child = newNode.getFather();
        Node father= child.getFather();
        child.setHeight();
        if (father == null){
            return;
        }
        while (father.getFather() != null){
            father.setHeight();
            if (checkViolations(father, child, grandchild)){
                return;
            }
            grandchild = child;
            child = father;
            father = father.getFather();
        }
        father.setHeight();
        checkViolations(father, child, grandchild);
    }

    private boolean checkViolations(Node father, Node child, Node grandchild){
        if (father.getHeightDiff() > 1){
            if(child == father.getRightSon() &&  grandchild == child.getRightSon()){
                fixRRViolation(father, child, grandchild);
            }
            else if (child == father.getRightSon() && grandchild == child.getLeftSon()){
                fixRLViolation(father, child, grandchild);
            }
            else if (child == father.getLeftSon() && grandchild == child.getRightSon()){
                fixLRViolation(father, child, grandchild);
            }
            else if (child == father.getLeftSon() && grandchild == child.getLeftSon()){
                fixLLViolation(father, child, grandchild);
            }
            return true;
        }
        return false;
    }

    private void fixRRViolation(Node father, Node child, Node grandchild){
        Node orphanNode = child.getLeftSon();
        child.setLeftSon(father);
        father.setRightSon(orphanNode);
    }

    private void fixRLViolation(Node father, Node child, Node grandchild){

    }

    private void fixLRViolation(Node father, Node child, Node grandchild){

    }

    private void fixLLViolation(Node father, Node child, Node grandchild){
        Node orphanNode = child.getRightSon();
        child.setRightSon(father);
        father.setLeftSon(orphanNode);

    }

    private Node getLocation(int newValue){
        return new Node(1);
    }

    private int contains(int searchVal){
        return 1;
    }


}