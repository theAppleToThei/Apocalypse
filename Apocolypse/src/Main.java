import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class Main
{
	private static final int SUICIDE_INCREMENT = -4;
	private static final int ENCOUNTER_INCREMENT = -4;
	
	static String apocalypse = "Start";
	static Boolean isapocalypse = false;
	static JFrame mainWindow = new JFrame("apocalypse");
	static JButton apocalypseButton = new JButton(apocalypse + " apocalypse");
	static Long millis = System.currentTimeMillis();
	static Date date = new Date(millis);
	static SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
	static String timeStr = format.format(date);
	static JLabel time = new JLabel(timeStr);

	static Timer suicideTimer = new Timer();

	static int suicides;
	static int homocides;
	static int starvedToDeath;
	static int diedOfIllness;

	static ArrayList<Observer> population = new ArrayList<Observer>();
	static ArrayList<Prepper> preppers = new ArrayList<Prepper>();
	static ArrayList<Engineer> engineers = new ArrayList<Engineer>();
	static ArrayList<Brute> brutes = new ArrayList<Brute>();

	public static void main(String[] args)
	{
		for (int i = 0; i < 50; i++)
		{
			population.add(new Prepper("P" + String.valueOf(i)));
			preppers.add((Prepper) population.get(i));
			population.add(new Engineer("E" + String.valueOf(i)));
			engineers.add((Engineer) population.get(i + 1));
			population.add(new Brute("B" + String.valueOf(i)));
			brutes.add((Brute) population.get(i + 2));
		}
		mainWindow.setSize(500, 400);
		mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		ActionListener actionListener = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateTime();
				System.out
						.println("apocalypse " + apocalypse + "ed at " + time);
				mainWindow.setVisible(false);
				mainWindow.remove(apocalypseButton);
				if (apocalypse.equalsIgnoreCase("Start"))
				{
					apocalypse = "End";
					isapocalypse = true;
					apocalypseButton.setText(apocalypse + " apocalypse");
					mainWindow.add(apocalypseButton);
					mainWindow.setVisible(true);
					apocalypse();
				} else if (apocalypse.equalsIgnoreCase("End"))
				{
					apocalypse = "Start";
					isapocalypse = false;
					System.out.println("Suicides: " + suicides);
					apocalypseButton.setText(apocalypse + " apocalypse");
					mainWindow.add(apocalypseButton);
					mainWindow.setVisible(true);
				}
			}
		};

		mainWindow.add(apocalypseButton);
		// mainWindow.add(time);
		mainWindow.setVisible(true);
		apocalypseButton.addActionListener(actionListener);
	}

	public static void apocalypse()
	{
		suicideTimer(5);
	}

	public static void updateTime()
	{
		millis = System.currentTimeMillis();
		date = new Date(millis);
		format = new SimpleDateFormat("hh:mm:ss");
		timeStr = format.format(date);
		time = new JLabel(timeStr);
	}

	public static void suicideTimer(int seconds)
	{
		Timer timer = new Timer();
		suicides += 1;
		timer.scheduleAtFixedRate(new SuicideTask(), seconds + SUICIDE_INCREMENT,
				(seconds + SUICIDE_INCREMENT) * 1000);
	}
	
	public static void encounterTimer(int seconds)
	{
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new EncounterTask(), seconds + ENCOUNTER_INCREMENT,
				(seconds + ENCOUNTER_INCREMENT) * 1000);
	}

	public static Observer getRandomObserver()
	{
		int random = (int) (Math.random() * (population.size()));
		return population.get(random);
	}

}

class SuicideTask extends TimerTask
{
	public void run()
	{
		Observer toDie = Main.getRandomObserver();
		System.out.println(toDie.getName() + " commited suicide...");
		Main.population.remove(toDie);
	}
}

class EncounterTask extends TimerTask
{
	public void run()
	{
		//TODO Add Logic
	}
}