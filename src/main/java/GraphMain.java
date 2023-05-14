import java.util.Scanner;

public class GraphMain {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        GraphMain main = new GraphMain();
        Graph graph = new Graph();
        int choice = 0;
        do {
            System.out.println("------------------------------------------------------------------");
            System.out.println("***                            MENU                            ***");
            System.out.println("------------------------------------------------------------------");
            System.out.println("1.AddVertex");
            System.out.println("2.removeVertex");
            System.out.println("3.AddEdge");
            System.out.println("4.removeEdge");
            System.out.println("5.DisplayAdjacencyList");
            System.out.println("6.DisplayEdges");
            System.out.println("7.BFS");
            System.out.println("8.DFS");
            System.out.println("9.Check Flight availability");
            System.out.println("0.Exit");
            System.out.println("Enter your choice:");

            choice = reader.hasNextInt() ? reader.nextInt() : 10;
            reader.nextLine();

            switch (choice) {
                case 1:
                    main.addVertex(reader, graph);
                    break;
                case 2:
                    main.removeVertex(reader, graph);
                    break;
                case 3:
                    main.addEdge(reader, graph);
                    break;
                case 4:
                    main.removeEdge(reader, graph);
                    break;
                case 5:
                    graph.displayAdj();
                    break;
                case 6:
                    graph.displayEdges();
                    break;
                case 7:
                    main.BFS(reader, graph);
                    break;
                case 8:
                    main.DFS(reader, graph);
                    break;
                case 9:
                    main.flightAvailable(reader, graph);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Wrong Choice, Enter again!!!");
                    choice = 10;
            }
        } while(choice != 0);
        System.out.println("Thank You for using our Application!!!");
    }

    private void addVertex(Scanner reader, Graph graph) {
        String airport = reader.nextLine().toUpperCase();

        graph.addVertex(airport);
    }

    private void removeVertex(Scanner reader, Graph graph) {
        String airport = reader.nextLine().toUpperCase();

        graph.removeVertex(airport);
    }

    private void addEdge(Scanner reader, Graph graph) {
        String source = reader.next().toUpperCase();
        String destination = reader.next().toUpperCase();
        int airFair = reader.nextInt();
        reader.nextLine();

        graph.addEdge(source, destination, airFair);
    }

    private void removeEdge(Scanner reader, Graph graph) {
        String source = reader.next().toUpperCase();
        String destination = reader.next().toUpperCase();
        reader.nextLine();

        graph.removeEdge(source, destination);
    }

    private void BFS(Scanner reader, Graph graph) {
        String source = reader.nextLine().toUpperCase();

        graph.BFS(source);
    }

    private void DFS(Scanner reader, Graph graph) {
        String source = reader.nextLine().toUpperCase();

        graph.DFS(source);
    }

    private void flightAvailable(Scanner reader, Graph graph) {
        String source = reader.next().toUpperCase();
        String destination = reader.next().toUpperCase();
        reader.nextLine();

        graph.flightAvailable(source, destination);
    }
}
