package Projekt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

import com.sun.prism.paint.Color;

public class GUI {
	
	Symulacja n;
	Symulacja n2;
	Symulacja n3;
	Narciarz add;
	
	
	int ilosc_siedzen;
	double szansa_na_przerwe;
	int ilosc_narciarzy;
	int czas_zjazdu;
	
	
	int ilosc_siedzen2 = ilosc_siedzen/2;
	int ilosc_siedzen3 = ilosc_siedzen-ilosc_siedzen2;
	double szansa_przerwy2 = szansa_na_przerwe/2;
	
	
	String ilosc_siedzenS = "10";
	String szansa_na_przerweS = "0.05";
	String ilosc_narciarzyS = "30";
	String czas_zjazduS = "2000";
	
	
	public GUI(){
	
		
		JFrame gui = new JFrame();
		JTextArea konsola = new JTextArea();
		JScrollPane scrollpane = new JScrollPane(konsola);
		DefaultCaret caret = (DefaultCaret)konsola.getCaret();
		
		Animacja animacja = new Animacja();
		
		JPanel layout_dol= new JPanel(new BorderLayout());
		JPanel layout_gora= new JPanel(new BorderLayout());
		JPanel menu = new JPanel(new BorderLayout());
		JPanel dane_do_zmian = new JPanel(new BorderLayout());
		JPanel space = new JPanel(new BorderLayout());
		JPanel buttons= new JPanel(new BorderLayout());
		JScrollPane animacja_border = new JScrollPane(animacja);
		
		animacja.setPreferredSize(new Dimension(50000, 50000));
		animacja_border.setPreferredSize(new Dimension(600, 600));
		
		Font font = new Font("Open Sans", Font.BOLD, 20);
		Font font2 = new Font("Open Sans", Font.PLAIN, 20);
		
		
		konsola.setFont(font);
		
		JSplitPane layout= new JSplitPane(JSplitPane.VERTICAL_SPLIT, layout_gora, layout_dol);
		
		JButton zmiana = new JButton("Akceptuj zmiany");
		zmiana.setFont(font2);
		JButton start = new JButton("Start");
		start.setFont(font2);
		JButton stop = new JButton("Stop");
		stop.setFont(font2);
		
		
		
		JTextField ilosc_siedzen_text = new JTextField(ilosc_siedzenS);
		ilosc_siedzen_text.setToolTipText("Musi byc int");
		ilosc_siedzen_text.setFont(font);
		JTextField szansa_na_przerwe_text = new JTextField(szansa_na_przerweS);
		szansa_na_przerwe_text.setToolTipText("Musi byc double w przedziale <0,1>");
		szansa_na_przerwe_text.setFont(font);
		JTextField ilosc_narciarzy_text = new JTextField(ilosc_narciarzyS);
		ilosc_narciarzy_text.setToolTipText("Musi byc int");
		ilosc_narciarzy_text.setFont(font);
		JTextField czas_zjazdu_text = new JTextField(czas_zjazduS);
		czas_zjazdu_text.setToolTipText("Musi byc int > 2000");
		czas_zjazdu_text.setFont(font);
		
		
		dane_do_zmian.setLayout(new BoxLayout(dane_do_zmian, BoxLayout.Y_AXIS));
		dane_do_zmian.add(Box.createVerticalStrut(30));
		
		
		JLabel il = new JLabel("Ilosc siedzen na wyciagu nr 1: ");
		il.setFont(font);
		dane_do_zmian.add(il);
		dane_do_zmian.add(ilosc_siedzen_text);
		dane_do_zmian.add(Box.createVerticalStrut(10));
		
		JLabel p = new JLabel("Prawdopodobienstwo zatrzymania sie wyciagow: ");
		p.setFont(font);
		dane_do_zmian.add(p);
		dane_do_zmian.add(szansa_na_przerwe_text);
		dane_do_zmian.add(Box.createVerticalStrut(10));
		
		JLabel in = new JLabel("Ilosc narciarzy na stoku: ");
		in.setFont(font);
		dane_do_zmian.add(in);
		dane_do_zmian.add(ilosc_narciarzy_text);
		dane_do_zmian.add(Box.createVerticalStrut(10));
		
		JLabel max = new JLabel("Max czas zjazdu narciarzy: ");
		max.setFont(font);
		dane_do_zmian.add(max);
		dane_do_zmian.add(czas_zjazdu_text);
		dane_do_zmian.add(Box.createVerticalStrut(10));
		
		
		JLabel pw = new JLabel("Predkosc wyciagu nr 1: ");
		pw.setFont(font);
		dane_do_zmian.add(pw);
		JSlider slider1 = new JSlider(0,2000,1000);
		slider1.setPaintTicks(true);
		slider1.setMinorTickSpacing(100);
		slider1.setInverted(true);
		slider1.setPaintLabels(true);
		Hashtable<Integer, Component> labelTable1 = new Hashtable<Integer, Component>();
	    labelTable1.put(2000, new JLabel("Wolniej"));
	    labelTable1.put(0, new JLabel("Szybciej"));
	    slider1.setLabelTable(labelTable1);
	    dane_do_zmian.add(slider1);
	    dane_do_zmian.add(Box.createVerticalStrut(10));
		
		JLabel pw2 = new JLabel("Predkosc wyciagu nr 2 i 3: ");
		pw2.setFont(font);
		dane_do_zmian.add(pw2);
		JSlider slider2 = new JSlider(0,2000,500);
		slider2.setPaintTicks(true);
		slider2.setMinorTickSpacing(100);
		slider2.setInverted(true);
		slider2.setPaintLabels(true);
		Hashtable<Integer, Component> labelTable2 = new Hashtable<Integer, Component>();
	    labelTable2.put(2000, new JLabel("Wolniej"));
	    labelTable2.put(0, new JLabel("Szybciej"));
	    slider2.setLabelTable(labelTable2);
		dane_do_zmian.add(slider2);
		dane_do_zmian.add(Box.createVerticalStrut(20));
		
		
		dane_do_zmian.add(zmiana);
		dane_do_zmian.add(Box.createVerticalStrut(50));
		
		
		space.setLayout(new BoxLayout(space, BoxLayout.X_AXIS));
		space.add(dane_do_zmian);
		space.add(Box.createHorizontalStrut(30));
		
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(Box.createHorizontalStrut(100));
		buttons.add(start);
		buttons.add(Box.createHorizontalStrut(25));
		buttons.add(stop);
		buttons.add(Box.createVerticalStrut(100));
		
		menu.add(space, BorderLayout.NORTH);
		menu.add(buttons);
		
		layout_gora.add(menu, BorderLayout.EAST);
		layout_gora.add(animacja_border);
		layout_gora.setVisible(true);
		
		
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		scrollpane.setPreferredSize(new Dimension(200, 300));
			
		layout_dol.add(scrollpane);
		layout_dol.setVisible(true);
		
		
		gui.setTitle("Projekt");
		gui.setDefaultCloseOperation(gui.EXIT_ON_CLOSE);
		gui.setExtendedState(gui.MAXIMIZED_BOTH);
		gui.add(layout);
		gui.setResizable(false);
		gui.setVisible(true);
		
		
		
		zmiana.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				ilosc_siedzen = Integer.parseInt(ilosc_siedzen_text.getText());
				ilosc_siedzenS = ilosc_siedzen_text.getText();
				
				szansa_na_przerwe = Double.parseDouble(szansa_na_przerwe_text.getText());
				szansa_na_przerweS = szansa_na_przerwe_text.getText();
				
				ilosc_narciarzy = Integer.parseInt(ilosc_narciarzy_text.getText());
				ilosc_narciarzyS = ilosc_narciarzy_text.getText();
				
				czas_zjazdu = Integer.parseInt(czas_zjazdu_text.getText());
				czas_zjazduS = czas_zjazdu_text.getText();
				
				
				
				ilosc_siedzen2 = ilosc_siedzen/2;
				ilosc_siedzen3 = ilosc_siedzen-ilosc_siedzen2;
				szansa_przerwy2 = szansa_na_przerwe/2;
			}
			
		});
        
        
        start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LinkedBlockingQueue czekaj = new LinkedBlockingQueue();
				LinkedBlockingQueue czekaj2 = new LinkedBlockingQueue();
				
				for(int i = 0; i<ilosc_narciarzy; i++) {
					add = new Narciarz(czekaj, czas_zjazdu, animacja);
					czekaj.add(add);
				}
				konsola.setText(null);
				start.setEnabled(false);
				stop.setEnabled(true);
				ilosc_siedzen_text.setText(ilosc_siedzenS);
				szansa_na_przerwe_text.setText(szansa_na_przerweS);
				ilosc_narciarzy_text.setText(ilosc_narciarzyS);
				czas_zjazdu_text.setText(czas_zjazduS);
				animacja.wyc1_stop = 0;
				animacja.wyc2_stop = 0;
				animacja.wyc3_stop = 0;
				
				slider1.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						n.Predkosc_jazdy_wyciagu = slider1.getValue();
						animacja.predkosc_wyc1 = n.Predkosc_jazdy_wyciagu;
					}
					
				});
				
				slider2.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						n2.Predkosc_jazdy_wyciagu2 = slider2.getValue();
						n3.Predkosc_jazdy_wyciagu2 = slider2.getValue();
						animacja.predkosc_wyc2 = n2.Predkosc_jazdy_wyciagu2;
						animacja.predkosc_wyc3 = n3.Predkosc_jazdy_wyciagu2;
					}
					
				});
				
				
				
				n = new Symulacja(konsola, start, animacja, ilosc_siedzen, szansa_na_przerwe, czekaj, czekaj2, czas_zjazdu, ilosc_narciarzy, 1);
				n2 = new Symulacja(konsola, start, animacja, ilosc_siedzen2, szansa_przerwy2, czekaj, czekaj2, czas_zjazdu, ilosc_narciarzy, 2);
				n3 = new Symulacja(konsola, start, animacja, ilosc_siedzen3, szansa_przerwy2, czekaj, czekaj2, czas_zjazdu, ilosc_narciarzy, 3);
		        
				animacja.begin();
				Thread animacja_watek = new Thread(animacja);
				animacja_watek.start();
				
		        n.execute();
		        n2.execute();
		        n3.execute();
		        
			}
			
			

				
        	
        });
        
        stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				n.cancel(true);
				n2.cancel(true);
				n3.cancel(true);
				animacja.cancel();
				start.setEnabled(true);
				stop.setEnabled(false);
				add.numer = 0;
		        add.licznik = 0;
			}
		});
        
        zmiana.doClick();
	}
}
