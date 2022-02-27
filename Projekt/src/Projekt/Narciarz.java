package Projekt;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Narciarz implements Runnable{

	public int Predkosc_jazdy;
	public LinkedBlockingQueue czekaj;
	static int licznik = 0;
	public int numer;
	public Random rand = new Random();
	public int numer_wyciagu = 0;
	Animacja animacja;
	
	public Narciarz(LinkedBlockingQueue czekaj, int czas_zjazdu, Animacja animacja) {
		Predkosc_jazdy = ThreadLocalRandom.current().nextInt(2000, czas_zjazdu+1);
		this.czekaj = czekaj;
		licznik++;
		this.numer = licznik;
		this.animacja = animacja;
	}
	
	public Narciarz(){
		this.numer = 0;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(Predkosc_jazdy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			czekaj.add(this);
			if(numer_wyciagu == 1)
			{
				animacja.narciarze_na_stoku1--;
			}
			else if(numer_wyciagu == 2)
			{
				animacja.narciarze_na_stoku2--;
			}
			else if(numer_wyciagu == 3)
			{
				animacja.narciarze_na_stoku3--;
			}
		}
		
	}
	
	public String toString() {
		if (numer == 0) {
			return "PUSTE";
		}
		return Integer.toString(numer);
	}
	
	
	
}
