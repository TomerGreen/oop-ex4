add implementation:
* First, we use expectedParent, which finds the proper location of newValue incase it is not already in the
  tree, and returns null if the value is already in tree or if the tree is empty.

* if expectedParent returns null, and the root is also null, the tree is empty and we create the root with 
  newValue, update the size and return true.
* if expectedParent returns null but the root isn't null,, the value is already in the tree and we return 
  false.
* else, we add the value:
	* we check if newValue is bigger or smaller than its parent value, and add it at the proper place.
	* we call fixTree(newNode), which adjusts the heights and checks for violations until reaching the root.
	* if a violation is found we fix it and exit the method, as one call to add and only incur one violation.
	* update the size return true
	
Add takes O(logn + logn) = O(logn)   --> one log(n) to descend to the right place, and one log(n) to climb
back up.


delete implementation:
* First we use valueToNode on toDelete to check if we have a node with toDelete's value.
* If we do not, valueToNode returns null and we exit the method.
* Else, we check if the node is a leaf, single childed or double childed, and act accoridngly.
All cases use the method reBalanceTree(father), which checks for violations on the father node, 
and fixes them. It is different from fixTree because fixTree is given a leaf and calculates its father
and grandfather, and reBalanceTree is given a father and calculates its (highest) son and (highest) grandson.
Back to the three cases of the toDelete node:
	if leaf, we call leafDelete:
		* if it is also the root, we change the root to null and udpate the size.
		* else we change its father's pointer to null, update the size and call reBalanceTree.
	if single childed, we call singleChildDelete:
		* if toDelete is also the root, it makes the child the root and updates the size.
		* else, it changes toDelete's father to point at toDelete's single child
		* updates the size, and calls reBalanceTree on toDelete's father.
	if double childed:
		* it finds a successor, and swaps the successor and toDelete. 
		* if after the swap toDelete is not a leaf, it can only have a right son, otherwise it wouldn't be 
		  a successor. So in this case we essentially have a single chlilded case, and so we call
		  singleChildDelete
		* else, toDelete is now a leaf, so we call leafDelete.

running time of delete = O(logn + logn) = O(logn). One log(n) to find the right node, and one log(n) to fix
violations. 

Both add and delete methods use the checkViolations, which is the method that checks and fixes all violations
They also both use fixHeight, which fixes the height of a node.