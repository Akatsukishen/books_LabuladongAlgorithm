package com.lss.algorithm.study;

/**
 * 并查集
 */
public class UnionFind {

    private int count ;

    private int[] parent;
    private int[] size;

    public UnionFind(int n){
        this.count = n;
        parent = new int[n];
        size = new int[n];
        for(int i = 0 ; i < n ; i ++){
            parent[i] = i;
            size[i] = i;
        }
    }

    public void union(int p , int q){
        int rootP = find(p);
        int rootQ = find(q);
        if(rootP == rootQ){
            return;
        }

        //把小的树指向大的树，避免退化成链表
        if(size[rootP] < size[rootQ]){
            parent[rootP] = rootQ;
            size[rootQ] = size[rootQ] + size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] = size[rootP] + size[rootQ];
        }
        count --;
    }

    /**
     * 在find的过程中进行路径压缩:爷爷节点变成父亲节点
     * @param  x
     * @return
     */
    private int find(int x){
        while (x != parent[x]){
            parent[x] = parent[parent[x]]; //指向爷爷节点
            x = parent[x];
        }
        return parent[x];
    }

    public boolean connected(int p , int q){
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }

    /**
     *  判断等式是否相互冲突
     *  【 a==b, b!=c, c==a】冲突了
     *  【 c==c, b==d, x!=z】不冲突
     *  相等元素属于同一个集合
     *  通过并查集来判断是否不相等的元素属于同一个集合，那么就冲突了
     * @param equations
     * @return
     */
    public static boolean equationsPossible(String[] equations){
        UnionFind  uf = new UnionFind(26);

        for(String s : equations){
            if(s.charAt(1) == '=' ){ //相等的
                uf.union(s.charAt(0) - 'a',s.charAt(3) - 'a');
            }
        }

        for(String s : equations) {
            if(s.charAt(1) == '!'){ // 不相等的冲突了
                if(uf.connected(s.charAt(0) - 'a',s.charAt(3) - 'a')){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 将被x包围的o变成x
     *  x x x x o
     *  x x x o x
     *  o o x o x
     *  x o x x x
     *  ->
     *  x x x x o
     *  x x x x x
     *  o o x x x
     *  x o x x x
     * @param board
     */
    public static void replaceO(char[][]board){
        int m = board.length;
        int n = board[0].length;
        //第1列和最后一列的关联的o全部标记为'#' -> 不可变成'x'的 'o'
        for(int i = 0 ; i < m ; i ++){
            dfs(board,i ,0);
            dfs(board,i,n-1);
        }
        //将第1行和最后一行的关联的o全部标记为'#' -> 不可变成'x'的 'o'
        for(int j = 0 ; j < n ; j ++){
            dfs(board,0,j);
            dfs(board,m-1,j);
        }
        for(int i = 1 ; i < m -1 ; i ++){
            for(int j = 1; j < n -1 ; j++){
                if(board[i][j] == 'o'){
                    board[i][j] = 'x';
                }
            }
        }
        for(int i = 0 ; i < m ; i ++){ //将不可变成'x'的 'o'复原
            for(int j = 0 ; j < n ; j++){
                if(board[i][j] == '#'){
                    board[i][j] = '0';
                }
            }
        }
    }

    /**
     * 将o和它周围的o全部变成"#"
     * @param board
     * @param i
     * @param j
     */
    public static void dfs(char[][] board,int i, int j){
        int m = board.length;
        int n = board[0].length;
        if(i < 0 || i >= m || j < 0 || j >= n){
            return;
        }
        if(board[i][j] != 'o'){
            return;
        }
        board[i][j] = '#';

        dfs(board,i + 1, j);
        dfs(board,i - 1,j );
        dfs(board,i , j + 1);
        dfs(board,i , j - 1);

    }

    public static void replaceObyUnionFind(char[][] board){
        int m = board.length;
        int n = board[0].length;
        //给dummy留出一个位置
        UnionFind unionFind = new UnionFind(m * n  + 1);
        int dummy = m * n ;

        //第一列和最后一列的与dummy相连
        for(int i = 0 ; i < m ; i ++){
            if(board[i][0] == 'o'){
                unionFind.union(i * n ,dummy);
            }
            if(board[i][n - 1] == 'o'){
                unionFind.union(i * n + n - 1, dummy);
            }
        }
        //第一行和最后一行的与dummy相连
        for(int j = 0 ; j < n; j ++){
            if(board[0][j] == 'o'){
                unionFind.union(j,dummy);
            }
            if(board[m-1][j] == 'o'){
                unionFind.union(n * (m - 1) + j ,dummy);
            }
        }

        //将里面的o与周边的o联通
        int[][] d = new int[][] { {1,0},{0,1},{0,-1},{-1,0}};
        for(int i = 1 ; i < m-1 ; i ++){
            for(int j = 1 ; j < n - 1; j ++){
                if(board[i][j] == 'o'){
                    for(int k  = 0 ; k < 4 ; k ++){
                        int x = i + d[k][0];
                        int y = j + d[k][1];
                        if(board[x][y] == 'o'){
                            unionFind.union(x * n  + y , i * n + j);
                        }
                    }
                }
            }
        }

        for(int i = 0 ; i < m ; i ++){
            for(int j = 0 ; j < n ; j ++){
                if(!unionFind.connected(i * n + j , dummy)){
                    board[i][j] = 'x';
                }
            }
        }
    }

    public static void main(String[] args) {
//        UnionFind uf = new UnionFind(10);
//
//        // 1 -> 3
//        uf.union(1,3);
//        // 1 -> 3, 2 -> 4
//        uf.union(2,4);
//        // 1 -> 3, 2 -> 4, 7 -> 8
//        uf.union(7,8);
//        //1 -> 3, 2 -> 4, 7 -> 8, 8 -> 2
//        // 1 -> 3, 7 > 8 -> 2 ->4
//        uf.union(8,2);
//
//        System.out.println(uf.count);

        System.out.println(equationsPossible(new String[]{"a==b","b!=c","c==a"}));
        System.out.println(equationsPossible(new String[]{"c==c","b==d","x!=z"}));

        char[][] board = new char[4][5];
        board[0] = new char[] {'x','x','x','x','o'};
        board[1] = new char[] {'x','x','x','o','x'};
        board[2] = new char[] {'o','o','x','o','x'};
        board[3] = new char[] {'x','o','x','x','x'};
//        replaceO(board);
        replaceObyUnionFind(board);
        for(int i = 0; i < board.length ; i ++){
            for(int j = 0 ; j < board[0].length ; j ++){
                System.out.print(board[i][j] + "\t" );
            }
            System.out.println();
        }
    }

}
