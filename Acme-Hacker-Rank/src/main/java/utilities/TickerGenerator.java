
package utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalDate;

public class TickerGenerator {

	public static String generateTicker() {
		final List<String> alpha = new ArrayList<String>();
		alpha.add("A");
		alpha.add("B");
		alpha.add("C");
		alpha.add("D");
		alpha.add("E");
		alpha.add("F");
		alpha.add("G");
		alpha.add("H");
		alpha.add("I");
		alpha.add("J");
		alpha.add("K");
		alpha.add("L");
		alpha.add("M");
		alpha.add("N");
		alpha.add("O");
		alpha.add("P");
		alpha.add("Q");
		alpha.add("R");
		alpha.add("S");
		alpha.add("T");
		alpha.add("U");
		alpha.add("V");
		alpha.add("W");
		alpha.add("X");
		alpha.add("Y");
		alpha.add("Z");

		final List<String> nums = new ArrayList<String>();
		nums.add("0");
		nums.add("1");
		nums.add("2");
		nums.add("3");
		nums.add("4");
		nums.add("5");
		nums.add("6");
		nums.add("7");
		nums.add("8");
		nums.add("9");

		final int day1 = Calendar.getInstance().getTime().getDate();
		final String day = String.valueOf(day1);
		System.out.println("day " + day1);
		final int year1 = Calendar.getInstance().getTime().getYear();
		final String year = String.valueOf(year1);
		System.out.println("year " + year1);
		final int month1 = Calendar.getInstance().getTime().getMonth();

		final String month = String.valueOf(month1);
		System.out.println("month " + month1);

		final String bueno = LocalDate.now().toString();
		final String bueno2 = bueno.replace("-", "");
		final String bueno3 = bueno2.substring(2);

		String ticker = bueno3;

		ticker = ticker + "-";
		for (Integer i = 0; i < 6; i++) {
			final Integer selector = new Random().nextInt(26);
			if (selector > 14) {
				final Integer letra = new Random().nextInt(26);
				final String a = alpha.get(letra);
				ticker = ticker + a;
			} else {
				final Integer numero = new Random().nextInt(9);
				final String b = nums.get(numero);
				ticker = ticker + b;
			}
		}
		return ticker;
	}

	public static void main(final String[] args) {
		System.out.println("Ejemplo de ticket ");
		System.out.println(TickerGenerator.generateTicker());

	}

	public static String tickerLeave() {

		final List<String> alpha = new ArrayList<String>();
		alpha.add("A");
		alpha.add("B");
		alpha.add("C");
		alpha.add("D");
		alpha.add("E");
		alpha.add("F");
		alpha.add("G");
		alpha.add("H");
		alpha.add("I");
		alpha.add("J");
		alpha.add("K");
		alpha.add("L");
		alpha.add("M");
		alpha.add("N");
		alpha.add("O");
		alpha.add("P");
		alpha.add("Q");
		alpha.add("R");
		alpha.add("S");
		alpha.add("T");
		alpha.add("U");
		alpha.add("V");
		alpha.add("W");
		alpha.add("X");
		alpha.add("Y");
		alpha.add("Z");

		final List<String> nums = new ArrayList<String>();
		nums.add("0");
		nums.add("1");
		nums.add("2");
		nums.add("3");
		nums.add("4");
		nums.add("5");
		nums.add("6");
		nums.add("7");
		nums.add("8");
		nums.add("9");

		String ticker = "";

		for (Integer i = 0; i < 6; i++) {
			final Integer selector = new Random().nextInt(26);
			if (selector > 14) {
				final Integer letra = new Random().nextInt(26);
				final String a = alpha.get(letra);
				ticker = ticker + a;
			} else {
				final Integer numero = new Random().nextInt(9);
				final String b = nums.get(numero);
				ticker = ticker + b;
			}
		}

		return ticker;
	}

	public static String tickerPosition(final String name) {

		String valid = "";
		if (name.length() >= 4) {
			valid = valid + name.charAt(0);
			valid = valid + name.charAt(1);
			valid = valid + name.charAt(2);
			valid = valid + name.charAt(3);
		} else if (name.length() == 3) {
			valid = valid + name.charAt(0);
			valid = valid + name.charAt(1);
			valid = valid + name.charAt(2);
			valid = valid + "X";
		} else if (name.length() == 2) {
			valid = valid + name.charAt(0);
			valid = valid + name.charAt(1);
			valid = valid + "XX";
		} else if (name.length() == 1) {
			valid = valid + name.charAt(0);
			valid = valid + "XXX";
		}

		final List<String> nums = new ArrayList<String>();
		nums.add("0");
		nums.add("1");
		nums.add("2");
		nums.add("3");
		nums.add("4");
		nums.add("5");
		nums.add("6");
		nums.add("7");
		nums.add("8");
		nums.add("9");

		String ticker = "";

		ticker = ticker + valid + "-";

		for (Integer i = 0; i < 4; i++) {
			final Integer numero = new Random().nextInt(9);
			final String b = nums.get(numero);
			ticker = ticker + b;
		}

		return ticker;
	}
}
