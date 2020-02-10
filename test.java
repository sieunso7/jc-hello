import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
/*
 * 
 * 
4
4 4 0
0 1 2
0 3 2
1 2 1
3 2 0
4 4 0
0 1 10
0 3 104
1 2 100
3 2 6
4 4 0
0 1 10
0 3 104
1 2 100
3 2 7
4 5 0
0 1 10
0 3 20
1 3 10
1 2 5
2 3 5
 * 
 * 
 */

public class OctFifthTest {

	static class Node{
		int x, y, cost;
		public Node(int x, int y, int cost){
			this.x = x;
			this.y = y;
			this.cost = cost;
		}
	}

	static class NodeArray implements Comparable<NodeArray>{
		int x, y, cost;
		public NodeArray(int x, int y, int cost){
			this.x = x;
			this.y = y;
			this.cost = cost;
		}
		public int compareTo(NodeArray n) {
			return this.cost - n.cost;
		}
	}

	public static int T, N, M, S;
	public static int INF = 987654321;
	public static ArrayList<Node> firstList;
	public static int[] sDist1;
	public static int[] sDist2;
	public static ArrayList<NodeArray> arrayList;
	public static int[] parent;
	public static void main(String args[]) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.valueOf(br.readLine());
		for(int t = 1; t <= T; t++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.valueOf(st.nextToken());
			M = Integer.valueOf(st.nextToken());
			S = Integer.valueOf(st.nextToken());

			firstList = new ArrayList<>();
			arrayList = new ArrayList<>();

			sDist1 = new int[N + 1];
			sDist2 = new int[N + 1];
			parent = new int[N + 1];

			Arrays.fill(sDist1, INF);
			Arrays.fill(sDist2, INF);

			int a, b, c;
			for(int i = 1; i <= M; i++){
				st = new StringTokenizer(br.readLine());
				a = Integer.valueOf(st.nextToken());
				b = Integer.valueOf(st.nextToken());
				c = Integer.valueOf(st.nextToken());

				if(a == S){
					sDist1[b] = c;
					sDist2[b] = c;
					firstList.add(new Node(a, b, c));
				}else if(b == S){
					sDist1[a] = c;
					sDist2[a] = c;
					firstList.add(new Node(a, b, c));
				}else{
					arrayList.add(new NodeArray(a, b, c));
				}
			}

			Collections.sort(arrayList);


			int sol = 0;
			int sol1 = 0;
			int sol2 = 0;
			for(int i = 1; i <= N; i++){
				parent[i] = i;
			}

			for(int j=0; j < firstList.size(); j++) {
				for(int i= 0; i < arrayList.size(); i++) {
					Node preN = firstList.get(j); // 시작값을 들고 있는 list
					NodeArray newN = arrayList.get(i); // 시작값 제외 리스트
					int curCost = 0;

					if(isSameParent(preN.x, newN.y)){
						curCost = preN.cost + newN.cost;
						if(sDist1[preN.y] >= curCost){
							sDist1[preN.y] = curCost;
							if(sDist2[newN.x] > newN.cost){
								sDist2[newN.x] = newN.cost;
							}
						}
					}

					if(isSameParent(preN.y, newN.x)){
						curCost = preN.cost + newN.cost;
						if(sDist1[preN.x] >= curCost){
							sDist1[preN.x] = curCost;
							if(sDist2[newN.y] > newN.cost){
								sDist2[newN.y] = newN.cost;
							}
						}
					}

					if(!isSameParent(newN.x, newN.y)){
						union(newN.x, newN.y);
					}
				}
			}

			for(int i = 0; i <= N; i++){
				if(sDist2[i] != INF){
					sol += sDist2[i];
				}
			}
			System.out.println("#" + t +" " +sol);
		}

	}

	static int find(int x){
		if(parent[x] == x){
			return x;
		}
		return parent[x] = find(parent[x]);
	}

	static void union(int x, int y){
		x = find(x);
		y = find(y);

		if(x != y){
			parent[y] = x;
		}
	}

	static boolean isSameParent(int x, int y){
		x = find(x);
		y = find(y);

		if(x == y){
			return true;
		}else{
			return false;
		}
	}

}
