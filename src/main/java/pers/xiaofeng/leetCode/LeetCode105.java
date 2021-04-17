package pers.xiaofeng.leetCode;

import com.google.gson.Gson;

import javax.swing.tree.TreeNode;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: pers.xiaofeng.leetCode.LeetCode105
 * @description: 根据前序遍历和中序遍历的结果还原 ⼀棵⼆叉树
 * @author: xiaofeng
 * @create: 2021-04-17 16:50
 */
public class LeetCode105 {

    public static void main(String[] args) {
        int[] preorder = {1, 2, 4, 5, 3, 6, 7};
        int[] inorder = {4, 2, 5, 1, 6, 3, 7};
        HashMap<Integer, Integer> inMap = new HashMap<>();
        inMap.put(1, 1);
        inMap.put(2, 2);
        inMap.put(3, 3);
        inMap.put(4, 4);
        inMap.put(5, 5);
        inMap.put(6, 6);
        inMap.put(7, 7);
        BinaryTreeNode binaryTreeNode = buildTree(preorder, 0, 6, inorder, 0, 6, inMap);
        System.out.println(new Gson().toJson(binaryTreeNode));
    }

    private static BinaryTreeNode buildTree(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd, Map<Integer, Integer> inMap) {

        if (preStart > preEnd || inStart > inEnd) return null;

        BinaryTreeNode root = new BinaryTreeNode(preorder[preStart]);
        int inRoot = inMap.get(root.val);
        int numsLeft = inRoot - inStart;

        root.left = buildTree(preorder, preStart + 1, preStart + numsLeft, inorder, inStart, inRoot - 1, inMap);
        root.right = buildTree(preorder, preStart + numsLeft + 1, preEnd, inorder, inRoot + 1, inEnd, inMap);

        return root;
    }

    /**
     * 二叉树结点
     */
    private static class BinaryTreeNode {
        int val;
        BinaryTreeNode left;
        BinaryTreeNode right;

        public BinaryTreeNode(int val) {
            this.val = val;
        }
    }
}
