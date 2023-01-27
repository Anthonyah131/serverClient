/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tony.cliente.util;

/**
 *
 * @author ANTHONY
 */
public class CeldaPos {   // Clase para ayudarse con las letras ya puestas en la tabla o los que van a poner
    String letra;
    Long puntos;
    int posx;
    int posy;

    public CeldaPos(String letra, Long puntos, int posx, int posy) {
        this.letra = letra;
        this.puntos = puntos;
        this.posx = posx;
        this.posy = posy;
    }

    public CeldaPos() {
        this.letra = "";
        this.puntos = 0L;
        this.posx = 0;
        this.posy = 0;
    }
    
    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public Long getPuntos() {
        return puntos;
    }

    public void setPuntos(Long puntos) {
        this.puntos = puntos;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }
    
    
}
