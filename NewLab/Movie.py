class Movie:
    def __init__(self, title, year, worldwide_earnings, domestic_earnings, foreign_earnings,
                 domestic_percent_earnings, foreign_percent_earnings):
        self.title = title
        self.year = year
        self.worldwide_earnings = worldwide_earnings
        self.domestic_earnings = domestic_earnings
        self.foreign_earnings = foreign_earnings
        self.domestic_percent_earnings = domestic_percent_earnings
        self.foreign_percent_earnings = foreign_percent_earnings
        self.left: "Movie" = None
        self.right: "Movie" = None
        self.height = 1