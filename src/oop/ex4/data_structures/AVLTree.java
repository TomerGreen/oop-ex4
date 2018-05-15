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
        child.setHeight(1);
        if (father == null){
            return;
        }
        while (father.getFather() != null){
            father.setHeight(1);
            if (checkViolations(father, child, grandchild)){
                father.setHeight(-1);
                return;
            }
            grandchild = child;
            child = father;
            father = father.getFather();
        }
        father.setHeight(1);
        checkViolations(father, child, grandchild);
    }

    private boolean checkViolations(Node father, Node child, Node grandchild){
        if (father.getHeightDiff() > 1){
            if(child == father.getRightSon() &&  grandchild == child.getRightSon()){
                fixRRViolation(father, child);
            }
            else if (child == father.getRightSon() && grandchild == child.getLeftSon()){
                fixRLViolation(father, child, grandchild);
            }
            else if (child == father.getLeftSon() && grandchild == child.getRightSon()){
                fixLRViolation(father, child, grandchild);
            }
            else if (child == father.getLeftSon() && grandchild == child.getLeftSon()){
                fixLLViolation(father, child);
            }
            return true;
        }
        return false;
    }

    private void fixRRViolation(Node father, Node child){
        Node grandfather = father.getFather();
        Node orphanNode = child.getLeftSon();
        child.setLeftSon(father);
        father.setFather(child);
        if (grandfather.getRightSon() == father){
            grandfather.setRightSon(child);
        }
        else if (grandfather.getLeftSon() == father){
            grandfather.setLeftSon(child);
        }
        child.setFather(grandfather);
        father.setRightSon(orphanNode);
        orphanNode.setFather(father);
    }

    private void fixRLViolation(Node father, Node child, Node grandchild){
        Node orphanChild = grandchild.getRightSon();
        father.setRightSon(grandchild);
        grandchild.setFather(father);
        grandchild.setRightSon(child);
        child.setFather(grandchild);
        child.setLeftSon(orphanChild);
        orphanChild.setFather(child);
        fixRRViolation(father, grandchild);
    }

    private void fixLRViolation(Node father, Node child, Node grandchild){

    }

    private void fixLLViolation(Node father, Node child){
        Node grandfather = father.getFather();
        Node orphanNode = child.getRightSon();
        child.setRightSon(father);
        father.setFather(child);
        if (grandfather.getRightSon() == father){
            grandfather.setRightSon(child);
        }
        else if (grandfather.getLeftSon() == father){
            grandfather.setLeftSon(child);
        }
        child.setFather(grandfather);
        father.setLeftSon(orphanNode);
        orphanNode.setFather(father);

    }

    private Node getLocation(int newValue){
        return new Node(1);
    }

    private int contains(int searchVal){
        return 1;
    }


}