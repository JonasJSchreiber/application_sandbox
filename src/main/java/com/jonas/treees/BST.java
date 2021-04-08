package com.jonas.treees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BST {
	
	Integer val;
	BST parent;
	BST left;
	BST right;
	
	public BST() {
		this.val = null;
	}
	
	public BST(int val) {
		this.val = val;
	}
	
	public void insert(int val, BST parent) {
		if (this.val == null) {
			this.val = val;
			this.parent = parent;
			this.left = new BST();
			this.right = new BST();
		} else if (this.val > val) {
			if (this.left == null) {
				this.left = new BST();
			}
			this.left.insert(val, this);
		} else if (this.val < val) {
			if (this.right == null) {
				this.right = new BST();
			}
			this.right.insert(val, this);
		}
	}
	
	public void insert(int val) {
		this.insert(val, this.parent);
	}
	
	public BST find(int val) {
		if (this == null || this.val == null) {
			return null;
		} else if (this.val == val) {
			return this;
		} else if (this.val > val) {
			return this.left.find(val);
		} else {
			return this.right.find(val);
		}	
	}
	
	public int findDepthOfN(int val) {
		return findDepthOfN(val, 0);
	}
	
	public int findDepthOfN(int val, int depth) {
		if (this.val == null) {
			return 0;
		} else if (this.val == val) {
			return depth;
		} else if (this.val > val) {
			return this.left.findDepthOfN(val, depth+1);
		} else {
			return this.right.findDepthOfN(val, depth+1);
		}
	}
	
	public BST remove(int val) {
		if (this.find(val) != null) {
			//Cases: 
			// *  Special Case : Node to remove is head
			// 1. Node has zero children - destroy node
			// 2. Node has one child - replace node with child
			// 3. Node has two children - replace this node with largest value on left subtree
			//    Make parent.right/left replacement
			//    Make replacement's parent's right/left null.
			//	  Make replacement's parent = target's parent
			//    Make the replacement's right node the target node right node
			//    If target.left.maxdepth > 1, make replacement.left target.left 
			
			BST target = this.find(val);
			BST replacement = null;
			if (target.getChildren().size() == 0) {
				target.val = null;
			} else if (target.getChildren().size() == 1) {
				replacement = (target.left == null) ? target.right : target.left;
			} else {
				replacement = findLargestNode(target.left);						
				if (replacement == null || replacement.val == null) {
					replacement = findSmallestNode(target.right);
					replacement.right = (findMaxDepth(target.right) > 1) ? target.right : null;
					replacement.left = target.left;
				} else {
					replacement.left = (findMaxDepth(target.left) > 1) ? target.left : null;
					replacement.right = target.right;	
				}
			}
			
			if (target.getChildren().size() == 0) {
				if (target.parent != null) {
					if (target.parent.right == target) {
						target.parent.right = null;
					} else {
						target.parent.left = null;
					}
				}
			} else {		
				
				target.val = replacement.val;
				target.left = (replacement.left == null || replacement.left.val == null) ? null : replacement.left;
				target.right = (replacement.right == null || replacement.right.val == null) ? null : replacement.right;
				
				if (replacement.parent != null) {
					if (replacement.parent.right == replacement) {
						replacement.parent.right = null;
					} else {
						replacement.parent.left = null;
					}	
				}			
				
				if (target.parent != null) {
					replacement.parent = target.parent;
					if (target.parent.right == target) {
						target.parent.right = replacement;
					} else {
						target.parent.left = replacement;
					}
				}
			} 
		}
		return this;
	}
	
	public BST findLargestNode(BST tree) {
		BST ptr = tree;
		while (ptr.right != null && ptr.right.val != null) {
			ptr = ptr.right;
		}
		return ptr;
	}
	
	public BST findSmallestNode(BST tree) {
		BST ptr = tree;
		while (ptr.left != null && ptr.left.val != null) {
			ptr = ptr.left;
		}
		return ptr;
	}
	
	public int findMaxDepth() {
		return findMaxDepth(this);
	}
	
	public int findMaxDepth(BST tree) {
		if (tree == null || tree.val == null) {
			return 0;
		} else {
			return 1 + Math.max(findMaxDepth(tree.left), findMaxDepth(tree.right));
		}
	}
	
	public void printTree() {
		//do a BFS on the tree
		System.out.println("\n");
		List<BST> queue = this.bfs();
		Iterator<BST> i = queue.iterator();
		int depth = 0;
		System.out.print("Depth: " + depth + ". ");
		while (i.hasNext()) {
			BST ptr = i.next();
			if (findDepthOfN(ptr.val) != depth) {
				System.out.println();
				depth = findDepthOfN(ptr.val);
				System.out.print("Depth: " + depth + ". ");
			}
			System.out.print(ptr.val + " ");
		}
		//use the max depth to figure out spacing 
		//a height of 4 means print 4*2 spaces before the head, 3*2 before the head's children
		//between each line print height-1 * 2 spaces "/\" * (2 ^ (current depth -1 )). 
		//(at depth 1, you do this once) at depth 2 you do this twice, at depth 3 you do this 4 times)
	}
	
    public List<BST> bfs() {
        //Since queue is a interface
        List<BST> queue = new CopyOnWriteArrayList<BST>();
        if (this.val != null) {
            queue.add(this);	
        }
        for (int i = 0; i < queue.size(); i++) {
        	BST ptr = queue.get(i);
        	for (BST b : ptr.getChildren()) {
        		if (b != null && b.val != null) {
            		queue.add(b);	
        		}
        	}
        }
        return queue;
    }
    
    public List<BST> getChildren() {
    	List<BST> children = new ArrayList<BST>();
    	if (this.left != null) {
    		children.add(this.left);	
    	}
    	if (this.right != null) {
    		children.add(this.right);	
    	}
    	return children;
    }

}
