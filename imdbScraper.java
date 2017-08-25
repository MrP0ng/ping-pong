package imdbScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Scanner;

public class imdbScraper {
	private static Document document;
	private static String title; // varje films titel
	private static String rating; // varje films rating
	private static ArrayList<String> titleList;
	private static ArrayList<String> ratingList;

	public static void main(String[] args) throws Exception {

		document = Jsoup.connect("http://www.imdb.com/chart/top").get();

		int nbrEight = 0;
		int nbrEightHalf = 0;
		int nbrNine = 0;

		titleList = new ArrayList<String>();
		ratingList = new ArrayList<String>();

		for (Element row : document.select("table.chart.full-width tr")) {

			title = row.select(".titleColumn a").text();
			rating = row.select(".imdbRating").text();

			titleList.add(title);
			ratingList.add(rating);

			if (title.length() > 0) {
				System.out.println(title + " - " + rating);
			}

			if (rating.compareTo("8.0") >= 0 && rating.compareTo("8.5") < 0) {
				nbrEight++;
			} else if (rating.compareTo("8.5") >= 0 && rating.compareTo("9.0") < 0) {
				nbrEightHalf++;
			} else if (rating.compareTo("9.0") >= 0) {
				nbrNine++;
			}

		}

		System.out.println("\n" + "Antal filmer med 9.0+ i betyg: " + nbrNine);
		System.out.println("Antal filmer med 8.5 - 8.9 i betyg: " + nbrEightHalf);
		System.out.println("Antal filmer med 8.0 - 8.4 i betyg: " + nbrEight);

		findMovieByTitle();

	}

	public static void findMovieByTitle() {
		System.out.println("\nVilken film letar du efter?");

		Scanner scan = new Scanner(System.in);
		String titleName = scan.nextLine();

		int placeInList = 0;

		for (String title : titleList) {
			if (title.equalsIgnoreCase(titleName)) {

					System.out.println(titleList.get(placeInList) + " finns på IMDB top 250, på plats " + placeInList
							+ ". Den har " + ratingList.get(placeInList) + " i rating");
					return;
			}
			placeInList++;
		}
		System.out.println("Denna film finns inte på IMDB top 250");
		findMovieByTitle();
	}
}

