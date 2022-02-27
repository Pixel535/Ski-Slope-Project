package Projekt;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Kolejka {

	ArrayBlockingQueue<Narciarz> wjazd;
	LinkedBlockingQueue czekaj;
	public int Predkosc_jazdy_wyciagu;
	public int ilosc_siedzen;
	public double szansa_na_przerwe;
	
	public Kolejka(LinkedBlockingQueue czekaj, int Predkosc_jazdy_wyciagu, int ilosc_siedzen, double szansa_na_przerwe) {
		this.ilosc_siedzen = ilosc_siedzen;
		this.wjazd = new ArrayBlockingQueue<Narciarz>(ilosc_siedzen);
		for(int i = 0; i < ilosc_siedzen; i++)
		{
			try {
				this.wjazd.put(new Narciarz());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.czekaj = czekaj;
		this.Predkosc_jazdy_wyciagu = Predkosc_jazdy_wyciagu;
		this.szansa_na_przerwe = szansa_na_przerwe;
	}
	
	public int narciarze_na_wyciagu() {
		int ilosc_narciarzy = 0;
		Iterator loopIT = this.wjazd.iterator();
		while(loopIT.hasNext()) {
			Narciarz nastepny = (Narciarz) loopIT.next();
			if(nastepny.numer == 0) {
				ilosc_narciarzy++;
			}
		}
		return (this.wjazd.size() - ilosc_narciarzy);
	}
	
}
