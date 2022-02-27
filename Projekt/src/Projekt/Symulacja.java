package Projekt;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;


public class Symulacja extends SwingWorker<Void, String> {

	public int Predkosc_jazdy_wyciagu = 1000;
	public int Predkosc_jazdy_wyciagu2 = 500;
	public int ilosc_siedzen;
	public double szansa_na_przerwe;
	public int ilosc_narciarzy;
	public int ilosc_narciarzy_wpol;
	public int czas_zjazdu;
	public int pusty_wyciag;
	public int numer_wyciagu;
	public LinkedBlockingQueue czekaj;
	public LinkedBlockingQueue czekaj2;
	
	JTextArea konsola;
	JButton button;
	Animacja animacja;
	
	
	public Symulacja(JTextArea konsola, JButton button,Animacja animacja, int ilosc_siedzen, double szansa_na_przerwe, LinkedBlockingQueue czekaj, LinkedBlockingQueue czekaj2, int czas_zjazdu, int ilosc_narciarzy, int numer_wyciagu) {
		this.konsola = konsola;
		this.button = button;
		//this.Predkosc_jazdy_wyciagu = Predkosc_jazdy_wyciagu;
		this.ilosc_siedzen = ilosc_siedzen;
		this.szansa_na_przerwe = szansa_na_przerwe;
		this.czas_zjazdu = czas_zjazdu;
		this.numer_wyciagu = numer_wyciagu;
		this.czekaj = czekaj;
		this.czekaj2 = czekaj2;
		this.ilosc_narciarzy = ilosc_narciarzy;
		this.animacja = animacja;
	}

