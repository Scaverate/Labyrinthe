package tools;

public enum MazeCouloirImage {
    Couloir_0("Couloir_1_1_0_1", "piece_labyrinthe_type_0.png"),
    Couloir_1("Couloir_1_0_1_0", "piece_labyrinthe_type_1.png"),
    Couloir_2("Couloir_1_1_0_0", "piece_labyrinthe_type_2.png"),
    Couloir_3("Couloir_1_0_0_1", "piece_labyrinthe_type_3.png"),
    Couloir_4("Couloir_0_1_0_1", "piece_labyrinthe_type_4.png"),
    Couloir_5("Couloir_0_1_1_0", "piece_labyrinthe_type_5.png"),
    Couloir_6("Couloir_0_0_1_1", "piece_labyrinthe_type_6.png"),
    Couloir_7("Couloir_0_1_1_1", "piece_labyrinthe_type_7.png"),
    Couloir_8("Couloir_1_1_1_0", "piece_labyrinthe_type_8.png"),
    Couloir_9("Couloir_1_0_1_1", "piece_labyrinthe_type_9.png")
    ;

    public String name;
    public String imageFile;

    MazeCouloirImage (String name, String imageFile) {
        this.name = name;
        this.imageFile = imageFile;
    }
}