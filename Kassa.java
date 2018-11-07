/*************************************************************************
 *  Saker att utforska 1: inte tillåta dubblerade valörer i Kassa.
 *  Använd en collection som kan kolla efter dubletter snabbt.
 *  Annars kan vi modifiera den sorterade arrayen genom att iterera
 *  genom hela arrayen och kolla om värdet är == det föregående värdet.
 *
 *  Alternativt använda array.contains() i en loop. Ska fundera vad som är snabbast.
 *
 *  Saker att utforska 2: Använd en eller två stacks för att lagra betalningarna.
 *  Använd Stack.peek()? för att returnera den mest nyliga betalningen.
 *************************************************************************/

package stud.ltu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kassa {

    /**
     * int-array som ska hålla valörerna, möjliggör att räkna
     *  med olika valörkombinationer.
     */
    private int[] valorer;

    //objektunikt värde med antalet valörer
    private int antalVal;

    //Skapa en lista att lagra betalningarna i
    private List<Betalning> betList = new ArrayList<Betalning>();

    /**
     * Konstruktorn tar en int-array med valörer som indata
     */
    Kassa(int[] array){
        // sortera valörerna i stigande ordning
        valorer = array;
        Arrays.sort(valorer);
        this.antalVal = array.length;
    }

    public void regBetal(int betalning, int kostnad){
        Betalning b = new Betalning();
        b.setBetalat(betalning);
        b.setKostnad(kostnad);
        b.beraknaVaxel();
        betList.add(b);
    }


    /**
     * En privat klass som kan registrera flera betalningsobjekt på en viss kassa.
     * */
    private class Betalning{
        private int betalat;
        private int kostnad;
        private int[] vaxelAntal;
        private int kvarSumma;

        Betalning(){
            vaxelAntal = new int[antalVal];
        }

        public void setBetalat(int bet){
            this.betalat = bet;
        }

        private int getBetalat(){
            return betalat;
        }

        public void setKostnad(int kostn){
            this.kostnad = kostn;
        }

        private int getKostnad(){
            return kostnad;
        }

        /**
         * Metod för att beräkna antal av varje valör som ska återbetalas som växel.
         */
        private void beraknaVaxel(){

            int summa = beraknaVaxelSumma();

            //pga sortering hamnar största värdet sist i arrayen
            for (int i = antalVal; i > 0; i--){
                //summan som ska betalas tillbaka som växel
                this.vaxelAntal[i - 1] = summa / valorer[i - 1];
                summa = summa % valorer[i - 1];
            }

            /**
             * Om inga 1-valörer finns kan det vara växel som inte kan betalas ut,
             * vi lagrar detta i betalningens variabel kvarSumma
             */
            this.kvarSumma = summa;
        }

        /**
         * Metod för att beräkna summan som ska återbetalas som växel.
         * Ger felmeddelande om inte hela summan betalats.
         */
        private int beraknaVaxelSumma(){

            //ge felmeddelande om hela kostnaden ej är betalad
            if (this.getBetalat() < this.getKostnad()) {
                throw new IllegalArgumentException("Hela beloppet ej betalat!");
            }

            return this.getBetalat() - this.getKostnad();
        }

        private void print(){
            System.out.println("Betalat: " + betalat);
            System.out.println("Kostnad: " + kostnad);
            System.out.println("Antalväxel: " + Arrays.toString(vaxelAntal));
            System.out.println("Växel som ej kan betalas ut: " + kvarSumma);
        }
    }

    public static void main(String[] args) {

        int[] a = {20, 1000, 1, 500, 50, 2, 200, 100};
        Kassa k1 = new Kassa(a);
        k1.regBetal(95427, 798);
        Betalning betal = k1.betList.get(0);
        betal.print();


    }

}