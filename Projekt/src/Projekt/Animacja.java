package Projekt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JComponent;

public class Animacja extends JComponent implements Runnable{

	private Thread watek_animacji = null;
	int liczba_narciarzy;
	int liczba_narciarzy_kol_1;
	int liczba_narciarzy_kol_2;
	int liczba_narciarzy_kol_3;
	int wysokosc_kwnr;
	int wielkosc_kol1;
	int wielkosc_kol2;
	int wielkosc_kol3;
	int narciarze_na_wyciagu1;
	int narciarze_na_wyciagu2;
	int narciarze_na_wyciagu3;
	
	int predkosc_wyc1 = 1000;
	int predkosc_wyc2 = 500;
	int predkosc_wyc3 = 500;
	
	int narciarze_po_prawej;
	int narciarze_po_lewej;
	
	int narciarze_ktorzy_zjezdzaja;
	int narciarze_ktorzy_zjezdzaja_po_prawej;
	
	int wyc1_stop;
	int wyc2_stop;
	int wyc3_stop;
	
	int narciarze_na_stoku1;
	int narciarze_na_stoku2;
	int narciarze_na_stoku3;
	boolean isalive = true;
	
	int wielkosc_kropki;
	ArrayBlockingQueue<Narciarz> wjazd;
	ArrayBlockingQueue<Narciarz> wjazd2;
	ArrayBlockingQueue<Narciarz> wjazd3;
	LinkedBlockingQueue czekaj;
	LinkedBlockingQueue czekaj2;
	
	public Animacja() {
		
	}
	
	public void aktualizowanie_rys(Graphics g) {
		paint(g);
	}
	
