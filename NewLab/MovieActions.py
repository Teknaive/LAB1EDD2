import csv
from NewLab import Movie

class MovieActions:
    def __init__(self, root: "Movie" = None) -> None:
        self.root = root

    def __height_r(self, movie: "Movie") -> int:
        if movie is None:
            return 0
        return 1 + max(self.__height_r(movie.left), self.__height_r(movie.right))

    def __get_balance(self, movie: "Movie") -> int:
        if movie is None:
            return 0
        return self.__height_r(movie.left) - self.__height_r(movie.right)

    def __rotate_right(self, y: "Movie") -> "Movie":
        x = y.left
        T2 = x.right

        # Perform rotation
        x.right = y
        y.left = T2

        return x

    def __rotate_left(self, x: "Movie") -> "Movie":
        y = x.right
        T2 = y.left

        # Perform rotation
        y.left = x
        x.right = T2

        return y

    def __balance(self, node: "Movie") -> "Movie":
        balance = self.__get_balance(node)

        # Left heavy
        if balance > 1:
            # Left-Right case
            if self.__get_balance(node.left) < 0:
                node.left = self.__rotate_left(node.left)
            # Left-Left case
            return self.__rotate_right(node)

        # Right heavy
        if balance < -1:
            # Right-Left case
            if self.__get_balance(node.right) > 0:
                node.right = self.__rotate_right(node.right)
            # Right-Right case
            return self.__rotate_left(node)

        return node

    def search(self, data: str) -> any:
        p, pad = self.root, None
        while p is not None:
            if data == p.data:
                return p, pad
            else:
                pad = p
                if data < p.data:
                    p = p.left
                else:
                    p = p.right
        return p, pad

    def insert(self, data: Movie) -> bool:
        to_insert = Movie(data)
        if self.root is None:
            self.root = to_insert
            return True
        else:
            self.root = self.__insert_r(self.root, to_insert)
            return True

    def __insert_r(self, node: "Movie", to_insert: "Movie") -> "Movie":
        if node is None:
            return to_insert

        if to_insert.data < node.data:
            node.left = self.__insert_r(node.left, to_insert)
        elif to_insert.data > node.data:
            node.right = self.__insert_r(node.right, to_insert)
        else:
            return node  # Duplicates are not allowed

        return self.__balance(node)

    def delete(self, data: Movie) -> bool:
        if self.root is None:
            return False
        else:
            self.root, deleted = self.__delete_r(self.root, data)
            return deleted

    def __delete_r(self, node: "Movie", data: Movie) -> ("Movie", bool): # type: ignore
        if node is None:
            return node, False

        if data < node.data:
            node.left, deleted = self.__delete_r(node.left, data)
        elif data > node.data:
            node.right, deleted = self.__delete_r(node.right, data)
        else:
            deleted = True
            if node.left is None:
                return node.right, deleted
            elif node.right is None:
                return node.left, deleted

            temp = self.__min_value_node(node.right)
            node.data = temp.data
            node.right, _ = self.__delete_r(node.right, temp.data)

        return self.__balance(node), deleted

    def __min_value_node(self, node: "Movie") -> "Movie":
        current = node
        while current.left is not None:
            current = current.left
        return current

def fill_nodes(self, csv_file: str) -> None:
        try:
            csv_file = "C:\Users\subje\Documents\Projects and materials\NewLab\dataset_movies.csv"
            with open(csv_file, newline='', encoding='utf-8') as csvfile:
                csvreader = csv.reader(csvfile)
                next(csvreader)  # Saltar la primera línea (encabezado)
                
                count = 0
                for row in csvreader:
                    if count >= 20:  # Solo leer las primeras 20 líneas después del encabezado
                        break
                    
                    # Asumimos que las columnas están en el orden esperado: título, año, ganancias, etc.
                    title = row[0]
                    year = int(row[1])
                    worldwide_earnings = float(row[2])
                    domestic_earnings = float(row[3])
                    foreign_earnings = float(row[4])
                    domestic_percent_earnings = float(row[5])
                    foreign_percent_earnings = float(row[6])

                    # Crear la película (Movie) con los datos del CSV
                    new_movie = Movie(
                        title=title, 
                        year=year, 
                        worldwide_earnings=worldwide_earnings, 
                        domestic_earnings=domestic_earnings, 
                        foreign_earnings=foreign_earnings, 
                        domestic_percent_earnings=domestic_percent_earnings, 
                        foreign_percent_earnings=foreign_percent_earnings
                    )

                    # Insertar la nueva película en el árbol AVL
                    self.insert(new_movie)
                    count += 1

        except FileNotFoundError:
            print(f"El archivo {csv_file} no se encontró.")
        except Exception as e:
            print(f"Ocurrió un error al leer el archivo CSV: {e}")

