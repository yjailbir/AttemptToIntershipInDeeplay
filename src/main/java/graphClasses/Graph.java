package graphClasses;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int INFINITY = 100000000; // это число у нас будет служить в качестве "бесконечности"
    private final Vertex[] vertexList; // список вершин
    private final int[][] relationMatrix; // матрица связей вершин
    private int countOfVertices; // текущее количество вершин
    private int countOfVertexInTree; // количество рассмотренных вершин в дереве
    private List<Path> shortestPaths; // список данных кратчайших путей
    private int currentVertex; // текущая вершина
    private int startToCurrent; //расстояние до currentVertex

    public Graph() {
        // максимальное количество вершин
        int MAX_VERTEXES = 16;
        vertexList = new Vertex[MAX_VERTEXES]; // матрица смежности
        relationMatrix = new int[MAX_VERTEXES][MAX_VERTEXES];
        countOfVertices = 0;
        countOfVertexInTree = 0;
        for (int i = 0; i < MAX_VERTEXES; i++) {// матрица смежности заполняется
            for (int k = 0; k < MAX_VERTEXES; k++) { // бесконечными расстояниями
                relationMatrix[i][k] = INFINITY; // задания значений по умолчанию
                shortestPaths = new ArrayList<>();// задается пустым
            }
        }
    }

    public void addVertex(String lab) {// задание новых вершин
        vertexList[countOfVertices++] = new Vertex(lab);
    }

    public void addEdge(int start, int end, int weight) {
        relationMatrix[start][end] = weight; // задание ребер между вершинами, с весом между ними
    }

    public void path() { // выбор кратчайшего пути
        //  задание данных для стартовой вершины
        int startTree = 0; // стартуем с вершины 0
        vertexList[startTree].setInTree(true); // включение в состав дерева первого элемента
        countOfVertexInTree = 1;

        // заполнение коротких путей для вершин смежных с стартовой
        for (int i = 0; i < countOfVertices; i++) {
            int tempDist = relationMatrix[startTree][i];
            Path path = new Path(tempDist);
            path.getParentVertices().add(0);// первым родительским элементом, будет всегда стартовая вершина
            shortestPaths.add(path);
        }
        // пока все вершины не окажутся в дереве
        while (countOfVertexInTree < countOfVertices) { // выполняем, пока количество вершин в дереве не сравняется с общим количеством вершин
            int indexMin = getMin();//получаем индекс вершины с наименшей дистанцией, из вершин еще не входящих в дерево
            int minDist = shortestPaths.get(indexMin).getDistance();// минимальная дистанция вершины, из тек которые ещё не в дереве

            if (minDist == INFINITY) {
                System.out.println("В графе пристувствуют недостижимые вершины");
                break;// в случае если остались непройденными только недостижимые вершины, мы выходим из цикла
            } else {
                currentVertex = indexMin; // переводим указатель currentVert к текущей вершине
                startToCurrent = shortestPaths.get(indexMin).getDistance();// задаем дистанцию к текущей вершине
            }

            vertexList[currentVertex].setInTree(true);  //включение текущей вершины в дерево
            countOfVertexInTree++; // увеличиваем счетчик вершин в дереве
            updateShortestPaths(); // обновление списка кратчайших путей
        }

        displayPaths(); // выводим в консоль результаты
    }

    public void clean() { // очиска дерева
        countOfVertexInTree = 0;
        for (int i = 0; i < countOfVertices; i++) {
            vertexList[i].setInTree(false);
        }
    }

    private int getMin() {
        int minDist = INFINITY; // за точку старта взята "бесконечная" длина
        int indexMin = 0;
        for (int i = 1; i < countOfVertices; i++) {// для каждой вершины
            if (!vertexList[i].isInTree() && shortestPaths.get(i).getDistance() < minDist) { // если вершина ещё не ве дереве и её растояние меньше старого минимума
                minDist = shortestPaths.get(i).getDistance(); // задаётся новый минимум
                indexMin = i; // обновление индекса вершины содержащую минимаьную дистанцию
            }
        }
        return indexMin; //возвращает индекс вершины с наименшей дистанцией, из вершин еще не входящих в дерево
    }

    private void updateShortestPaths() {
        int vertexIndex = 1; // стартовая вершина пропускается
        while (vertexIndex < countOfVertices) { // перебор столбцов

            if (vertexList[vertexIndex].isInTree()) { // если вершина column уже включена в дерево, она пропускается
                vertexIndex++;
                continue;
            }
            // вычисление расстояния для одного элемента sPath
            // получение ребра от currentVert к column
            int currentToFringe = relationMatrix[currentVertex][vertexIndex];
            // суммирование всех расстояний
            int startToFringe = startToCurrent + currentToFringe;
            // определение расстояния текущего элемента vertexIndex
            int shortPathDistance = shortestPaths.get(vertexIndex).getDistance();

            // сравнение расстояния через currentVertex с текущим расстоянием в вершине с индексом vertexIndex
            if (startToFringe < shortPathDistance) {// если меньше, то у вершины под индексом vertexIndex будет задан новый кратчайший путь
                List<Integer> newParents = new ArrayList<>(shortestPaths.get(currentVertex).getParentVertices());//создаём копию списка родителей вершины currentVert
                newParents.add(currentVertex);// задаём в него и currentVertex как предыдущий
                shortestPaths.get(vertexIndex).setParentVertices(newParents); // соохраняем новый маршут
                shortestPaths.get(vertexIndex).setDistance(startToFringe); // соохраняем новую дистанцию
            }
            vertexIndex++;
        }
    }

    private void displayPaths() { // метод для вывода кратчайших путей на экран
        for (int i = 0; i < countOfVertices; i++) {
            System.out.print(vertexList[i].getLabel() + " = ");
            if (shortestPaths.get(i).getDistance() == INFINITY) {
                System.out.println("0");
            } else {
                StringBuilder result = new StringBuilder(shortestPaths.get(i).getDistance() + " (");
                List<Integer> parents = shortestPaths.get(i).getParentVertices();
                for (Integer parent : parents) {
                    result.append(vertexList[parent].getLabel()).append(" -> ");
                }
                System.out.println(result.toString() + vertexList[i].getLabel() + ")");
            }
        }
    }
}