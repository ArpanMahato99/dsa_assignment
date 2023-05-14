import java.util.*;

public class Graph {

    private final List<String> vertexList;
    private final List<List<Integer>> adjMatrix;
    private final Map<String, Integer> vertexToIndexMap;

    public Graph() {
        vertexList = new ArrayList<>();
        adjMatrix = new ArrayList<>();
        vertexToIndexMap = new HashMap<>();
    }

    /*
        Pre cond: s is the Vertex (airport) as input in the command prompt
        Effect : a new vertex is added to the Graph. If the Graph is empty, a new vertex is created at the start

        Time Complexity: O(V).
        Space Complexity: O(V^2).
     */
    public void addVertex(String s){
        if (vertexToIndexMap.containsKey(s)) {
            System.out.println("Vertex already exist.");
            return;
        }
        vertexList.add(s);
        vertexToIndexMap.put(s, vertexList.size()-1);
        adjMatrix.add(new ArrayList<>());

        // Filling the newly added row with 0s takes O(V) time
        for (int i = 0; i < vertexList.size() - 1; i++) {
            adjMatrix.get(vertexList.size() - 1).add(0);
        }
        // Adding a new column with 0s to all existing rows in the adjMatrix takes O(V) time
        for (List<Integer> row : adjMatrix) {
            row.add(0);
        }
    }

    /*
        Pre cond : All vertices are already added to the Graph
        Effect : the program finds the vertex at the entered position and removes it from the list of vertices. It
        also checks if there are any edges associated with that vertex and resets their weight to Zero. It also
        adjusts the
        Time Complexity: O(V).
        Space Complexity: O(1).
     */
    public void removeVertex(String s){
        if (!vertexToIndexMap.containsKey(s)) {
            System.out.println("Vertex " + s + " does not exist");
        } else {
            int vertexIdx = vertexToIndexMap.get(s);
            // Removing the row associated with the vertex from the adjMatrix takes O(V) time
            adjMatrix.remove(vertexIdx);
            // Removing the column associated with the vertex from each row in the adjMatrix takes O(V) time
            for (List<Integer> row : adjMatrix) {
                row.remove(vertexIdx);
            }
            // Removing the vertex from the vertexList and updating the vertexToIndexMap takes O(V) time
            vertexList.remove(vertexIdx);
            vertexToIndexMap.remove(s);

            // Updating the indices in the vertexToIndexMap for the remaining vertices takes O(V) time.
            for (int i = 0; i < vertexList.size(); i++) {
                vertexToIndexMap.put(vertexList.get(i), i);
            }
        }
    }

    /*
        Pre cond : All vertices v and w are already added to the Graph
        Effect : the program finds the position of each of the vertices v and w and records the weight x against
        edge for v and w
        Time Complexity: O(1)
        Space Complexity: O(1)
     */
    public void addEdge(String v, String w, int x) {
        if (v.equals(w)) {
            System.out.println("No edge allowed as both " + v + " and " + w + " are same.");
        } else if (!vertexToIndexMap.containsKey(v)) {
            System.out.println("Vertex " + v + " does not exist.");
        } else if(!vertexToIndexMap.containsKey(w)) {
            System.out.println("Vertex " + w + " does not exist.");
        } else {
            int sourceIndex = vertexToIndexMap.get(v);
            int destinationIndex = vertexToIndexMap.get(w);
            adjMatrix.get(sourceIndex).set(destinationIndex, x);
            adjMatrix.get(destinationIndex).set(sourceIndex, x);
            System.out.println("Added edges between " + v + " and " + w + ".");
        }
    }

    /*
        Pre cond : All vertices are already added to the Graph
        Effect : the program finds the position of the vertices in the Graph and resets the corresponding edge
        weight to Zero.
        Time Complexity: O(1)
        Space Complexity: O(1)
     */
    public void removeEdge(String v, String w) {
        if (!vertexToIndexMap.containsKey(v)) {
            System.out.println("Vertex " + v + " does not exist.");
        } else if(!vertexToIndexMap.containsKey(w)) {
            System.out.println("Vertex " + w + " does not exist.");
        } else {
            int sourceIdx = vertexToIndexMap.get(v);
            int destIdx = vertexToIndexMap.get(w);

            adjMatrix.get(sourceIdx).set(destIdx, 0);
            adjMatrix.get(destIdx).set(sourceIdx, 0);
            System.out.println("Removed edges between " + v + " and " + w);
        }
    }

    /*
        Pre cond: Graph is not empty
        Effect: Displays all vertices in a 2 by 2 matrix. Each valid edge is represented by the weight of the edge
        displayed in the corresponding position in the matrix. Each invalid edge is represented by Zero weight.
        Time Complexity: O(V^2)
        Space Complexity: O(1)
     */
    public void displayAdj(){
        System.out.print("   ");
        // prints the header row, which displays the vertex names.
        for (String vertex : vertexList) {
            System.out.print(vertex + "    ");
        }
        System.out.println();

        for (int sourceIdx = 0; sourceIdx < adjMatrix.size(); sourceIdx++) {
            // prints the vertex name corresponding to the current row
            System.out.print(vertexList.get(sourceIdx) + "  ");
            // iterates over each element in the current row using the destIdx variable and prints the cost
            for (int destIdx = 0; destIdx < adjMatrix.size(); destIdx++ ) {
                System.out.print(adjMatrix.get(sourceIdx).get(destIdx) + "    ");
            }
            System.out.println();
        }
    }

