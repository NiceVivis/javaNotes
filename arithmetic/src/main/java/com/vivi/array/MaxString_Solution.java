package com.vivi.array;

public class MaxString_Solution {

    public static void main(String[] args) {
        int number = MaxStringSon2("abcabcbb");
        System.out.printf(""+number);
        // MaxStringSon("b");
        //   int[] a=new int[128];

    }

    public static void MaxStringSon(String s){
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            System.out.println(""+i+","+j);
            i = Math.max(index[s.charAt(j)], i);
            System.out.println(index[s.charAt(j)]);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        System.out.println(ans);
    }


    public static int MaxStringSon2(String s){
        // List<Character>  tempChars = new ArrayList<Character>();
        //char[] chars = s.toCharArray();
        String count = "";
        for(int i=0;i<s.length();i++){
            for(int j=i+1;j<=s.length();j++){
                String str = s.substring(i,j);
                if (j == s.length()){
                    if (count.length() < str.length()) {
                        count = str;
                    }
                    break;
                }else {
                    if (str.contains(s.substring(j, j + 1))) {
                        if (count.length() < str.length()) {
                            count = str;
                        }
                        break;
                    }
                }
            }
        }
        System.out.println("cout: "+count);
        return count.length();
    }
}
