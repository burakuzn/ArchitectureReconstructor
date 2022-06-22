package nl.wur;

public class Main {

    public static void main(String[] args) {
        ArchitectureReconstructor reconstructor = new ArchitectureReconstructor();
        reconstructor.reconstruct(args[0]);
    }

}
