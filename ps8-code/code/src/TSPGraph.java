import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

public class TSPGraph implements IApproximateTSP {

    int[] parent;
    // Prim's algo
    @Override
    public void MST(TSPMap map) {
        // TODO: implement this method
        parent = new int[map.getCount()];
        TreeMapPriorityQueue<Double, Integer> pq = new TreeMapPriorityQueue<>();
        for (int i = 0; i < map.getCount(); i++) {
            pq.add(i, Double.MAX_VALUE);
        }
        pq.decreasePriority(0,0.0);
        HashSet<Integer> S = new HashSet<>();
        S.add(0);
        while (!pq.isEmpty()) {
            Integer v = pq.extractMin();
            S.add(v);
            map.setLink(v, parent[v], false);
            for (int i = 0; i < map.getCount(); i++) {
                if (!S.contains(i)) {
                    if (map.pointDistance(i, v) < pq.lookup(i)) {
                        pq.decreasePriority(i, map.pointDistance(i, v));
                        parent[i] = v;
                    }
                }

            }

        }
        map.redraw();

    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);
        // TODO: implement the rest of this method.
        // Adjacency List
        ArrayList<LinkedList<Integer>> nodeList = new ArrayList<>();
        for (int i = 0; i < map.getCount(); i++) {
            LinkedList<Integer> linkedList = new LinkedList<>();
            for (int j = 0; j < map.getCount(); j++) {
                if (parent[i] == j || parent[j] == i) {
                    linkedList.add(j);
                }
            }
            nodeList.add(linkedList);
        }
        DFS(nodeList, map);
        map.redraw();
    }

    public void DFS(ArrayList<LinkedList<Integer>> nodeList, TSPMap map) {
        boolean[] visited = new boolean[nodeList.size()];
        Arrays.fill(visited, false);
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        visited[0] = true;
        while (!stack.isEmpty()) {
            Integer current = stack.pop();
            visited[current] = true;
            for (Integer p : nodeList.get(current)) {
                if (!visited[p]) {
                    stack.push(p);
                }
            }
            if (stack.isEmpty()) {
                map.setLink(current,0, false);
            } else {
                map.setLink(current, stack.peek(), false);
            }
        }
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        // Cities visited once
        // outgoing link
        int numOfPoints = map.getCount();
        boolean[] visited = new boolean[numOfPoints];
        Arrays.fill(visited, false);
        for (int i = 0; i < numOfPoints; i++) {
            int next = map.getLink(i);
            if (next == -1 || visited[next]) {
                return false;
            } else {
                visited[next] = true;
            }
        }
        // Links == cities
            int counter = 0;
            int parent = -1;
            int current = 0;
            while (parent != 0) {
                parent = map.getLink(current);
                current = parent;
                counter++;
            }

        return counter == map.getCount();
    }

    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        if (!isValidTour(map)) {
            return -1;
        }

        int numOfPoints = map.getCount();
        double sum = 0.0;
        for (int i = 0; i < numOfPoints; i++) {
            int next = map.getLink(i);
            sum += map.pointDistance(i, next);
        }
        return sum;
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "hundredpoints.txt");
        TSPGraph graph = new TSPGraph();

        graph.MST(map);
       // graph.TSP(map);
        // System.out.println(graph.isValidTour(map));
        //System.out.println(graph.tourDistance(map));
    }
}