	@Override
	public Void doInBackground() throws Exception {
		
		animacja.liczba_narciarzy = ilosc_narciarzy;
		animacja.wysokosc_kwnr = ilosc_narciarzy/10;
		if(numer_wyciagu == 1) {
			
		Kolejka kolejka = new Kolejka(czekaj, Predkosc_jazdy_wyciagu, ilosc_siedzen, szansa_na_przerwe);
		while(!isCancelled()) {
			
			ilosc_narciarzy = kolejka.narciarze_na_wyciagu();
			
			System.out.println("[Na Wyciagu nr #"+ numer_wyciagu+"]: " + kolejka.wjazd+"\n");
			System.out.println("[W kolejce na dole oczekuje]: " + czekaj+"\n");
			konsola.append("[Na Wyciagu nr #"+ numer_wyciagu+"]: " + kolejka.wjazd+"\n");
			konsola.append("[W kolejce na dole oczekuje]: " + czekaj+"\n");
			konsola.append("\n");
			
			animacja.wielkosc_kol1 = ilosc_siedzen;
			animacja.wielkosc_kol2 = ilosc_siedzen/2;
			animacja.wielkosc_kol3 = ilosc_siedzen - animacja.wielkosc_kol2;
			animacja.czekaj = czekaj;
			animacja.wjazd = kolejka.wjazd;
			
			Thread.sleep(Predkosc_jazdy_wyciagu);
			Narciarz nastepnyWkolejce;
			try {
			Random rand = new Random();
			if(rand.nextDouble() < szansa_na_przerwe) {
				int stop = ThreadLocalRandom.current().nextInt(0, 8000 + 1);
				animacja.wyc1_stop = 1;
				System.out.println("\n----------- WYCIAG #"+ numer_wyciagu +" MA PRZERWE TECHNICZNA -----------\n");
				Thread.sleep(stop);
				System.out.println("\n----------- WZNOWIENIE PRACY WYCIAGU #"+ numer_wyciagu +"-----------\n");
				konsola.append("\n----------- WYCIAG #"+ numer_wyciagu +" MA PRZERWE TECHNICZNA -----------\n");
				konsola.append("\n----------- WZNOWIENIE PRACY WYCIAGU #"+ numer_wyciagu +"-----------\n");
				konsola.append("\n");
				animacja.wyc1_stop = 0;
			}
			Narciarz naGorze = (Narciarz) kolejka.wjazd.peek();
			if(naGorze.numer != 0)
			{
				Thread NarTh = new Thread((Narciarz) kolejka.wjazd.take());
				naGorze.numer_wyciagu = 1;
				animacja.narciarze_na_stoku1++;
				NarTh.start();
			}
			else {
				kolejka.wjazd.take();
			}
			
			if(czekaj.isEmpty()){
				kolejka.wjazd.put(new Narciarz());
				
			}
			else {
				nastepnyWkolejce = (Narciarz) czekaj.take();
				kolejka.wjazd.put(nastepnyWkolejce);
				
				}
			} catch (InterruptedException e) {
				
			}
		}
		}
		else if (numer_wyciagu == 2){
			Kolejka kolejka = new Kolejka(czekaj, Predkosc_jazdy_wyciagu2, ilosc_siedzen, szansa_na_przerwe);
			while(!isCancelled()) {
				
				ilosc_narciarzy = kolejka.narciarze_na_wyciagu();
				
				System.out.println("[Na Wyciagu nr #"+ numer_wyciagu+"]: " + kolejka.wjazd+"\n");
				System.out.println("[W kolejce na dole oczekuje]: " + czekaj+"\n");
				konsola.append("[Na Wyciagu nr #"+ numer_wyciagu+"]: " + kolejka.wjazd+"\n");
				konsola.append("[W kolejce na dole oczekuje]: " + czekaj+"\n");
				konsola.append("\n");
				
				
				animacja.wjazd2 = kolejka.wjazd;
				
				Thread.sleep(Predkosc_jazdy_wyciagu2);
				Narciarz nastepnyWkolejce;
				try {
				Random rand = new Random();
				if(rand.nextDouble() < szansa_na_przerwe) {
					int stop = ThreadLocalRandom.current().nextInt(0, 8000 + 1);
					animacja.wyc2_stop = 1;
					System.out.println("\n----------- WYCIAG #"+ numer_wyciagu +" MA PRZERWE TECHNICZNA -----------\n");
					Thread.sleep(stop);
					System.out.println("\n----------- WZNOWIENIE PRACY WYCIAGU #"+ numer_wyciagu +"-----------\n");
					konsola.append("\n----------- WYCIAG #"+ numer_wyciagu +" MA PRZERWE TECHNICZNA -----------\n");
					konsola.append("\n----------- WZNOWIENIE PRACY WYCIAGU #"+ numer_wyciagu +"-----------\n");
					konsola.append("\n");
					animacja.wyc2_stop = 0;
				}
				Narciarz wPolowie = (Narciarz) kolejka.wjazd.peek();
				if(wPolowie.numer != 0)
				{
					int wybor;
					Random wybor_zj_wj = new Random();
					wybor = wybor_zj_wj.nextInt(2)+1;// 1 == wjazd ;;;;; 2 == zjazd
					if(wybor == 1) 
					{
						
						Narciarz add2 = new Narciarz(czekaj2, czas_zjazdu, animacja);
						Narciarz zmiana = (Narciarz) kolejka.wjazd.take();
						add2.numer = zmiana.numer;
						czekaj2.add(add2);
						
					}
					else if(wybor == 2)
					{
						Thread NarTh = new Thread((Narciarz) kolejka.wjazd.take());
						wPolowie.numer_wyciagu = 2;
						animacja.narciarze_na_stoku2++;
						NarTh.start();
						
					}
					
				}
				else {
					kolejka.wjazd.take();
				}
				
				if(czekaj.isEmpty()){
					kolejka.wjazd.put(new Narciarz());
					
				}
				else {
					nastepnyWkolejce = (Narciarz) czekaj.take();
					kolejka.wjazd.put(nastepnyWkolejce);
					
					}
				} catch (InterruptedException e) {
					
				}
			}
		}
		else if(numer_wyciagu == 3)
		{
			
			Kolejka kolejka2 = new Kolejka(czekaj2, Predkosc_jazdy_wyciagu2, ilosc_siedzen, szansa_na_przerwe);
			
			while(!isCancelled()) {
				ilosc_narciarzy_wpol = kolejka2.narciarze_na_wyciagu();
				
				System.out.println("[Na Wyciagu nr #3]: " + kolejka2.wjazd+"\n");
				System.out.println("[W kolejce w polowie oczekuje]: " + czekaj2 +"\n");
				konsola.append("[Na Wyciagu nr #3]: " + kolejka2.wjazd+"\n");
				konsola.append("[W kolejce w polowie oczekuje]: " + czekaj2 +"\n");
				konsola.append("\n");
				
				animacja.czekaj2 = czekaj2;
				animacja.wjazd3 = kolejka2.wjazd;
				
				Thread.sleep(Predkosc_jazdy_wyciagu2);
				
				Narciarz nastepnyWkolejce2;
				try {
				Random rand2 = new Random();
				if(rand2.nextDouble() < szansa_na_przerwe) {
					int stop2 = ThreadLocalRandom.current().nextInt(0, 8000 + 1);
					animacja.wyc3_stop = 1;
					System.out.println("\n----------- WYCIAG #3 MA PRZERWE TECHNICZNA -----------\n");
					Thread.sleep(stop2);
					System.out.println("\n----------- WZNOWIENIE PRACY WYCIAGU #3 -----------\n");
					konsola.append("\n----------- WYCIAG #3 MA PRZERWE TECHNICZNA -----------\n");
					konsola.append("\n----------- WZNOWIENIE PRACY WYCIAGU #3 -----------\n");
					konsola.append("\n");
					animacja.wyc3_stop = 0;
				}
				Narciarz naGorze2 = (Narciarz) kolejka2.wjazd.peek();
				if(naGorze2.numer != 0)
				{
					int wybor2;
					Random wybor_zjp_zjd = new Random();
					wybor2 = wybor_zjp_zjd.nextInt(2)+1;//1 do pol;;;;2 na dol
					if(wybor2 == 1) {
						
						Thread NarTh = new Thread((Narciarz) kolejka2.wjazd.take());
						naGorze2.numer_wyciagu = 3;
						animacja.narciarze_na_stoku3++;
						NarTh.start();
							
					}
					else if (wybor2 == 2) {
						naGorze2.czekaj = czekaj;
						Thread NarTh = new Thread((Narciarz) kolejka2.wjazd.take());
						naGorze2.numer_wyciagu = 3;
						animacja.narciarze_na_stoku3++;
						NarTh.start();
						
						
					}
				}
				else {
					kolejka2.wjazd.take();
				}
				
				if(czekaj2.isEmpty()){
					kolejka2.wjazd.put(new Narciarz());
					
				}
				else {
					nastepnyWkolejce2 = (Narciarz) czekaj2.take();
					kolejka2.wjazd.put(nastepnyWkolejce2);
					
					}
				} catch (InterruptedException e) {
					
				}
			}
		}
		return null;
	}
	

}