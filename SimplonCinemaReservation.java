package io.readapt.simplon;

import java.util.*;

/**
 * Mini projet de selection Simplon
 */
public class SimplonCinemaReservation {

    private static int RANKS = 8;
    private static int COLUMNS = 9;

    private Map<Integer, List<Integer>> registry;

    public SimplonCinemaReservation() {
        this.registry = new HashMap<>();
    }

    /**
     * Ajouter une reservation
     * @param rank rangee de reservation
     * @param size nombre de places
     */
    public void addReservation(int rank, int size) {
        if (rank > 0 && rank <= RANKS && size > 0 && size <= COLUMNS) {
            this.registry.computeIfPresent(rank - 1, (k, v) -> {
                int currentSeat = v.size();
                if (COLUMNS - currentSeat < size) {
                    // pas assez de places dans cette rangee
                    System.out.println("Il n'y a pas assez de place dans cette rangee.");
                } else {
                    for (int i=0; i<size; i++) {
                        v.add(currentSeat + i);
                    }
                    System.out.println("Votre reservation de " + size + " places sur la rangee " + rank + " a ete prise en compte.");
                }
                return v;
            });

            this.registry.computeIfAbsent(rank - 1, k -> {
                List<Integer> v = new ArrayList<>();
                for (int i = 0; i<size; i++) {
                    v.add(i);
                }
                System.out.println("Votre reservation de " + size + " places sur la rangee " + rank + " a ete prise en compte.");
                return v;
            });
        }
    }

    /**
     * Afficher la salle
     */
    public void afficherCinema() {
        System.out.println("     ---------------------------");

        for (int i=0; i<RANKS; i++) {
            System.out.print((i + 1) + " => ");
            for (int j=0; j<COLUMNS; j++) {
                if (this.registry.containsKey(i) && this.registry.get(i).contains(j))
                    System.out.print("[x]");
                else
                    System.out.print("[_]");
            }
            System.out.println();
        }
        System.out.println("      1  2  3  4  5  6  7  8  9");
        System.out.println();
    }

    public void reserver(Scanner kb) {
        try {
            // menu
            System.out.println("******** Ajouter une reservation ********");
            this.afficherCinema();
            System.out.print("Combien de places voulez vous acheter ? ");
            int places = kb.nextInt();
            System.out.print("A quelle rangée voulez vous aller? ");
            int rangee = kb.nextInt();
            // add reservation
            this.addReservation(rangee, places);
            this.afficherCinema();
        }
        catch(InputMismatchException e) {
            System.out.println("Une erreur est survenue lors de votre reservation. ");
            System.out.println("Verifier que les valeurs entrees sont des nombres entiers");
        }
        catch(Exception e) {
            System.out.println("Une erreur inconnue est survenue");
        }

    }

    public void menu() {
        Scanner kb = new Scanner(System.in);
        while (true) {
            try {
                // menu
                System.out.println("********Menu ********");
                System.out.println("A- Afficher la salle");
                System.out.println("B- Reserver");
                System.out.println("Q- Quitter l'applicaation");
                System.out.print("Choisissez une option et appuyer ENTRER: ");
                String option = kb.next();
                System.out.println(" ");
                if (option.equalsIgnoreCase("A")){
                    afficherCinema();
                }
                else if (option.equalsIgnoreCase("B")) {
                    reserver(kb);
                }
                else if (option.equalsIgnoreCase("Q")) {
                    kb.close();
                    System.out.println("Merci pour votre visite. Aurevoir");
                    return;
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Erreur lors du choix de la fonction.");
                System.out.println("Verifier que la valeur est A, B ou Q");
            }
            catch(Exception e) {
                System.out.println("Une erreur inconnue est survenue");
            }
        }
    }

    public static void main(String[] args) {
        SimplonCinemaReservation app = new SimplonCinemaReservation();
        app.menu();
    }
}