	public void paintComponent(Graphics g) {
		
		wielkosc_kropki = 10;
		
		Graphics2D g2 = (Graphics2D) g;
		Graphics wyc1 = (Graphics) g;
		Graphics wyc2 = (Graphics) g;
		Graphics wyc3 = (Graphics) g;
		g.setColor(Color.black);
		g2.setStroke(new BasicStroke(3));
		g2.draw(new Line2D.Float(550,100+wielkosc_kropki*wielkosc_kol1,550,50));
		g2.drawString("Wyciag #1", 450, 50+(wielkosc_kropki*wielkosc_kol1)/2);
        
		g2.draw(new Line2D.Float(750,50+wielkosc_kropki*wielkosc_kol3,750,50));
		g2.drawString("Wyciag #3", 800, 50+(wielkosc_kropki*wielkosc_kol3)/2);
        
		g2.draw(new Line2D.Float(750,50+50+wielkosc_kropki*wielkosc_kol2,750,50+50+wielkosc_kropki*wielkosc_kol2*2));
		g2.drawString("Wyciag #2", 800, 50+50+(wielkosc_kropki*wielkosc_kol2)+(wielkosc_kropki*wielkosc_kol2/2));
		
		
		if(czekaj != null) {
			int licznik = 1;
			int i = 0;
			int x = 695+wielkosc_kropki;
			int y = 50+300+wielkosc_kropki*wielkosc_kol1;
			Iterator iterator = czekaj.iterator();
			while(iterator.hasNext()) {
				Narciarz nastepnyWkolejce = (Narciarz) iterator.next();
				g.setColor(Color.red);
				g.fillOval(x - licznik*wielkosc_kropki, y-wielkosc_kropki*i, wielkosc_kropki, wielkosc_kropki);
				if(licznik % 10 == 0)
				{
					x = 695+wielkosc_kropki + licznik*wielkosc_kropki;
					i++;
				}
				
				licznik++;
				
			}
		}
		
		if(czekaj2 != null) {
			int licznik = 1;
			int i = 0;
			int x = 725+wielkosc_kropki;
			int y = 50+10+wielkosc_kropki*wielkosc_kol2;
			Iterator iterator = czekaj2.iterator();
			while(iterator.hasNext()) {
				Narciarz nastepnyWkolejce = (Narciarz) iterator.next();
				g.setColor(Color.red);
				g.fillOval(x + licznik*wielkosc_kropki, y+wielkosc_kropki, wielkosc_kropki, wielkosc_kropki);
				licznik++;
				
			}
		}
		
		if(wjazd != null) {
			int licznik = 0;
			narciarze_na_wyciagu1 = 0;
			int x = 555+wielkosc_kropki;
			int y = 100;
			Iterator iterator = wjazd.iterator();
			while(iterator.hasNext()) {
				
				Narciarz nastepnyWkolejce = (Narciarz) iterator.next();
				if(!(nastepnyWkolejce.numer == 0)) {
					narciarze_na_wyciagu1++;
					wyc1.setColor(Color.red);
					wyc1.fillOval(x, y+licznik*wielkosc_kropki, wielkosc_kropki, wielkosc_kropki);
					if(wyc1_stop == 1)
					{
						wyc1.drawString("WYCIAG #1 MA PRZERWE TECHNICZNA", 1100, 360);
					}
				}
				licznik++;
				
			}
			
			for(int i = 0; i < narciarze_na_stoku1; i++) {
				wyc1.fillOval(400, 50+i*wielkosc_kropki, wielkosc_kropki, wielkosc_kropki);
			}
			
		}
		
		if(wjazd2 != null) {
			int licznik = 0;
			narciarze_na_wyciagu2 = 0;
			int x = 725+wielkosc_kropki;
			int y = 50+50+wielkosc_kropki*wielkosc_kol3;
			Iterator iterator = wjazd2.iterator();
			while(iterator.hasNext()) {
				Narciarz nastepnyWkolejce = (Narciarz) iterator.next();
				
				if(!(nastepnyWkolejce.numer == 0)) {
					narciarze_na_wyciagu2++;
					wyc2.setColor(Color.blue);
					wyc2.fillOval(x, y+licznik*wielkosc_kropki, wielkosc_kropki, wielkosc_kropki);
					if(wyc2_stop == 1)
					{
						wyc2.drawString("WYCIAG #2 MA PRZERWE TECHNICZNA", 1100, 380);
					}
				}
				licznik++;
			}
			
			Graphics tekst = (Graphics) g;
			tekst.setColor(Color.black);
			tekst.drawString("Z wyciagu #1 zjezdza: "+narciarze_na_stoku1+"", 1100, 300);
			tekst.drawString("Z wyciagu #2 zjezdza: "+narciarze_na_stoku2+"", 1100, 320);
			tekst.drawString("Z wyciagu #3 zjezdza: "+narciarze_na_stoku3+"", 1100, 340);
			tekst.drawString("Predkosc Wyciagu #1: " + predkosc_wyc1, 1100, 420);
			tekst.drawString("Predkosc Wyciagu #2: " + predkosc_wyc2, 1100, 440);
			tekst.drawString("Predkosc Wyciagu #3: " + predkosc_wyc3, 1100, 460);
			
			
			for(int i = 0; i < narciarze_na_stoku2; i++) {
				wyc2.setColor(Color.blue);
				wyc2.fillOval(900, 50+50+(wielkosc_kropki*wielkosc_kol2)+i*wielkosc_kropki, wielkosc_kropki, wielkosc_kropki);
			}
			
		}
		
		if(wjazd3 != null) {
			int licznik = 0;
			narciarze_na_wyciagu3 = 0;
			int x = 725+wielkosc_kropki;
			int y = 50;
			Iterator iterator = wjazd3.iterator();
			while(iterator.hasNext()) {
				Narciarz nastepnyWkolejce = (Narciarz) iterator.next();
				
				if(!(nastepnyWkolejce.numer == 0)) {
					narciarze_na_wyciagu3++;
					wyc3.setColor(Color.magenta);
					wyc3.fillOval(x, y+licznik*wielkosc_kropki, wielkosc_kropki, wielkosc_kropki);
					if(wyc3_stop == 1)
					{
						wyc3.drawString("WYCIAG #3 MA PRZERWE TECHNICZNA", 1100, 400);
					}
				}
				licznik++;
			}
			
			for(int i = 0; i < narciarze_na_stoku3; i++) {
				wyc3.fillOval(1000, 50+i*wielkosc_kropki, wielkosc_kropki, wielkosc_kropki);
			}
			
		}
		
		
	}
	
	public void start() {
		watek_animacji = new Thread(this);
		watek_animacji.start();
	}
	
	public void cancel() {
		isalive = false;
	}
	
	public void begin(){
		isalive = true;
	}
	
	public void run() {
		while(isalive) {
			repaint();
		}
	}

	
	
}

