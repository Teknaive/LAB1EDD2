package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MovieActions {

    private Movie root = null;
    private final String csvFile = "path/to/your/csvfile.csv";

    private Movie createNode(String title, Integer year, Double worldwideEarnings, Double domesticEarnings, Double foreignEarnings, Double DomesticPercentEarnings, Double ForeignPercentEarning, Movie left, Movie right, Integer height) {
        return new Movie(title, year, worldwideEarnings, domesticEarnings, foreignEarnings, DomesticPercentEarnings, ForeignPercentEarning, left, right, height);
    }

    private Integer max(Integer nodeA, Integer nodeB) {
        return (nodeA > nodeB) ? nodeA : nodeB;
    }

    private Integer getBalance(Movie node) {
        if (node == null) {
            return 0;
        }
        return (node.getRight().getHeight() - node.getLeft().getHeight());
    }

    private Movie rightRotate(Movie y) {
        Movie x = y.getLeft();
        Movie T2 = x.getRight();
        x.setRight(y);
        y.setLeft(T2);
        y.setHeight(max(y.getLeft().getHeight(), y.getRight().getHeight()) + 1);
        x.setHeight(max(x.getLeft().getHeight(), x.getRight().getHeight()) + 1);
        return x;
    }

    private Movie leftRotate(Movie x) {
        Movie y = x.getRight();
        Movie T2 = y.getLeft();
        y.setLeft(x);
        x.setRight(T2);
        x.setHeight(max(x.getLeft().getHeight(), x.getRight().getHeight()) + 1);
        y.setHeight(max(y.getLeft().getHeight(), y.getRight().getHeight()) + 1);
        return y;
    }

    public Movie insertMovie(Movie node, Movie movie) {
        if (node == null) {
            return movie;
        }
        if (movie.getTitle().compareTo(node.getTitle()) < 0) {
            node.setLeft(insertMovie(node.getLeft(), movie));
        } else if (movie.getTitle().compareTo(node.getTitle()) > 0) {
            node.setRight(insertMovie(node.getRight(), movie));
        } else {
            return node;  // El título ya existe, no insertamos duplicados
        }

        node.setHeight(1 + max(node.getLeft().getHeight(), node.getRight().getHeight()));
        Integer balance = getBalance(node);

        if (balance > 1 && movie.getTitle().compareTo(node.getLeft().getTitle()) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && movie.getTitle().compareTo(node.getRight().getTitle()) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && movie.getTitle().compareTo(node.getLeft().getTitle()) > 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        if (balance < -1 && movie.getTitle().compareTo(node.getRight().getTitle()) < 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    public Movie deleteMovie(Movie root, String title) {
        if (root == null) {
            return root;
        }

        // Buscar el nodo a eliminar
        if (title.compareTo(root.getTitle()) < 0) {
            root.setLeft(deleteMovie(root.getLeft(), title));
        } else if (title.compareTo(root.getTitle()) > 0) {
            root.setRight(deleteMovie(root.getRight(), title));
        } else {
            // Caso 1: Nodo sin hijos
            if (root.getLeft() == null && root.getRight() == null) {
                return null;
            }

            // Caso 2: Nodo con un solo hijo
            if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            }

            // Caso 3: Nodo con dos hijos, buscar predecesor 
            Movie predecesor = getMaxValueNode(root.getLeft());

            // Reemplazar el valor del nodo con el predecesor
            root.setTitle(predecesor.getTitle());
            root.setYear(predecesor.getYear());
            root.setWorldwideEarnings(predecesor.getWorldwideEarnings());
            root.setDomesticEarnings(predecesor.getDomesticEarnings());
            root.setForeignEarnings(predecesor.getForeignEarnings());
            root.setDomesticPercentEarnings(predecesor.getDomesticPercentEarnings());
            root.setForeignPercentEarning(predecesor.getForeignPercentEarning());

            // Eliminar el predecesor
            root.setLeft(deleteMovie(root.getLeft(), predecesor.getTitle()));
        }

        // Actualizar la altura del nodo actual
        root.setHeight(1 + max(root.getLeft().getHeight(), root.getRight().getHeight()));

        // Verificar el balance del nodo actual
        int balance = getBalance(root);

        // Aplicar rotaciones si el nodo está desbalanceado
        if (balance > 1 && getBalance(root.getLeft()) >= 0) {
            return rightRotate(root);
        }

        if (balance > 1 && getBalance(root.getLeft()) < 0) {
            root.setLeft(leftRotate(root.getLeft()));
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.getRight()) <= 0) {
            return leftRotate(root);
        }

        if (balance < -1 && getBalance(root.getRight()) > 0) {
            root.setRight(rightRotate(root.getRight()));
            return leftRotate(root);
        }

        return root;
    }

    // Método auxiliar para encontrar el nodo con el valor máximo en un subárbol
    private Movie getMaxValueNode(Movie node) {
        Movie current = node;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }

    public void fillNodes() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int count = 0;

            while ((line = br.readLine()) != null && count < 20) {
                String[] partes = line.split(",");

                String title = partes[0];
                Integer year = Integer.valueOf(partes[1]);
                Double worldwideEarnings = Double.valueOf(partes[2]);
                Double domesticEarnings = Double.valueOf(partes[3]);
                Double foreignEarnings = Double.valueOf(partes[4]);
                Double domesticPercentEarnings = Double.valueOf(partes[5]);
                Double foreignPercentEarnings = Double.valueOf(partes[6]);

                Movie newMovie = createNode(title, year, worldwideEarnings, domesticEarnings, foreignEarnings, domesticPercentEarnings, foreignPercentEarnings, null, null, 1);

                root = insertMovie(root, newMovie);
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