    /*
        Pre cond : Graph is not empty
        Effect: Displays all combinations of valid edges. For example: if there is a valid edge between A and B,
        then the program will show both “A->B” and “B->A”
        Time Complexity: O(V^2)
        Space Complexity: O(1)
     */
    public void displayEdges(){
        for (int sourceIdx = 0; sourceIdx < adjMatrix.size(); sourceIdx++) {
            List<Integer> row = adjMatrix.get(sourceIdx);
            for (int destIdx = 0; destIdx < row.size(); destIdx++) {
                if (adjMatrix.get(sourceIdx).get(destIdx) != 0) {
                    String source = vertexList.get(sourceIdx);
                    String dest = vertexList.get(destIdx);
                    System.out.println(source + "->" + dest);
                }
            }
        }
    }

    /*
        Pre cond : Graph is not empty
        Effect : Display the vertices in the Graph starting with the vertex input by the user and then traversing
        the graph using BFS.
        Time Complexity: O(V^2)
        Space Complexity: O(V)
     */
    public void BFS(String v) {
        if (!vertexToIndexMap.containsKey(v)) {
            System.out.println("Vertex " + v + " does not exist.");
            return;
        }
        ArrayList<String> BFS = new ArrayList<>();
        boolean[] isVisited = new boolean[adjMatrix.size()];  // isVisited array is to keep track of the visited airports and not to visit it again in the traversal
        Queue<Integer> queue = new LinkedList<>();  // to traverse the graph in level order

        int sourceIdx = vertexToIndexMap.get(v);
        isVisited[sourceIdx] = true;
        queue.offer(sourceIdx);
        while(!queue.isEmpty()) {
            int currentVertexIdx = queue.poll();
            BFS.add(vertexList.get(currentVertexIdx));
            for (int i = 0; i < adjMatrix.size(); i++) {
                if (adjMatrix.get(currentVertexIdx).get(i) != 0
                        && !isVisited[i]) {
                    queue.offer(i);
                    isVisited[i] = true;
                }
            }

        }
        System.out.println("BFS : " + BFS);
    }

    /*
        Pre cond : Graph is not empty
        Effect : Display the vertices in the Graph starting with the vertex input by the user and then traversing
        the graph using DFS.
        Time Complexity: O(V^2)
        Space Complexity: O(V)
     */
    public void DFS(String v) {
        if (!vertexToIndexMap.containsKey(v)) {
            System.out.println("Vertex " + v + " does not exist.");
            return;
        }
        ArrayList<String> DFS = new ArrayList<>();
        boolean[] isVisited = new boolean[adjMatrix.size()];
        Stack<Integer> stack = new Stack<>();

        int sourceIndex = vertexToIndexMap.get(v);
        stack.push(sourceIndex);

        while (!stack.isEmpty()) {
            int currentVertexIdx = stack.pop();

            if (!isVisited[currentVertexIdx]) {
                isVisited[currentVertexIdx] = true;
                DFS.add(vertexList.get(currentVertexIdx));

                for (int i = adjMatrix.size() - 1; i >= 0; i--) {
                    if (adjMatrix.get(currentVertexIdx).get(i) != 0
                            && !isVisited[i]) {
                        stack.push(i);
                    }
                }
            }
        }
        System.out.println("DFS : " + DFS);
    }

    /*
        Pre cond : Graph is not empty
        Effect : Based on the source s and destination d entered, the program displays the weight (cost) of the
        edge if it is a valid edge. If the edge is not a valid one, a relevant message is displayed.
        Time Complexity: O(V^2)
        Space Complexity: O(V)
     */
    public void flightAvailable(String s, String d) {
        if (s.equals(d)) {
            System.out.println("No edge allowed as both " + s + " and " + d + " are same.");
        } else if (!vertexToIndexMap.containsKey(s)) {
            System.out.println("Vertex " + s + " does not exist.");
        } else if(!vertexToIndexMap.containsKey(d)) {
            System.out.println("Vertex " + d + " does not exist.");
        } else {
            int sourceIdx = vertexToIndexMap.get(s);
            int destIdx = vertexToIndexMap.get(d);

            // checks if there is a direct flight (edge) between the source and destination vertices
            if (adjMatrix.get(sourceIdx).get(destIdx) != 0) {
                System.out.println("Flight Price is: " + adjMatrix.get(sourceIdx).get(destIdx));
                return;
            }

            int[] cost = {0};
            boolean[] isVisited = new boolean[adjMatrix.size()];

            if (flightAvailableHelper(isVisited, sourceIdx, destIdx, cost)) {
                System.out.println("Flight Price is: " + cost[0]);
            } else {
                System.out.println("No flight available from " + s + " to " + d + ".");
            }
        }

    }

    private boolean flightAvailableHelper(boolean[] isVisited, int currentVertexIdx, int destIdx, int[] cost) {
        isVisited[currentVertexIdx] = true;

        if (currentVertexIdx == destIdx) {
            return true;
        }

        for (int i = 0; i < adjMatrix.size(); i++) {
            if (adjMatrix.get(currentVertexIdx).get(i) != 0
                    && !isVisited[i]) {
                cost[0] += adjMatrix.get(currentVertexIdx).get(i);
                if (flightAvailableHelper(isVisited, i, destIdx, cost)) {
                    return true;
                }
                cost[0] -= adjMatrix.get(currentVertexIdx).get(i);
            }
        }
        return false;
    }


}
