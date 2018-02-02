/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intersect;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author MazyK
 */
public class Intersect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int k = 1;
        int p [][] = new int [2][(int) Math.pow(10,k)];
        rnd(k,p);
        
        Arrays.sort(p[0]);
        Arrays.sort(p[1]);
        
        for(int i = 0;i<p[0].length;i++){
            System.out.format("%d ",p[0][i]);
        }
        System.out.print("\n");
        for(int i = 0;i<p[1].length;i++){
            System.out.format("%d ",p[1][i]);
        }
        
        int minmax[] = min(p);
        System.out.print("\n");
        System.out.print(minmax[0]);
        System.out.print("\n");
        System.out.print(minmax[1]);
        int exist[] = {0};
        int idapozice[] = vhodnost(p,minmax,exist);
        System.out.print("\n");
        System.out.print(idapozice[0]);
        System.out.print("\n");
        System.out.print(idapozice[1]);
        System.out.print("\n");
        System.out.print(exist[0]);
        intersect(p,exist,idapozice);
      
    }
    /** Metoda pro generování náhodných čísel
     * @param k - exponent
     * @param p - dvourozměrné pole reprezentující posloupnosti
     */    
    public static void rnd(int k, int p[][]){
        Random numbers = new Random();
        for(int i =0;i<Math.pow(10,k);i++){
//          interval čísel pro generování 
            p[0][i] = numbers.nextInt(10);
            p[1][i] = numbers.nextInt(10);
        }
    }
    /** Metoda, která charakterizuje vztahy mezi minimy a maximy posloupností
     * @param p - dvourozměrné pole reprezentující posloupnosti
     * @return pole s čísly od 0 do 2, které reprezentují vztah mezi
     * minimy a maximy
     */  
    public static int[] min(int p[][]){
        int minmax[] = new int [2];
//      větší minimum -> prvky dané posloupnosti budou hledány ve druhé
        if (p[0][0] > p[1][0]){
            minmax[0] = 0;
        }
        else if (p[0][0] < p[1][0]){
             minmax[0] = 1;
        }
        else{
            minmax[0] = 2;
        }
//      menší maximum i minimum -> budu hledat prvky z dané posloupnosti   
        if(p[0][p[0].length-1] < p[1][p[1].length-1]){
            minmax[1] = 0;
        }
        else if (p[0][p[0].length-1] > p[1][p[1].length-1]){
            minmax[1] = 1;
        }
        else{
            minmax[1] = 2;
        }
        return minmax;
    }
    /** Metoda pro výběr vhodnější posloupnosti pro hledání stejných čísel
     * @param p - dvourozměrné pole reprezentující posloupnosti
     * @param minmax - pole, které obsahuje vztahy mezi minimy a maximy
     * posloupností
     * @return - pole s informací o vhodnější posloupnosti a případně s pozicí
     * menšího maxima v druhé posloupnosti
     */  
    public static int []vhodnost(int p[][], int minmax[], int exist[]){
        int idaprvek[] = new int [2];
        idaprvek[1] = -1;
        int j=0;
//      Pokud jsou minima stejná, využiji vztah maxim a zavolám metodu
//      vyhledavanimax. 
        if(minmax[0]==2){
            if(minmax[1] == 0){
                int l = 0;
                int r = p[1].length-1;
                int m = p[0][p[0].length-1];
                j++;
                idaprvek[1] = vyhledavani(p,l,r,m,j,exist);
                idaprvek[0] = 1;
            }
            else if(minmax[1] == 1){
                int l = 0;
                int r = p[0].length-1;
                int m = p[1][p[1].length-1];
                idaprvek[1] = vyhledavani(p,l,r,m,j,exist);
                idaprvek[0] = 0;
            }
        }
//      pokud minima nejsou stejná, pokračuji porovnáním všech možností, abych
//      nalezl nejvhodnější interval pro vyhledávání
        else{
            for(int i =0;i<2;i++){
                if(minmax[0]==i){
                    if(minmax[1] == 0+i){
                        idaprvek[0] = 0+i;
                    }
                    else if(minmax[1] == 1-i){
                        int l = 0;
                        int r = p[i].length-1;                      
                        int m = p[1-i][p[1-i].length-1];
                        idaprvek[1] = vyhledavani(p,l,r,m,j+i,exist);
                        idaprvek[0] = 0+i;
                    }
                    else {
                        idaprvek[0] = 0+i;
                    }
                    break;
                }
            }
        }
        return idaprvek;
    }
    /** Metoda pro vyhledávání pozice menšího maxima v druhé posloupnosti
     * @param p - dvourozměrné pole reprezentující posloupnosti
     * @param l - levá strana intervalu.
     * @param r - pravá strana intervalu.
     * @param m - hledané menší maximum 
     * @param j - index pro změnu posloupnosti, v které hledáme
     * @return -  pozice, do které je nutné hledat stejná čísla.
     */  
    public static int vyhledavani(int p[][], int l, int r, int m,int j,
            int [] exist){
        int k = (l+r)/2;
        if(r-l <= 1){
            if (p[0+j][r]==m){
                return r;
            }
            else if(p[0+j][l]==m){
                return l;
            }
            else {
//              pokud zde maximum není, je mu přiřazena pozice na které by se
//              vyskytovalo při jeho doplnění do posloupnosti
                exist[0] = -1;
                return r;
            }
        }
        if(m == p[0+j][k]){
            return k;
        }
        else if (m > p[0+j][k]){
            return vyhledavani(p,k,r,m,j,exist);
        }
        else {
            return vyhledavani(p,l,k,m,j,exist);
        }  
    }
    public static void intersect(int p[][],int exist [], int idapozice []){
        int l = 0;int r;int j =0;int mmax;int g =0;
        if (idapozice[0] == 0){
            j++;
            r = p[1].length-1;
            if(idapozice[1] == -1){
               mmax = p[0].length;
            }
            else {
                mmax = idapozice[1]+1;
            }
        }
        else {
            r = p[0].length-1;
            if(idapozice[1] == -1){
               mmax = p[1].length;
            }
            else {
                mmax = idapozice[1]+1;
            }
        }
        int intersect [] = new int [mmax];
        System.out.print("\n");
        System.out.print(mmax);
        for(int i =0;i<mmax;i++){
            exist[0] = -2;
            int m = p[1-j][i];
            int h = vyhledavani(p,l,r,m,j,exist);
            if (exist[0] != -1){
                intersect [i-g] = p[j][h];
                p[j][h] = p[1-j][0];
            }
            else {
                g++;
            }
        }        
        int in [] = new int [mmax-g];
        for(int i=0;i<in.length;i++){
            in[i] = intersect[i]; 
        }
        System.out.print("\n");
        for(int i = 0;i<in.length;i++){
            System.out.format("%d ",intersect[i]);
        }
        
    }
}
