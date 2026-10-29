import java.util.*;

class Solution {
    private int timer = 0;
    private static final int LOG = 18;

    @SuppressWarnings("unchecked")
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] adj = (List<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        int[][] up = new int[n][LOG];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] initialP = new int[n];
        char[] chars = s.toCharArray();

        timer = 0;
        dfs(0, 0, 0, 0, adj, chars, up, depth, tin, tout, initialP);

        int[] bit = new int[n + 2];
        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            if (q.charAt(0) == 'u') {
                int firstSpace = q.indexOf(' ', 7);
                int u = Integer.parseInt(q.substring(7, firstSpace));
                char c = q.charAt(q.length() - 1);
                char oldC = chars[u];
                if (oldC != c) {
                    int diff = (1 << (oldC - 'a')) ^ (1 << (c - 'a'));
                    chars[u] = c;
                    updateBit(bit, tin[u], diff, n);
                    updateBit(bit, tout[u] + 1, diff, n);
                }
            } else {
                int firstSpace = q.indexOf(' ', 6);
                int u = Integer.parseInt(q.substring(6, firstSpace));
                int v = Integer.parseInt(q.substring(firstSpace + 1));

                int w = getLca(u, v, up, depth);
                int pu = initialP[u] ^ queryBit(bit, tin[u]);
                int pv = initialP[v] ^ queryBit(bit, tin[v]);
                int wMask = 1 << (chars[w] - 'a');

                int pathMask = pu ^ pv ^ wMask;
                ans.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return ans;
    }

    private void dfs(int u, int p, int d, int currMask, List<Integer>[] adj, char[] chars,
                     int[][] up, int[] depth, int[] tin, int[] tout, int[] initialP) {
        tin[u] = ++timer;
        depth[u] = d;
        up[u][0] = p;
        for (int k = 1; k < LOG; k++) up[u][k] = up[up[u][k - 1]][k - 1];

        currMask ^= (1 << (chars[u] - 'a'));
        initialP[u] = currMask;

        for (int v : adj[u]) {
            if (v != p) dfs(v, u, d + 1, currMask, adj, chars, up, depth, tin, tout, initialP);
        }
        tout[u] = timer;
    }

    private int getLca(int u, int v, int[][] up, int[] depth) {
        if (depth[u] < depth[v]) {
            int tmp = u; u = v; v = tmp;
        }
        for (int k = LOG - 1; k >= 0; k--) {
            if (depth[u] - (1 << k) >= depth[v]) u = up[u][k];
        }
        if (u == v) return u;
        for (int k = LOG - 1; k >= 0; k--) {
            if (up[u][k] != up[v][k]) {
                u = up[u][k];
                v = up[v][k];
            }
        }
        return up[u][0];
    }

    private void updateBit(int[] bit, int idx, int val, int n) {
        for (; idx <= n; idx += idx & -idx) bit[idx] ^= val;
    }

    private int queryBit(int[] bit, int idx) {
        int res = 0;
        for (; idx > 0; idx -= idx & -idx) res ^= bit[idx];
        return res;
    }
}