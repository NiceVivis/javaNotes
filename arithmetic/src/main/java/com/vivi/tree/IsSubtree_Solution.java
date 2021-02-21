package com.vivi.tree;

import com.vivi.tree.linked.TreeNode;

/**
 * @author yangwei
 * @date 2021/2/4 11:19 上午
 * 572. 另一个树的子树
 * 给定两个非空二叉树 s 和 t，检验 s 中是否包含和 t 具有相同结构和节点值的子树。s 的一个子树包括 s 的一个节点和这个节点的所有子孙。s 也可以看做它自身的一棵子树。
 * 链接：https://leetcode-cn.com/problems/subtree-of-another-tree
 */

public class IsSubtree_Solution {

    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null && t == null){
            return false;
        }


        return true;
    }

    public static void main(String[] args) {

    }

}
