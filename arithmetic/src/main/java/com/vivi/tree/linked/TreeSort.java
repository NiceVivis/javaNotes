package com.vivi.tree.linked;

import java.util.ArrayList;
import java.util.List;

/**
 * 前序遍历：根左右
 * 中序：左中右
 * 后序：左右根
 */
public class TreeSort {
    /**
     * 前序遍历
     * 根左右
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> left = inorderTraversal(root.left);
        List<Integer> right = inorderTraversal(root.right);

        List<Integer> list = new ArrayList<>();
        list.addAll(left);
        list.add(root.val);
        list.addAll(right);
        return list;
    }


}
